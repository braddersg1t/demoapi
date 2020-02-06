# Getting Started

### System Requirements
* Java 8 (used 1.8.0_202)
* Gradle (used 5.3.1)

### Build
* From root of project: gradle build
* Resulting jar can be found at: build/libs/demoapi-1.0.jar 

### To run application
* java -jar -Dlogs_dir=/{path-to-desired-log-location} build/libs/demoapi-1.0.jar
* Example: java -jar -Dlogs_dir=/opt/demoapi build/libs/demoapi-1.0.jar

### Testing Endpoints
Using Swagger UI.
* Load the following URL in your browser: http://localhost:8080/swagger-ui.html
* To load listed London users: See endpoint /city/{city}/users/listed where {city} = London
* To load users within 50 miles of London: See endpoint /city/{city}/users/distance/{miles} where {city} = London and {miles} = 50

Using a API test suite such as Postman, the following endpoints can be tested.
* To load listed London users: http://localhost:8080/city/London/users/listed
* To load users within 50 miles of London: http://localhost:8080/city/London/users/distance/50

### Code Coverage Report
* From root of project: gradle test jacocoTestReport
* Reports located at: build/reports/jacoco/test/html/index.html

### SonarQube Code Analysis Report
* Install Docker
* run: docker run -d --name sonarqube -p 9000:9000 sonarqube
* Browse to http://127.0.0.1:9000 to load SonarQube UI
* Default user:pass is admin:admin
* From root of project: gradle sonarqube
* Refresh SonarQube UI to see latest results
