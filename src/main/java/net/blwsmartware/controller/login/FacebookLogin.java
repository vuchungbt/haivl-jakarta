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
    public static String getToken(String code) {
        String response =null;
        try {
            response= Request.Post(IConstant.FACEBOOK_LINK_GET_TOKEN)
                    .bodyForm(
                            Form.form()
                                    .add("client_id", IConstant.FACEBOOK_CLIENT_ID)
                                    .add("client_secret", IConstant.FACEBOOK_CLIENT_SECRET)
                                    .add("redirect_uri", IConstant.FACEBOOK_REDIRECT_URI)
                                    .add("code", code)
                                    .build()
                    )
                    .execute().returnContent().asString();
        }
        catch (IOException e ) {
            System.out.println(">>>"+e.getMessage());
        }

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        //System.out.println(jobj.get("access_token").toString().replaceAll("\"", ""));
        return jobj.get("access_token").toString().replaceAll("\"", "");
    }
    public static UserModel getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = IConstant.FACEBOOK_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        JsonObject object = new Gson().fromJson(response , JsonObject.class);
        UserModel fbAccount = new UserModel();
        fbAccount.setFbID(object.get("id").getAsString());
        fbAccount.setName(object.get("name").getAsString());
        if(object.has("email")){
            fbAccount.setEmail((object.get("email").getAsString()));
        }
        if(object.has("picture")){
            JsonObject picObject = object.getAsJsonObject("picture")
                    .getAsJsonObject("data");
            fbAccount.setThumbnail(picObject.get("url").getAsString());
            fbAccount.setAvatar(picObject.get("url").getAsString());
        }
        return fbAccount;
    }
}
