#
# Bases de Dados 2021/2022
# Trabalho Prático
#
# Authors:
#   Nuno Antunes <nmsa@dei.uc.pt>
#   BD 2022 Team - https://dei.uc.pt/lei/
#   University of Coimbra

FROM maven:3-jdk-11-slim

COPY 	mvn-spring-boot /mvn-spring-boot
WORKDIR /mvn-spring-boot


RUN mvn clean package


CMD ["sh", "/mvn-spring-boot/docker.sh"]

# CMD ["/mvn-spring-boot/docker.sh"]
