package org.acme.domain.entity;

import java.io.Serializable;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor

@MongoEntity(collection = "people")
public class People extends ReactivePanacheMongoEntity implements Serializable{
    
    private String name;

    private String height;

	private String mass;

	private String hair_color;

	private String skin_color;
	
    private String eye_color;
	
    private String birth_year;
	
    private String gender;

    private String homeworld;
    
}
