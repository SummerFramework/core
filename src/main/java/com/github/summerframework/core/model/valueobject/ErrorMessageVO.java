package com.github.summerframework.core.model.valueobject;

import java.io.Serializable;

/**
 * Created by renan on 21/02/16.
 */
public class ErrorMessageVO implements Serializable {

    private String field;

    private String error;


    public ErrorMessageVO(){

    }

    public ErrorMessageVO(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}