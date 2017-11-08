#!/bin/bash
until nc -z smart_alarm_postgresql_host 5432; do echo "Waiting for PostgreSQL to boot up..." && sleep 1; done


if [ $# -ne 0 ] ; then
    # Allows for using as entrypoint and executing arbitrary command in docker compose
    echo "Evaluating... $($*)"
    eval $*
else
    python manage.py migrate
    python manage.py runserver 0.0.0.0:8000 --settings=config.settings.dev
fi
