#!/bin/sh

#
#   Script according to the documentation in:
#       https://github.com/citusdata/pg_cron
#

set -e

#By default, the pg_cron background worker expects its metadata tables to be created in the "postgres" database. However, you can configure this by setting the cron.database_name configuration parameter in postgresql.conf.

## add to postgresql.conf:
echo "shared_preload_libraries = 'pg_cron'" >> /var/lib/postgresql/data/postgresql.conf
echo "cron.database_name = '$POSTGRES_DB'"      >> /var/lib/postgresql/data/postgresql.conf


# See next comment :-)
pg_ctl restart


# After restarting PostgreSQL, you can create the pg_cron functions and metadata tables using CREATE EXTENSION pg_cron.

psql -U $POSTGRES_USER -d $POSTGRES_DB -c "CREATE EXTENSION  IF NOT EXISTS  pg_cron;"
psql -U $POSTGRES_USER -d $POSTGRES_DB -c "GRANT USAGE ON SCHEMA cron TO $POSTGRES_USER;"

