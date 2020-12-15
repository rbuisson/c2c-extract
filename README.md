# C2C Migration

Java project to migrate Care 2 Communities' historical data to OpenMRS. This project relies heavily on the [Apache Camel](https://camel.apache.org/) library, [Apache ActiveMQ](https://activemq.apache.org/) and the [OpenMRS DB Sync](https://github.com/openmrs/openmrs-dbsync) project.

<p align="left">
<img src="./readme/camel-long.png" alt="Apcahe Camel" height="70">
&nbsp;&nbsp;&nbsp
<img src="./readme/active-mq-long.png" alt="Apache ActiveMQ" height="80">
&nbsp;&nbsp;
<img src="./readme/openmrs-long.png" alt="OpenMRS Logo" height="80">
</p>

## Run the project:

### Build:
`mvn clean install`
<p align="center">
<img src="./readme/build-successful.png" alt="Build Successful" height="300">
</p>

---

### Run:

#### Start C2C Bahmni server

Run a Bahmni server loaded with the [Bahmni C2C distribution](https://github.com/mekomsolutions/bahmni-distro-c2c).

See [Bahmni Docker]() project instructions for more info.

In short:

- `docker-compose up`

Create a **docker-compose.override.yml** to override the OpenMRS MySQL port on the host so that OpenMRS DB Sync 'reciever' can access it:

**docker-compose.override.yml**
```
[...]
mysql:
  ports:
    - 3306:3306
[...]
```

#### Start ActiveMQ Artemis
C2C Migration uses a standalone instance of ActiveMQ Artemis to store the messages between routes. A sample Artemis Broker is already provided as part of this project. Run it using:

```
cd artemis-broker/bin
artemis run
```

#### Run OpenMRS DB Sync 'receiver'

The [OpenMRS DB Sync](https://github.com/openmrs/openmrs-dbsync) component will be responsible for the loading of the messages generated by the C2C Migration tool, into the OpenMRS database.

See the project README and Sample README for more details, but in short:

Configure the **application-receiver.properties** file, located in **app/src/main/resources/** with the ActiveMQ endpoint, URL and credentials:

```
camel.input.endpoint=activemq:openmrs-db-sync
camel.input.endpoint.file.location=file:/tmp/openmrs-dbsync/file

spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=admin
spring.activemq.password=password
```

Rebuild the application:
```
mvn clean install
```

Run the app:
```
cd sample/sample_springboot_setup/receiver
java -jar -Dspring.profiles.active=receiver ../../../app/target/openmrs-sync-app-1.0-SNAPSHOT.jar
```

#### Start C2C Couchbase DB
Start C2C's Couchbase database loaded with historical data.
We assume that the server is running on `localhost` and the **Data Service** is accessible at the default `11210` port.



#### Run C2C Migration
Then run the program:

```
mvn exec:java -Dexec.mainClass="net.mekomsolutions.c2c.migration.Main"
```
