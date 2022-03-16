#!/bin/bash
#
# Bases de Dados 2021/2022
# Trabalho Prático
#
# Authors:
#   Nuno Antunes <nmsa@dei.uc.pt>
#   BD 2022 Team - https://dei.uc.pt/lei/
#   University of Coimbra

image="bd-psql"
container="db"



docker stop $container
docker rm $container
