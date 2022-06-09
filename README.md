## Project 2
###Module 7 Submission for  Adam Chappell

###Contents:
- musicstore-catalog: 
  - app: the Java Project for the Catalog API Service
  - db: the root for the docker container to run the mySQL Db
- musicstore-recommendations:
  - app: the Java Project for the Recommendations API Service
  - db: the root for the docker container to run the mySQL Db
  
- Project 2 - Music Store Collection:
  - the Exported Postman collection to test the heroku deployed applications.
***
###Reproduction Steps:
- Open or Clone Each Service as a new project:
  - `musicstore-catalog/app`
  - `musicstore-recommendations/app `
  
####Running Tests
- Edit the `application.properties` file in each project within its' own `src/test/resources/` directory, to include your local database username and password in order to run tests.
`spring.datasource.username=[YOUR_DB_USER]
   spring.datasource.password=[YOUR_DB_PASSWORD]`
####Running Local Instance
- Edit the `application.properties` file in the `src/main/resources` directory to use `jdbc:mysql://localhost/` instead of the docker db name within the `spring.datasource.url=` property in order to compile and run the application locally.
####Testing Heroku Deployed Instance
- Use Postman to import the following file as a collection. It contains all methods, endpoints, and sample data, using the deployed URLs:

  -`Project 2 - Music Store Collection`

*Failing the Import, the Deployed URLs and Endpoints are below:*

####Catalog Service:

https://immense-mesa-40644.herokuapp.com/

- Endpoints:
  - /track
  - /album 
  - /label 
  - /artist

####Recommendations Service: 

https://fierce-oasis-08982.herokuapp.com/

- Endpoints:
  - /recommendations/label
  - /recommendations/artist
  - /recommendations/album
  - /recommendations/track



