#!/bin/bash
# 
# Bases de Dados 2020/2021
# Trabalho Prático
#
# Authors: 
#   Nuno Antunes <nmsa@dei.uc.pt>
#   BD 2021 Team - https://dei.uc.pt/lei/
#   University of Coimbra

image="bd-psql"
container="db"



echo "-- Building --"
docker   build  -t  $image   .
