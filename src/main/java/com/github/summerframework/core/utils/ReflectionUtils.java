package com.github.summerframework.core.utils;

import org.springframework.beans.BeanUtils;

/**
 * Created by renan on 20/02/16.
 */
public class ReflectionUtils {

    public static Object merge ( Object source, Object target ){
        BeanUtils.copyProperties(source, target);
        return source;
    }
}
