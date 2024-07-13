# Award Producers Application

Welcome to the Award Producers Application! This application is a Java powered service that provides information about award-winning movie producers. The project is based on Java 21, Spring Boot 3.3.1, and utilizes an H2 in-memory database and Maven as the build and dependency tool.

## Technologies Used

- Java 21
- Spring Boot 3.3.1
- H2 In-Memory Database
- Maven

## Building the Application

To build this project, we use Maven. Execute the following command in the project root directory:
```
java -jar target/worstmovie.jar
```

To access the sole endpoint of this application, navigate to the following URL on your browser:
http://localhost:8080/award-producers

Alternatively, you can use the cURL command:
```
curl http://localhost:8080/award-producers
```

## Accessing the In-Memory Database


The project runs an H2 database in memory. Once the project is running, it's possible to access the database console at:

```
http://localhost:8080/h2-console/
```
Use the following credentials:

Username: sa
Password: sa

## Running Tests
To execute the automated unit tests via the command line, run the following Maven command
```
mvn test
```
## Changing Initial Data

Every time the project starts, it re-creates the table and inserts values based on a CSV file passed into the application. If you wish to modify these initial data, add your new .csv file in the resource folder and update the filename in the WorstMovieApplication.java as showcased below:

```
@SpringBootApplication
public class WorstMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorstMovieApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(AwardMovieService awardMovieService) {
        return args -> {

            Path path = Paths.get(Objects.requireNonNull(
                    ReadCSV.class.getResource("/changedmovielist.csv")).toURI());

            List<MovieModel> movieModels = ReadCSV.readCSV(path);
            awardMovieService.saveMovies(movieModels);
        };
    }
}

```
