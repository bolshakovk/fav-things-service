FROM openjdk:latest
ADD target/fav-things-service-0.0.1-SNAPSHOT.jar fav-things-service.jar
ENTRYPOINT ["java", "-jar","fav-things-service.jar"]
EXPOSE 8090
#Основной имейдж для докера
#в задании было указано сделать порт 8090 и время utc+3
#docker build -t springboot-fav-things-service:latest запуск докер контейнера