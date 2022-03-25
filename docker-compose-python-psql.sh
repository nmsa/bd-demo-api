#!/bin/bash
#
# Bases de Dados 2021/2022
# Trabalho Prático
#
# Authors:
#   Nuno Antunes <nmsa@dei.uc.pt>
#   BD 2022 Team - https://dei.uc.pt/lei/
#   University of Coimbra


#
# ATTENTION: This will stop and delete all the running containers
# Use it only if you are not using docker for other ativities
#
#docker rm $(docker stop $(docker ps -a -q))




# add  -d  to the command below if you want the containers running in background without logs
docker-compose  -f docker-compose-python-psql.yml up --build
