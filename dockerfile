FROM java:8
EXPOSE 8086
ADD /target/hardskillsrecruitement.jar hardskillsrecruitement.jar
ENTRYPOINT ["java", "-jar", "/hardskillsrecruitement.jar"]