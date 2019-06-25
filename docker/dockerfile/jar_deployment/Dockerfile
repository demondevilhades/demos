
FROM oracle/openjdk:8 

MAINTAINER awesome

# copy project 
COPY ./aaa.jar ./bbb.jar /root/

CMD ["/docker-entrypoint.sh", "bash"]

RUN mkdir -p /home/data/logs/

VOLUME ["/home/data/logs"]

# docker build -t ***/***:1.0 . 
# docker run -itd --name *** -v ***:/home/data/logs -p 8730:8730 -p 8750:8750 -p 8700:8700 -p 8761:8761 ***/***:1.0 
