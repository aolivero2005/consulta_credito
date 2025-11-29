
Como executar localmente com Docker Compose
- Pré‑requisitos: Docker e Docker Compose instalados.
- Passos:
  1. Suba os containers (o build do JAR é feito dentro da imagem automaticamente):
     - docker compose up --build
  2. A API ficará disponível em: http://localhost:8080
  3. O banco PostgreSQL ficará disponível em: localhost:5432

Migrações Flyway
- As migrações são executadas automaticamente na inicialização:
  - V1__create_credito_table.sql: cria a tabela `credito`.
  - V2__populate_credito.sql: insere 3 registros de exemplo na tabela `credito`.
- Os scripts estão em `src/main/resources/db/migration/`.

Execução com Flyway (padrão)
- A aplicação usa Flyway para criar o schema e popular os dados automaticamente na inicialização, inclusive em Docker.
- Caso já exista um volume antigo sem histórico do Flyway, recomenda-se limpar os volumes antes de subir novamente (ver seção "Solução de problemas").

Solução de problemas (Flyway não cria tabela nem insere dados)
- Se você já subiu os containers antes, o volume do Postgres pode estar mantendo um estado antigo sem o controle do Flyway. Nesse caso, derrube e remova o volume para um recomeço limpo:
  - docker compose down -v
  - docker compose up --build
- Logs detalhados do Flyway foram habilitados. Verifique no log do container da API:
  - docker logs -f credito-api
  - Procure por mensagens de "Flyway Community Edition" e execução de migrações V1/V2.
- A aplicação está configurada para usar explicitamente o schema `public` e criar schemas quando necessário (spring.flyway.default-schema=public e spring.flyway.create-schemas=true). Se seu banco usa um schema diferente, ajuste essas propriedades.

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

Comandos úteis
- Parar containers: docker compose down
- Remover volumes (apaga dados do Postgres): docker compose down -v