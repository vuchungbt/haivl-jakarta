package net.blwsmartware.controller.login;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.blwsmartware.constant.IConstant;
import net.blwsmartware.model.UserModel;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class FacebookLogin {
    public static String getToken(String code){
        String respone = null;
        try{
            respone = Request.Post(IConstant.FACEBOOK_LINK_GET_TOKEN)
                    .bodyForm(
                            Form.form()
                                    .add("client_id", IConstant.FACEBOOK_CLIENT_ID)
                                    .add("client_secret", IConstant.FACEBOOK_CLIENT_SECRET)
                                    .add("redirect_uri", IConstant.FACEBOOK_REDIRECT_URI)
                                    .add("code",code)
                                    .build()
                    )
                    .execute().returnContent().asString();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        JsonObject json = new Gson().fromJson(respone, JsonObject.class);
        return json.get("access_token").toString().replaceAll("\"","");
    }
    public static UserModel getUserInfo(final String accessToken)
            throws ClientProtocolException, IOException {
        String link = IConstant.FACEBOOK_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        JsonObject jsonObject = new Gson().fromJson(response,JsonObject.class);
        UserModel fbAccount = new UserModel();
        fbAccount.setName(jsonObject.get("name").getAsString());
        fbAccount.setFbID(jsonObject.get("id").getAsString());
        if(jsonObject.has("email")){
            fbAccount.setEmail(jsonObject.get("email").getAsString());
        }
        if(jsonObject.has("picture")){
            JsonObject json = jsonObject.getAsJsonObject("picture")
                    .getAsJsonObject("data");
            fbAccount.setAvatar(json.get("url").getAsString());
            fbAccount.setThumbnail(json.get("url").getAsString());
        }
        return fbAccount;
    }
}
