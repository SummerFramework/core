package com.github.summerframework.core.utils;

import java.lang.reflect.ParameterizedType;

/**
 * Created by renan on 20/02/16.
 */
public class ObjectUtils<T> {

    public Class<T> getGeneric(){
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}