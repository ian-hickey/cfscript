
# Dark Matter
_The glue that binds the universe_

There are a number of moving parts in this framework:

Parser for DMscript, a language based on (a subset of) Cfscript that generates Java from CFscript.
The language is based on Cfscript but does not
implement the entire (or even most of) the api.
The add native annotations to the components
methods, and properties. We don't use Cfscript scopes (arguments, variables)
and instead favor Java scoping rules (this keyword).
We do implement most String (len), List (listLen), and Array (arrayAppend) methods.

Most of your favorite APIs are missing and replaced with something else.
However. If you do try it out was you get is a powerful, fast, and feature rich
modern development experience. How fast? Try, fraction of a second startup, built in
server, no configuration dev profiles, built in application metrics. And it will
feel like second nature to anyone who already knows Cfscript.

There are main considerations:
We use JSON-B and quarkus-resteasy-jsonb extension

We import:
jakarta.ws.rs.* (in anything annotated with Path)
java.util.* 
java.lang.*

Imported with wildcards doesn't change the
runtime size.

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



