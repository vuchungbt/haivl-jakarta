package net.blwsmartware.util;


import jakarta.servlet.http.HttpServletRequest;

public class RouterUtil {
    public static String getRouter(int index, HttpServletRequest request){
        String[] uri = request.getRequestURI().split("/");
        String link = "";
        if(uri.length>index){
            link=uri[index];
        }
        return link;
    }
}
