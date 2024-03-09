# Chat Application Server

### Introduction
A Java Spring Boot based Chat Application Server

## Technology
The project uses Websocket communication protocol with STOMP being the sub protocol.  
The project is implemented using Java Spring Boot  
Two in-memory users are available with credentials as follows
- user1/hello1
- user2/hello2

The project uses in memory brokers for handling channel messages and MySQL as database for storing messages.

## Features
- Live chat, with up to 2 users, can join a room and send message to each other
- Option to delete the message
- Persists the messages in database table
- Websocket authentication to prevent unauthorized access

## Startup
- Make sure a mysql database service is running with root/root as credentials on port 3306.
  - If not update the connection string and credentials in the application.properties accordingly
  - spring.jpa.generate-ddl is set to true, to create tables at startup
- Application runs on port 8080
- websocket URL: [ws://\<host\>:\<port\>/websocket](ws://localhost:8080/websocket)
- A [web page](http://localhost:8080/?index.html) is available to simulate the chat application
- Refer [section](#Technology) above for credentials


### Code Structure
```
├──gradle
│   └──wrapper
├──azure-pipelines.yml -- Template for Azure Pipeline
├──build.gradle -- Gradle Build configuration
└──src
├──main
│   ├──java -- Java Source Code
│   │   └──com
│   │       └──backend
│   │           └──chatserver
│   │               ├──config
│   │               ├──controller
│   │               ├──dao
│   │               │  ├──entity
│   │               │  └── repository
│   │               ├──schema
│   │               └──service
│   └──resources -- For serving Chat WebPage
│       ├──static
│       │   ├──css
│       │   ├──images
│       │   └──js
│       └──templates
└──test -- Java Unit tests
├──java
│   └──com
│       └──backend
│           └──chatserver
│               ├──controller
│               └──service
└──resources
```