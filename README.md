Como executar localmente com Docker Compose
- Pré‑requisitos: Docker e Docker Compose instalados.
- Passos:
  1. Suba os containers (o build do JAR é feito dentro da imagem automaticamente):
     - docker compose up --build
  2. A API ficará disponível em: http://localhost:8080
  3. O banco PostgreSQL ficará disponível em: localhost:5432

Documentação OpenAPI (Swagger)
- UI interativa (Swagger UI):
  - http://localhost:8080/swagger-ui.html
- Especificação OpenAPI (JSON):
  - http://localhost:8080/v3/api-docs
- Especificação OpenAPI (YAML):
  - http://localhost:8080/v3/api-docs.yaml

Observações:
- O projeto usa springdoc-openapi (starter WebMVC UI).
- Ao subir com Docker Compose, os links acima ficam disponíveis automaticamente.

Migrações Flyway
- As migrações são executadas automaticamente na inicialização:
  - V1__create_credito_table.sql: cria a tabela `credito`.
  - V2__populate_credito.sql: insere 3 registros de exemplo na tabela `credito`.
- Os scripts estão em `src/main/resources/db/migration/`.

Execução com Flyway (padrão)
- A aplicação usa Flyway para criar o schema e popular os dados automaticamente na inicialização, inclusive em Docker.

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
- Flyway habilitado e `spring.jpa.hibernate.ddl-auto=validate` para evitar conflitos com migrações.
- O Dockerfile é multi-stage: compila o JAR com Maven na primeira etapa e roda com JRE na segunda.

Endpoints
- Consultar crédito por número de NFSe:
  - GET http://localhost:8080/api/creditos/{numeroNfse}
  - Exemplos (dados populados por V2):
    - /api/creditos/7891011
    - /api/creditos/1122334

Kafka (testing)

- Topic por defecto: `creditos-consulta`
- Variável de ambiente usada pela aplicação: `KAFKA_BOOTSTRAP_SERVERS` (em `docker-compose.yml` está configurada como `kafka:29092` para comunicação interna). Se você executar a aplicação localmente sem Docker, o valor padrão em `application.properties` é `localhost:9092`.

Conectar do host (localhost:9092)

- Se você iniciar os serviços com `docker compose up --build`, o broker Kafka estará exposto em `localhost:9092` (mapeamento em `docker-compose.yml`). A partir do host, você pode usar uma imagem do Kafka para executar clientes. No Windows, evite `--network=host`; em vez disso, use `docker compose exec` ou conecte-se diretamente a `localhost:9092` se a imagem do cliente estiver sendo executada no host.