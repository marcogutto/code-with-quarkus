package org.acme.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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

@MongoEntity(collection = "suco-parcial")
public class SucoParcial extends ReactivePanacheMongoEntity implements Serializable{

    private String descricao;

    private BigDecimal valor;
    
}
