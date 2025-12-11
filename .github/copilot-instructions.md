**Visão geral do projeto**

- **Módulo principal**: todo o backend fica em `dev/` (ignore qualquer `src/` na raiz).
- **Stack**: Spring Boot 3.5.x, Java 17, Maven; dependências em `dev/pom.xml` (Web, Thymeleaf, Data JPA, Validation, Security, Flyway + Postgres, JasperReports).
- **Entry point**: `dev/src/main/java/systemagenda/com/dev/DevApplication.java` (@SpringBootApplication na raiz do pacote `systemagenda.com.dev`).
- **Banco**: PostgreSQL em runtime (Flyway), H2 apenas em testes.

**Arquitetura e camadas**

- **Pacotes principais** (sempre sob `systemagenda.com.dev`):
  - `entity`: modelo JPA (`Cliente`, `Usuario`, `Tratamento`, `Sessao`, `FichaAvaliacao`).
  - `repository`: interfaces `JpaRepository` por entidade (`ClienteRepository`, `TratamentoRepository`, etc.).
  - `service`: regras de negócio e transações (`ClienteService`, `SessaoService`, ...), usando `@Service` + `@Transactional` do Spring.
  - `controller`: controllers MVC anotados com `@Controller` (não usar REST aqui por padrão).
- **Views Thymeleaf**: em `dev/src/main/resources/templates`, organizadas por funcionalidade (`clientes/`, `fichas/`, `tratamentos/`, `sessoes/`, `usuarios/`).
- **Fluxos principais já implementados**:
  - Clientes: listagem paginada com busca (`ClienteController` + `clientes/lista.html` usando htmx).
  - Ficha de avaliação: formulário 1–1 por cliente (`FichaAvaliacaoController` + `fichas/form.html`).
  - Tratamentos e sessões: navegação `Cliente → Tratamentos → Sessões` com forms simples.
  - Usuários: CRUD básico sem segurança ainda (`UsuarioController` + `usuarios/lista.html`).

**Modelo de domínio e JPA**

- Entidades em `entity` mapeiam as tabelas da `V1__create_tabelas.sql` em `dev/src/main/resources/db/migration`:
  - `Usuario` ↔ `usuarios`, `Cliente` ↔ `clientes`, `Tratamento` ↔ `tratamentos`, `Sessao` ↔ `sessoes`, `FichaAvaliacao` ↔ `fichas_avaliacao`.
- Padrões das entidades:
  - PK `UUID` com `@GeneratedValue(strategy = GenerationType.UUID)`.
  - `@Table(name = "<tabela>")` quando necessário; nomes de coluna em snake_case via `@Column(name = ...)` (ex.: `data_sessao`, `eh_reavaliacao`, `area_tratamento`).
  - Construtor vazio público + getters/setters explícitos; não usar Lombok em classes já existentes.
- Relacionamentos importantes (copiar esses padrões):
  - `Cliente` 1–1 `FichaAvaliacao` (`@OneToOne(mappedBy = "cliente")`).
  - `Cliente` 1–N `Tratamento` (`@OneToMany(mappedBy = "cliente")`).
  - `Tratamento` 1–N `Sessao` com `cascade = CascadeType.ALL` e `orphanRemoval = true`.
  - `Sessao` N–1 `Tratamento` (`@ManyToOne` + `@JoinColumn(name = "tratamento_id")`).

**Migrações e banco de dados**

- Migrações Flyway: `dev/src/main/resources/db/migration/V1__create_tabelas.sql` cria todas as tabelas com PK `UUID` e FKs alinhadas às entidades.
- Não reescrever/alterar `V1__...`; para qualquer mudança de schema, criar novos arquivos `V2__...`, `V3__...` etc.
- Convenções:
  - SQL em `snake_case`, Java em `camelCase` com `@Column(name = ...)` quando o nome divergir.
  - Novas PKs devem continuar usando `UUID`.

**Execução local, build e testes**

- Sempre trabalhar a partir da pasta `dev/`:
  - Build: `cd dev && ./mvnw clean package` (ou `mvnw.cmd clean package` no Windows).
  - Testes: `cd dev && ./mvnw test`.
  - Rodar app: `cd dev && ./mvnw spring-boot:run`.
- Configuração padrão em `dev/src/main/resources/application.properties`:
  - Postgres local em `jdbc:postgresql://localhost:8000/stardepiller` com usuário/senha `stardepiller` / `159753`.
  - `spring.jpa.hibernate.ddl-auto=none` (schema só via Flyway) e `spring.flyway.locations=classpath:db/migration`.
  - `server.port=8081` (acesso via `http://localhost:8081`).
- Não trocar credenciais hardcoded nem porta por padrão; se precisar usar outro banco/porta, criar outro profile (`application-<profile>.properties`).

**Padrões para novo código**

- Entidades novas: colocar em `entity`, seguir estilo de `Cliente`/`Tratamento` (UUID, construtor vazio, relacionamento mapeado do lado 1–N na coleção).
- Repositórios: `JpaRepository<Entidade, UUID>` em `repository`, com métodos derivados por nome (ex.: `findByClienteOrderByAreaTratamentoAsc`).
- Serviços: classes em `service` com `@Service` + `@Transactional` (usar `readOnly = true` para consultas); toda lógica de negócio deve ficar aqui, não nos controllers.
- Controllers: usar `@Controller` + retorno de nome de view; injetar serviços via construtor; para paginação usar `Pageable`/`Page` como em `ClienteController`.
- Views: criar templates em subpastas de `templates` seguindo convenções já usadas; para interação dinâmica preferir htmx (ver `clientes/lista.html`).

**Segurança, relatórios e limites para o agente**

- Dependências de segurança e Jasper já estão no `pom.xml`, mas a configuração detalhada de Spring Security e relatórios ainda será evoluída; ao criar código novo, apenas prepare pontos de extensão (ex.: separar lógica de autenticação em serviço dedicado) sem assumir detalhes que não existem.
- Não introduzir novos frameworks de web/ORM (por exemplo, não adicionar REST controllers ou outro template engine) sem necessidade forte.
- Não apagar ou renomear entidades, controllers, services ou templates existentes sem orientação explícita, pois eles estão amarrados às etapas do trabalho DAW1.
