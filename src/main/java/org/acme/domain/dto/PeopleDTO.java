package org.acme.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor

public class PeopleDTO {

    private String id;
    
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
