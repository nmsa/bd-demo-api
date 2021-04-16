# Base de Dados - Postgresql Demo

This code is to be used in the scope of the _BD_ course.


## Requirements

- To execute this project it is required to have installed:
  - Docker

## Development

Use only if you need to have database running in separate. 
The executables in the root are preparared to start the database and connect it with Web Application.

It could be useful to have it running in separate for the Java example, where it is not possible for you to change code being executed by Docker and access does changes without starting docker components again.

## Database Connection

- **User**: aulaspl
- **Password**: aulaspl
- **Database name**: dbfichas
- **Host**: localhost:5432

## Setup and Run

To build the docker image you should run:

```sh
sh build.sh
```

To run the container:

```sh
sh run.sh
```

- _note: modifying the `run.sh` script to include -dit will make the container work in background. But dont forget to use `stop.sh` to stop/remove it later._

To stop the container:

```sh
sh stop.sh
```


## Authors

* BD 2021 Team - https://dei.uc.pt/lei/
* University of Coimbra

