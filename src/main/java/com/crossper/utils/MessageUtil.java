/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.utils;

import com.crossper.provider.ApplicationContextProvider;
import java.util.Locale;
import org.springframework.context.ApplicationContext;

public class MessageUtil {
    
    public static String getMessage(String key) {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        String msg = ctx.getMessage(key, null, "System Exception", Locale.ENGLISH);
        return msg;
    }
}
