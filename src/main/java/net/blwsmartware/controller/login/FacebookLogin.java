package net.blwsmartware.controller.login;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.blwsmartware.constant.IConstant;
import net.blwsmartware.model.UserModel;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacebookLogin {
    public static String getToken(String code){
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(IConstant.FACEBOOK_LINK_GET_TOKEN);
            List<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("client_id",IConstant.FACEBOOK_CLIENT_ID));
            params.add(new BasicNameValuePair("client_secret", IConstant.FACEBOOK_CLIENT_SECRET));
            params.add(new BasicNameValuePair("redirect_uri",IConstant.FACEBOOK_REDIRECT_URI));
            params.add(new BasicNameValuePair("code",code));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            try(CloseableHttpResponse response = httpClient.execute(httpPost)){
                String responeString = EntityUtils.toString(response.getEntity());
                JsonObject object = new Gson().fromJson(responeString, JsonObject.class);
                return object.get("access_token").toString().replaceAll("\"","");

            }catch (ParseException e){
                System.out.println(e.getMessage());
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static UserModel getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet(IConstant.FACEBOOK_LINK_GET_USER_INFO + accessToken);

            try(CloseableHttpResponse response = httpClient.execute(httpGet)){
                String responseString = EntityUtils.toString(response.getEntity());
                JsonObject object = new Gson().fromJson(responseString, JsonObject.class);

                UserModel fbAccount = new UserModel();
                fbAccount.setFbID(object.get("id").getAsString());
                fbAccount.setName(object.get("name").getAsString());
                if(object.has("email")){
                    fbAccount.setEmail("email");
                }
                if(object.has("picture")){
                    JsonObject picObject = object.getAsJsonObject("picture")
                            .getAsJsonObject("data");
                    fbAccount.setAvatar(picObject.get("url").getAsString());
                    fbAccount.setThumbnail(picObject.get("url").getAsString());
                }
                return fbAccount;

            }catch (ParseException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
    }
}
