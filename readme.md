
# Dark Matter
_The glue that binds the universe_

There are a number of moving parts in this framework:

Parser for DMscript, a language based on (a subset of) cfscript that generates Java from CFscript.
The language is based on cfscript but does not
implement the entire (or even most of) the api.
The add native annotations to the components
methods, and properties. We don't use cfscript scopes (arguments, variables)
and instead favor Java scoping rules (this keyword).
We do implement most String (len), List (listLen), and Array (arrayAppend) methods.

Most of your favorite APIs are missing and replaced with something else.
However. If you do try it out was you get is a powerful, fast, and feature rich
modern development experience. How fast? Try, fraction of a second startup, built in
server, no configuration dev profiles, built in application metrics. And it will
feel like second nature to anyone who already knows cfscript.

There are a few key differences:
We favor annotations over attributes for most things.
Arrays are 0 based
Struct access uses str["key"] notation, and not str.key notation.  * future release
For struct you can use the default methods available on the struct (getKey(), isEmpty() etc)
There is no ## based string interpolation. * future release
String concat happens using + not &
The component name defaults to the file, but you can overwrite that using name="ComponentName"

## Logging
You can log from anywhere using Log class.

` Log.info("Simple!"); `

#### Log Levels
* OFF
A special level to use in configuration in order to turn off logging.

* FATAL
A critical service failure or complete inability to service requests of any kind.

* ERROR
A significant disruption in a request or the inability to service a request.

* WARN
A non-critical service error or problem that may not require immediate correction.

* INFO
Service lifecycle events or important related very low-frequency information.

* DEBUG
Messages that convey extra information regarding lifecycle or non-request-bound events, useful for debugging.

* TRACE
Messages that convey extra per-request debugging information that may be very high frequency.

* ALL
A special level to use in configuration to turn on logging for all messages, including custom levels.


There are main decisions made during this process:
We use JSON-B and quarkus-resteasy-jsonb extension

We import:
jakarta.ws.rs.* (in anything annotated with Path)
java.util.* 
java.lang.*
cfscript.library.StdLib which contains (case-sensitive Cfscript library methods such as arrayLen and trim.)

ORM:
We use application.properties to configure it.


Annotate entities with @Entity
io.quarkus:quarkus-hibernate-orm is the extension

Use a DB option:
quarkus-jdbc-db2 for IBM DB2
quarkus-jdbc-derby for Apache Derby
quarkus-jdbc-h2 for H2
quarkus-jdbc-mariadb for MariaDB
quarkus-jdbc-mssql for Microsoft SQL Server
quarkus-jdbc-mysql for MySQL
quarkus-jdbc-oracle for Oracle Database
quarkus-jdbc-postgresql for PostgreSQL

application.properties example:
## datasource configuration
quarkus.datasource.db-kind = postgresql
quarkus.datasource.username = hibernate
quarkus.datasource.password = hibernate
quarkus.datasource.jdbc.url = jdbc:postgresql://localhost:5432/hibernate_db

### drop and create the database at startup (use `update` to only update the schema)
quarkus.hibernate-orm.database.generation=drop-and-create

POM:
<!-- Hibernate ORM specific dependencies -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-orm</artifactId>
</dependency>

<!-- JDBC driver dependencies -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jdbc-postgresql</artifactId>
</dependency>


Services should be annotated with @ApplicationScope.
@ApplicationScoped
public class SomeService { }

## Annotation List



