# 
# Bases de Dados 2020/2021
# Trabalho Prático
#
# Authors: 
#   Nuno Antunes <nmsa@dei.uc.pt>
#   BD 2021 Team - https://dei.uc.pt/lei/
#   University of Coimbra

FROM maven:3-jdk-11-slim

COPY 	mvn-spring-boot /mvn-spring-boot
WORKDIR /mvn-spring-boot


RUN mvn clean package


CMD ["/mvn-spring-boot/docker.sh"]



