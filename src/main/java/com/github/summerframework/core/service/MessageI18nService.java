package com.github.summerframework.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by renan on 28/02/16.
 */

@Service
public class MessageI18nService {

    private final MessageSource messageSource;

    @Autowired
    public MessageI18nService(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public String get(String key){
        return this.messageSource.getMessage( key, null, LocaleContextHolder.getLocale());
    }

}
