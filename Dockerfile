## Etapa de build: compila o JAR dentro da imagem (sem necessidade de build prévio)
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /workspace

# Copiamos primeiro os arquivos de definição para otimizar cache
COPY pom.xml ./
RUN mvn -q -e -DskipTests dependency:go-offline

# Agora copiamos o código-fonte
COPY src ./src

# Build do aplicativo
RUN mvn -q -DskipTests package

## Etapa de runtime: imagem leve apenas com o JRE e o artefato
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /workspace/target/consulta_credito-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

# Variáveis de ambiente padrão (podem ser sobrescritas no docker-compose)
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
