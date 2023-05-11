package org.acme.domain.mapper;

import org.acme.domain.dto.PeopleDTO;
import org.acme.domain.entity.People;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PeopleMapper {

    People toEntity(PeopleDTO people);

    PeopleDTO toDTO(People people);

    static ObjectId map(String value){
        if(value != null){
            return new ObjectId(value);
        }
        return null;
    }

    static String map(ObjectId value){
        if(value != null){
            return value.toString();
        }
        return null;
    }
}