# Booksriver Backend Server 📚🌊🌐

Booksriver Backend Server is a Java SpringBoot application designed to support the [Booksriver Android app](https://github.com/lorisgir/Booksriver). It supports both Docker Compose and Kubernetes for container orchestration.

## Architecture 🏢

The backend is built using a microservices architecture with Eureka for service discovery and a Gateway for routing requests. 

## Technologies Used 🛠️

- Java SpringBoot
- Eureka for service discovery
- Gateway for routing requests
- RabbitMQ for message queue
- Docker Compose and Kubernetes for container orchestration
- PostgreSQL as relational database

## Installation 💻


### How to run with Docker
```
mvn clean install -DskipTests=true
docker-compose build && docker-compose up
```
*It my take some time (~30 secs or more) to correctly register microservices on Eureka*

Try it at http://localhost/user-server/auth/login


### How to run locally
#### Create Database and User
```
sudo -su postgres psql -U postgres

create database booksriver_user;
create database booksriver_book;
create user booksriver with password 'booksriver';
grant all privileges on database booksriver_user to booksriver;
grant all privileges on database booksriver_book to booksriver;
```
#### Run RabbitMQ Server
```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```
#### Optional: 
From the RabbitMQ container terminal run
```
rabbitmqctl add_user admin admin
rabbitmqctl set_user_tags admin administrator
```
Try it at http://localhost:15672/

Credentials are admin admin
#### On IntelliJ
```
Run modules in the following order (important!):
- Eureka Server
- Gateway Server
- User Server
- Book Server
```
*It my take some time (but should be instant) to correctly register microservices on Eureka*

Try it at http://localhost/user-server/auth/login

### Swagger
http://localhost:9001/swagger-ui/index.html?configUrl=/rest-api-docs/swagger-config

http://localhost:9002/swagger-ui/index.html?configUrl=/rest-api-docs/swagger-config


### How to deploy on Kubernetes with local images
Start Minikube
```
minikube start
minikube dashboard
```
It should open the dashboard in a browser. Then in another terminal, build your images:
```
eval $(minikube docker-env)
mvn clean install -DskipTests=true
docker-compose build
```
Then apply Kubernetes files
```
kubectl apply -f kubernetes/
```
In the dashboard, if everything looks green, u good to go!
(It may take a while for user-server and book-server to correctly connect to their database service)

To access swagger, just type these two commands in two separated terminals
```
kubectl port-forward svc/user-server 9001:9001
kubectl port-forward svc/book-server 9002:9002
```

### How to deploy on Kubernetes with remote images
To run on kubernetes using images from docker hub, you need to edit:
1. user-server-depoloyment.yaml
2. book-server-depoloyment.yaml
3. eureka-server-depoloyment.yaml
4. gateway-server-depoloyment.yaml

and replace ```imagePullPolicy: Never``` to ```imagePullPolicy: ""```

and ```image:<local-image>``` to ```image:<remote-image>```

Then procede with the same steps from above, without the commands from ```build your images```

## Contributing 🤝

We welcome contributions from other developers. If you'd like to contribute to the Booksriver Backend Server, follow these steps:

1. Fork the repository on GitHub
2. Clone your fork to your local machine
3. Make your changes and test them thoroughly
4. Submit a pull request with your changes