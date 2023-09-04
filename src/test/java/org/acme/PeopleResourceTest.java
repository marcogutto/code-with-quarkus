package org.acme;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class PeopleResourceTest {

    @Test
    public void testSaveEndpoint() {

        String people = "{ \"name\":\"Luke Skywalker\",\"height\":\"172\",\"mass\":\"77\",\"hair_color\":\"blond\",\"skin_color\":\"fair\",\"eye_color\":\"blue\",\"birth_year\":\"19BBY\",\"gender\":\"male\",\"homeworld\":\"https://swapi.dev/api/planets/1/\"}";

        given()
          .contentType(ContentType.JSON)
          .body(people)
          .when().post("/v1/people")
            .then()
            .statusCode(201);
    }

}