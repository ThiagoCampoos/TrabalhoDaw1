**Visão geral do projeto**

- **Stack**: Spring Boot 3.5.x, Java 17, Maven. Dependências principais em `dev/pom.xml` (Web, Thymeleaf, Data JPA, Validation, Security, Flyway, Postgres, JasperReports).
- **Entry point**: `dev/src/main/java/systemagenda/com/dev/DevApplication.java`.
- **Banco**: PostgreSQL em runtime (Flyway para migrações), H2 apenas para testes.

**Modelo de domínio e JPA**

- Entidades estão em `dev/src/main/java/systemagenda/com/dev/entity` e mapeiam tabelas Flyway:
  - `Usuario` ↔ tabela `usuarios`.
  - `Cliente` ↔ tabela `clientes`.
  - `Tratamento` ↔ tabela `tratamentos`.
  - `Sessao` ↔ tabela `sessoes`.
  - `FichaAvaliacao` ↔ tabela `ficha_avaliacao` / `fichas_avaliacao`.
- Padrões das entidades:
  - `@Entity` + `@Table(name = "<tabela>")` quando o nome não é trivial.
  - Chave primária com `UUID` e `@GeneratedValue(strategy = GenerationType.UUID)`.
  - Construtor vazio público + getters/setters explícitos (não usar Lombok nas classes existentes).
- Relacionamentos importantes (usar como referência para novos relacionamentos):
  - `Cliente` 1–1 `FichaAvaliacao` (`@OneToOne(mappedBy = "cliente")`).
  - `Cliente` 1–N `Tratamento` (`@OneToMany(mappedBy = "cliente")`).
  - `Tratamento` 1–N `Sessao` com `cascade = CascadeType.ALL` e `orphanRemoval = true`.
  - `Sessao` N–1 `Tratamento` e `Tratamento` N–1 `Cliente` usando `@ManyToOne` + `@JoinColumn`.
- Colunas com nomes específicos devem usar `@Column(name = ...)`, por exemplo:
  - `Sessao.dataSessao` ↔ `data_sessao`.
  - `Sessao.ehReavaliacao` ↔ `eh_reavaliacao`.
  - `Tratamento.areaTratamento` ↔ `area_tratamento`.

**Migrações e banco de dados**

- Migrações Flyway ficam em `dev/src/main/resources/db/migration` (exemplo: `V1__create_tabelas.sql`).
- Não editar arquivos de migração já aplicados; para alterações, criar novos arquivos `V2__...`, `V3__...` etc.
- Ao criar campos/entidades novas, manter coerência entre tipos e nomes:
  - `snake_case` no SQL, `camelCase` no Java com `@Column(name = ...)` quando necessário.
  - Preferir `UUID` para PKs novas mesmo que a migração antiga use `SERIAL`.

**Configuração de execução local**

- Configurações principais em `dev/src/main/resources/application.properties`:
  - `spring.datasource.url=jdbc:postgresql://localhost:8000/stardepiller`.
  - `spring.datasource.username=stardepiller`.
  - `spring.datasource.password=159753`.
  - `spring.jpa.hibernate.ddl-auto=none` (schema só via Flyway).
  - `spring.flyway.enabled=true` e `spring.flyway.locations=classpath:db/migration`.
  - `server.port=8081` (acesso em `http://localhost:8081`).
- Não trocar credenciais hardcoded por variáveis de ambiente sem pedido explícito, mas evitar espalhar esse padrão para novos exemplos.

**Como buildar, testar e rodar**

- Sempre trabalhar dentro de `dev/`:
  - Build: `cd dev && ./mvnw clean package` (ou `mvnw.cmd` no Windows).
  - Testes: `cd dev && ./mvnw test`.
  - Execução em modo dev: `cd dev && ./mvnw spring-boot:run`.
- Jar gerado: `dev/target/dev-0.0.1-SNAPSHOT.jar` (rodar com `java -jar`).

**Padrões para novo código**

- **Novas entidades**: criar em `systemagenda/com/dev/entity`, copiar o estilo de `Cliente`/`Tratamento`:
  - `UUID` como PK, construtor vazio, getters/setters.
  - Mapeamento de relacionamento alinhado à direção já usada (por exemplo, `mappedBy` na coleção do lado 1–N).
- **Repositórios e serviços** (quando forem necessários):
  - Repositórios `JpaRepository` em pacote `.../repository` (a ser criado se ainda não existir).
  - Serviços em `.../service` com `@Service`, concentrando lógica de negócio.
- **Validação**: usar `jakarta.validation` (disponível via `spring-boot-starter-validation`) em DTOs/objetos de entrada quando criados.

**Segurança, views e relatórios**

- Dependências presentes no `pom.xml` indicam:
  - **Spring Security** (`spring-boot-starter-security`) — ao criar controllers/endpoints, assumir que haverá regras de autenticação/autorização.
  - **Thymeleaf + thymeleaf-extras-springsecurity6** — qualquer view nova deve seguir padrão de templates Thymeleaf em `resources/templates`.
  - **JasperReports** — relatórios devem ser organizados em um diretório claro (por exemplo `resources/reports`) e reutilizáveis (não embutir `.jrxml` diretamente em código).

**Boas práticas específicas deste repositório**

- Não introduzir novos frameworks grandes (outro ORM, outro framework web) sem motivo forte.
- Manter nomes de campos alinhados aos já existentes (`status` como `String`, flags booleanas com prefixo `is`/`eh`).
- Ao criar endpoints REST ou controllers MVC:
  - Manter separação entre entidade JPA, DTOs e camada de serviço.
  - Evitar acessar diretamente o `EntityManager` — preferir repositórios Spring Data.

**O que o agente não deve fazer**

- Não apagar ou reescrever migrações Flyway já versionadas.
- Não mudar radicalmente a configuração de banco/porta sem instrução explícita.
- Não mover o layout Maven padrão (`src/main/java`, `src/main/resources`).
