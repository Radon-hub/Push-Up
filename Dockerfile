FROM ubuntu:latest
LABEL authors="a.kherazan"

ENTRYPOINT ["top", "-b"]