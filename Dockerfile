FROM eclipse-temurin:17-jre-alpine AS runtime

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o wrapper Maven e o projeto para compilar a aplicação dentro do container, se preferir build local basta copiar o JAR final
# Nesta abordagem, assumimos que o JAR já foi construído fora (mvn package). Assim, copiamos apenas o artefato.

# Argumentos com valores padrão (podem ser sobrescritos no build)
ARG JAR_FILE=target/consulta_credito-0.0.1-SNAPSHOT.jar

# Copia o JAR construído localmente
COPY ${JAR_FILE} app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Variáveis de ambiente padrão (podem ser sobrescritas no docker-compose)
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
