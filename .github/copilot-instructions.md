**Project Overview**

- **Stack**: Spring Boot (3.5.x), Java 17, Maven. See `dev/pom.xml` for dependencies (Web, Thymeleaf, Data JPA, Security, Flyway, Postgres runtime).
- **Main entry**: `dev/src/main/java/systemagenda/com/dev/DevApplication.java` — standard Spring Boot app.
- **Runtime DB**: Postgres expected in production; tests use H2 (see `pom.xml`). Flyway is present for DB migrations.

**How To Build & Run (developer)**

- Windows (recommended):

  1. Open a `cmd.exe` in repo root and run:

     ```cmd
     cd dev
     mvnw.cmd clean package
     ```

  2. Run the packaged jar:

     ```cmd
     java -jar target\dev-0.0.1-SNAPSHOT.jar
     ```

- Alternative (dev mode):

  ```cmd
  cd dev
  mvnw.cmd spring-boot:run
  ```

**Tests**

- Run unit/integration tests:

  ```cmd
  cd dev
  mvnw.cmd test
  ```

- Tests use H2 as test-scoped dependency (see `dev/pom.xml`).

**Source Layout & Gotchas**

- Primary Maven layout: `dev/src/main/java` maps to package `systemagenda.com.dev` (application class location).
- There is an additional `dev/src/java/com/stardepiler/entity/*` tree containing entities such as `Usuario.java`. This is NOT under the standard `src/main/java` layout and may not be compiled by Maven by default. When investigating compile/runtime issues, check whether these sources are intended (legacy/extra) or should be moved into `src/main/java`.
- Templates and static assets: `dev/src/main/resources/templates` and `dev/src/main/resources/static` (standard Thymeleaf + static resource locations).

**Key Files to Inspect**

- `dev/pom.xml` — dependency and build plugin source of truth.
- `dev/src/main/java/systemagenda/com/dev/DevApplication.java` — app entry.
- `dev/src/main/resources/application.properties` — base app props (currently minimal).
- `dev/src/java/com/stardepiler/entity/Usuario.java` — example of entity found outside standard layout.

**Patterns & Conventions (observed)**

- Uses Spring MVC + Thymeleaf for server-side rendered views (look for controllers under `src/main/java` and templates under `resources/templates`).
- Spring Security is present (see `spring-boot-starter-security` in `pom.xml`) — search for security configuration classes if you need to modify auth behavior.
- JPA entities use Jakarta Persistence annotations (e.g., `@Entity`, `@Table`); UUID generation via `@UuidGenerator` is used in `Usuario.java`.

**Database & Migrations**

- Flyway is configured as a dependency; migrations are expected in the standard `db/migration` location under resources if used. Production expects Postgres — check environment/config when running outside tests.

**When Writing Code/PRs**

- Prefer adding new Java sources under `dev/src/main/java` to ensure Maven builds them by default.
- When adding DB-backed features, add Flyway migration scripts and update `application.properties` (or the environment-specific config) to point to the test/production database.

**Investigation Tips for AI agents**

- If you see code that compiles locally but not in CI, confirm which source roots are compiled by Maven. Inspect `dev/pom.xml` and, locally, `mvn help:evaluate -Dexpression=project.build.sourceDirectory`.
- To find where a class is referenced, search for its simple name across the repo (e.g., `Usuario`) and verify package imports.
- Use `dev\\mvnw.cmd -DskipTests package` for quick packaging iterations on Windows.

**Example quick checks**

- Does the app start? `cd dev && mvnw.cmd spring-boot:run` then open `http://localhost:8080`.
- Run a single test class: `cd dev && mvnw.cmd -Dtest=systemagenda.com.dev.DevApplicationTests test`.

If any section is unclear or you want me to add/check more files (for example to determine whether `dev/src/java` sources are intentionally included), tell me which files to inspect and I will update this file accordingly.
