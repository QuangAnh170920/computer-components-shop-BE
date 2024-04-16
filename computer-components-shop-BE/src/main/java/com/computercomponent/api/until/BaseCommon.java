package com.computercomponent.until;

import com.computercomponent.dto.decodeToken.Example;
import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class BaseCommon {
    public static String getUserName(){
        String tone =  ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader("Authorization");
        if (DataUtil.isNullOrEmpty(tone)){
            return null;
        }
        tone = tone.replace("Bearer ", "");
        return decodeToken(tone);
    }
    public static Long getId(){
        String tone =  ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader("Authorization");
        if (DataUtil.isNullOrEmpty(tone)){
            return null;
        }
        tone = tone.replace("Bearer ", "");
        return DataUtil.safeToLong(decodeTokenToGetId(tone));
    }
    private static String decodeTokenToGetId(String token) {
        try {
            String[] pieces = token.split("\\.");
            String b64payload = pieces[1];
            String jsonString = new String(Base64.decodeBase64(b64payload), "UTF-8");
            Gson g = new Gson();
            Example json = g.fromJson(jsonString, Example.class);
            return json.getIdAdmin();
        }catch (UnsupportedEncodingException e){
            System.out.println("---decodeToken--------" + e);
            return null;
        }
    }
    private static String decodeToken(String token) {
        try {
            String[] pieces = token.split("\\.");
            String b64payload = pieces[1];
            String jsonString = new String(Base64.decodeBase64(b64payload), "UTF-8");
            Gson g = new Gson();
            Example json = g.fromJson(jsonString, Example.class);
            return json.getSub();
        }catch (UnsupportedEncodingException e){
            System.out.println("---decodeToken--------" + e);
            return null;
        }
    }
}
