package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Generated;

@Entity
public class Config {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;

    private String key;

    private String value;

    public Config() {

    }

    @Generated
    public Integer getId() {
        return id;
    }

    @Generated
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated
    public String getKey() {
        return key;
    }

    @Generated
    public void setKey(String key) {
        this.key = key;
    }

    @Generated
    public String getValue() {
        return value;
    }

    @Generated
    public void setValue(String value) {
        this.value = value;
    }

    public Config(final int id, final String key, final String value){
        this.id = id;
        this.key = key;
        this.value = value;
    }
}
