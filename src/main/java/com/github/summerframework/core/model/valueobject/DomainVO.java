package com.github.summerframework.core.model.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by renan on 20/02/16.
 */
public class DomainVO implements Serializable{

    @JsonProperty("uuid")
    private String UUID;

    @JsonProperty("id")
    private long id;

    @JsonProperty("active")
    private boolean active = true;

    @JsonProperty("creation_date")
    private Date creationDate;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
