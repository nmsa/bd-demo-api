# 
# Bases de Dados 2020/2021
# Trabalho Prático
#
# Authors: 
#   Nuno Antunes <nmsa@dei.uc.pt>
#   BD 2021 Team - https://dei.uc.pt/lei/
#   University of Coimbra

FROM library/postgres


ENV POSTGRES_USER aulaspl
ENV POSTGRES_PASSWORD aulaspl
ENV POSTGRES_DB dbfichas

COPY BD2021_data.sql /docker-entrypoint-initdb.d/

EXPOSE 5432
