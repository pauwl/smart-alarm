FROM python:3.6.3

RUN apt-get update && apt-get install -y \
    netcat

COPY ./requirements /requirements
RUN pip install -r /requirements/requirements-dev.txt

COPY ./deploy /app/deploy
RUN chmod +x -R /app/deploy

WORKDIR /app
ENTRYPOINT /app/deploy/dev/start.sh
