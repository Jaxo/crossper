/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.utils;

import java.net.URLEncoder;
import java.util.Random;
import java.util.StringTokenizer;
/**
 * Helper for String operations
 * 
 */
public class StringHelper {
    
    public static final String UTF8_ENCODING="UTF-8";
    public static final String PLUS_SIGN= "+";
    public static final String QUOTE_SIGN= "\"";
    public static final String SPACE= "%20";
    public static final int ACTIVATION_CODE_LENGTH=10;
    private static Random rng = new Random();

    public static String generateQuotedSearchString( String str) {
        String quotedString = QUOTE_SIGN + str + QUOTE_SIGN;
        try {
//           quotedString = URLEncoder.encode(str, UTF8_ENCODING);
           quotedString = URLEncoder.encode(quotedString, UTF8_ENCODING);
        }catch (Exception ex) {
            
        }
        return quotedString;
    }
    
    public static String generateAddressSearchString (String address) {
        String retString;
        StringBuilder strBuff = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(address);
        while ( tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            strBuff.append(token).append(PLUS_SIGN);
        }
        retString = strBuff.substring(0, strBuff.length()-1);
        return retString;
    }
    
    /**
	 * returns 10 digit random alpha numeric string ( used for activation code)
	 * @return
	 */
	public static String generateActivationCode()
	{
            String characters = "wxyzabcdefghijklmnopqrstuv0123456789";
	    char[] text = new char[ACTIVATION_CODE_LENGTH];
	    for (int i = 0; i < ACTIVATION_CODE_LENGTH; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
}
