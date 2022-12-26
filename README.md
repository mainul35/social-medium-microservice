# social-medium-microservice
Remake of social medium project as microservice, a demonstration project for pondit batch 4. The Codes were initially committed in the pondit-b4-servlet-and-spring repo.

## How to Run:
So far the works of the following services and applications are in a workable phase.
- app-social-medium - The angular based front-end application, consuming ``bff-social-medium``

To run the app-social-medium, `cd` to the root directory and in command line, run `ng serve`

- bff-social-medium - The BFF microservice, which is so far consuming ``bs-userinfo`` and ``bs-auth``

To run the bff-social-medium, go to the root directory from command line, and run ``mvn spring-boot:run``

- bs-userinfo - Is responsible for user signup and providing user information

To run bs-userinfo, go to the root directory of the project and run the `docker-compose.yml` file. The docker-compose file has database and RabbitMQ deployment descriptor too written in it. So it will make the DB, RMQ and the BS up and running together

- bs-auth - Is responsible for providing JWT token based authentication and authorization feature

To run bs-auth, you first need the DB of bs-userinfo started first. Once the DB is up and running, you can just go to the root path and open a command prompt and run ``mvn spring-boot:run``

In case you have any problem running the application, don't hesitate to ask in mail: mainuls18@gmail.com

