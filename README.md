# Ejecutar con Docker

Para ejecutar en docker localmente, desde la consola construimos la imagen de Docker usando `make`:

```shell
make docker-build
```
Luego, ejecutamos el contenedor junto al de la base de datos:

```shell
make docker-run
```

-------------------- 

# Original Readme

# Authorization with Spring MVC

This sample demonstrates:

- Using the [Okta Spring Boot Starter](https://github.com/okta/okta-spring-boot) to configure a Spring Boot Servlet Resource Server 
- Protecting APIs to only allow authorized access

## Prerequisites

- Java 17 or greater
- An Auth0 account

## Setup

> For complete instructions and additional information, please refer to the [Spring 5 API Security Quickstart](https://auth0.com/docs/quickstart/backend/java-spring-security5) that this sample accompanies.

### Create an Auth0 API

In the [APIs](https://manage.auth0.com/dashboard/#/apis) section of the Auth0 dashboard, click Create API. Provide a name and an identifier for your API, for example, `https://quickstarts/api`. Leave the Signing Algorithm as RS256.

### Configure the project

The project needs to be configured with your Auth0 domain and API Identifier.

To do this, first copy `src/main/resources/application.yml.example` into a new file in the same folder called `src/main/resources/application.yml`, and replace the values with your own Auth0 domain and API Identifier:

```yaml
okta:
  oauth2:
    # Replace with the domain of your Auth0 tenant.
    issuer: https://dev-iwyuw5rnqqzmi8i3.us.auth0.com/
    # Replace with the API Identifier for your Auth0 API.
    audience: {AUDIENCE}
```

## Running

You can run the application using Gradle or Docker.

### Gradle

Linux / MacOS:
```bash
./gradlew clean bootRun
```

Windows:
```bash
gradlew.bat clean bootRun
```

### Docker

Linux / MacOS:
```bash
sh exec.sh
```

Windows:
```
./exec.ps1
```

## Running unit tests

Linux / macOS:

```bash
./gradlew clean test
```

Windows:

```bash
gradlew.cmd clean test
```

## Testing the secured APIs

Using a REST client such as Postman or cURL, issue a `GET` request to `http://localhost:3010/api/public`. You should receive the response:

```json
{"message":"All good. You DO NOT need to be authenticated to call /api/public."}
```

Next, issue a `GET` request to `http://localhost:3010/api/private`. You should receive a `401 Unauthorized` response.

To test that your API is properly secured, you can obtain a test token in the Auth0 Dashboard:

1. Go to the **Machine to Machine Applications** tab for the API you created above.
2. Ensure that your API test application is marked as authorized.
3. Click the **Test** tab, then **COPY TOKEN**.

Issue a `GET` request to the `/api/private` endpoint, this time passing the token you obtained above as an `Authorization` header set to `Bearer YOUR-API-TOKEN-HERE`. You should then see the response:

```json
{"message":"All good. You can see this because you are Authenticated."}
```

Finally, to test that the `/api/private-scoped` is properly protected by the `read:messages` scope, make a `GET` request to the `/api/private-scoped` endpoint using the same token as above. You should see a `403 Forbidden` response, as this token does not possess the `read:messages` scope.

Back in the Auth0 Dashboard:

1. Go to the **Permissions** tab for the API you created above.
2. Add a permission of `read:messages` and provide a description.
3. Go to the **Machine to Machine Applications** tab.
4. Expand your authorized test application, select the `read:messages` scope, then click **UPDATE** and then **CONTINUE**.
5. Click the **Test** tab, then **COPY TOKEN**.

Issue a GET request to `/api/private-scoped`, this time passing the token you obtained above (with the `read:messages` scope) as an `Authorization` header set to `Bearer YOUR-API-TOKEN-HERE`. You should see the response:

```json
{"message":"All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope"}
```
