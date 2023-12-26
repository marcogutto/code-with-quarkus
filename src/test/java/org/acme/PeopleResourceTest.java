package org.acme;

import static io.restassured.RestAssured.given;

import org.acme.domain.dto.PeopleDTO;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class PeopleResourceTest {

    @Test
    public void testPeopleEndpoint() {

      String endpoint = "/v1/people";

      PeopleDTO peopleDto = new PeopleDTO();
      peopleDto.setName("Luke Skywalker");

      this.validate(endpoint, peopleDto);

      peopleDto.setName("C-3PO");

      this.validate(endpoint, peopleDto);

      peopleDto.setName("R2-D2");
      
      this.validate(endpoint, peopleDto);
        
    }

    private void validate(String endpoint, PeopleDTO peopleDto){

        this.save(endpoint, peopleDto);

        peopleDto = this.findByName(endpoint, peopleDto);

        this.delete(endpoint, peopleDto.getId());

    }

    private void save(String endpoint, PeopleDTO peopleDto){
        
        given()
          .contentType(ContentType.JSON)
          .body(peopleDto)
          .when().post(endpoint)
            .then()
            .statusCode(201);
    }

    private PeopleDTO findByName(String endpoint, PeopleDTO peopleDto){
        
        return given()
          .contentType(ContentType.JSON)
          .when().get(endpoint+"/?search="+peopleDto.getName())
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(PeopleDTO.class);
    }

    private void delete(String endpoint, String id){
        
        given()
          .contentType(ContentType.JSON)
          .when().delete(endpoint+"/"+id)
            .then()
            .statusCode(200);
    }

}