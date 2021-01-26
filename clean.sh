#!/bin/bash
docker-compose down
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker ps
docker-compose up -d
docker-compose logs -f
