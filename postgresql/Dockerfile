# 
# Bases de Dados 2020/2021
# Trabalho Prático
#
# Authors: 
#   Nuno Antunes <nmsa@dei.uc.pt>
#   BD 2021 Team - https://dei.uc.pt/lei/
#   University of Coimbra

FROM library/postgres:13


RUN apt update  &&  apt -y install postgresql-13-cron


ENV POSTGRES_USER       aulaspl
ENV POSTGRES_PASSWORD   aulaspl
ENV POSTGRES_DB         dbfichas



# COPY order is not relevant, files loaded by alphabetical order
COPY BD2021_data.sql        /docker-entrypoint-initdb.d/
COPY ./initdb-pg_cron.sh    /docker-entrypoint-initdb.d/pg_cron.sh

EXPOSE 5432
