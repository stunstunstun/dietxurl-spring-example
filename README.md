[![Build Status](https://travis-ci.org/stunstunstun/dietxurl.svg?branch=master)](https://travis-ci.org/stunstunstun/dietxurl)

DIETXURL is a kind of `Shorten URL` service by Spring Boot

## Environments

- JDK 8, Gradle

#### Download & Run

```
$ git clone https://github.com/stunstunstun/dietxurl.git
$ cd dietxurl
$ ./gradlew bootRun
```
> The application creates a server process via embedded Tomcat, so the port on the server is `8080`

#### Test

```
$ ./gradlew test
```

#### Run

Run the application with the Gradle command

```
$ ./gradlew bootRun
```

After running the application, connect to the address below in your browser.

```
http://localhost:8080/
```
