# 
# Bases de Dados 2021/2022
# Trabalho Prático
#
# Authors: 
#   Nuno Antunes <nmsa@dei.uc.pt>
#   BD 2022 Team - https://dei.uc.pt/lei/
#   University of Coimbra

FROM library/postgres


ENV POSTGRES_USER aulaspl
ENV POSTGRES_PASSWORD aulaspl
ENV POSTGRES_DB dbfichas

COPY BD2022_data.sql /docker-entrypoint-initdb.d/

EXPOSE 5432
