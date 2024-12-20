# Getting Started

To run this application you need to create `.env` file in root directory with
next environments:

- `DB_HOST` - host of Postgresql database
- `DB_PORT` - port of Postgresql database
- `DB_NAME` - name of Postgresql database
- `DB_USER` - username for Postgresql database
- `DB_PASSWORD` - password for Postgresql database

## Setting Up the Project

1. **Clone the repository:**
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Create the `.env` file:**
   Create a `.env` file in the root directory of the project and add the appropriate environment variables as described
   above.

   For example:

```
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=database
   DB_USER=user
   DB_PASSWORD=password
   ```

3. **Run application:**
   Make sure PostgreSQL is running locally and configured with the provided credentials. 
   Then, you can start the Spring Boot application using:
   ```   sh
   set -a; source .env; set +a;
   ./mvnw spring-boot:run
   ```
   **Note**: `set -a; source .env; set +a;` - command to import environment variables
   
   Swagger: `http://localhost:8080/api/swagger-ui/index.html`

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/)
