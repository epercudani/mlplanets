package com.mlinc.mlplanets.domain.model;

import javax.persistence.Column;

public class Config extends Entity {

    @Column(unique = true, nullable = false)
    String key;

    String Value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
