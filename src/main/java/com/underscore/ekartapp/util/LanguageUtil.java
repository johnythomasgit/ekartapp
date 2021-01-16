/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author Libeesh
 */
public class LanguageUtil {

    @Autowired
    private MessageSource messageSource;

    public String getTranslatedText(String msgKey, Object[] obj, String lang) {
        return messageSource.getMessage(msgKey, obj, Locale.ENGLISH);
    }

    public String getTranslatedText(String msgKey) {
        return messageSource.getMessage(msgKey, null, Locale.ENGLISH);
    }
}
