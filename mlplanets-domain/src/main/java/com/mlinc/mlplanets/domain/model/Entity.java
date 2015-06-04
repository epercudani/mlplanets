package com.mlinc.mlplanets.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Entity {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }
}
