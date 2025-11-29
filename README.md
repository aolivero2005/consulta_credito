# consulta_credito

API Spring Boot containerizada com Docker + PostgreSQL para provisionamento local.

Como executar localmente com Docker Compose
- Pré‑requisitos: Docker e Docker Compose instalados.
- Passos:
  1. Compile o projeto para gerar o JAR:
     - No Windows: .\mvnw.cmd clean package
     - No Linux/macOS: ./mvnw clean package
  2. Suba os containers:
     - docker compose up --build
  3. A API ficará disponível em: http://localhost:8080
  4. O banco PostgreSQL ficará disponível em: localhost:5432

Variáveis de ambiente (opcionais)
- DB_NAME (padrão: credito_db)
- DB_USER (padrão: credito_user)
- DB_PASSWORD (padrão: credito_pass)
- Você pode criar um arquivo .env na raiz para customizar:

Exemplo de .env
DB_NAME=credito_db
DB_USER=credito_user
DB_PASSWORD=credito_pass

Detalhes técnicos
- O application.properties está parametrizado para usar PostgreSQL, com fallback para os valores definidos no docker-compose.
- JPA/Hibernate usa ddl-auto=update para criar/atualizar as tabelas automaticamente com base nas entidades.
- O Dockerfile espera que o JAR esteja em target/consulta_credito-0.0.1-SNAPSHOT.jar (gerado pelo Maven).

Comandos úteis
- Parar containers: docker compose down
- Remover volumes (apaga dados do Postgres): docker compose down -v