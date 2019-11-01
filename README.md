# QuestionsServer

To run client use console in /app-client with command "npm start". Client will be launched on a localhost:4200.

Use gradlew to start server. Access to the database at localhost:8080/h2-console/

The database runs in memory and will be deleted when the application restarts. If you want to change this, then you need changes in /src/ main/resources/application.properties.
Change this: "spring.datasource.url=jdbc:h2:mem:restapp" to "spring.datasource.url=jdbc:h2:file:./data/DBName"
