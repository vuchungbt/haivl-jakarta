package net.blwsmartware.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;

public class HttpUtil {
    private String value;
    public HttpUtil(String value){this.value = value; }

    public <T> T toModel (Class<T> tClass){
        try{
            return new ObjectMapper().readValue(value, tClass);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static HttpUtil of(BufferedReader reader){
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while((line = reader.readLine())!=null){
                sb.append(line);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new HttpUtil(sb.toString());
    }

}
