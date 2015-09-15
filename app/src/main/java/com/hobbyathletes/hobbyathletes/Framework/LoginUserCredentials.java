package com.hobbyathletes.hobbyathletes.Framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

import com.hobbyathletes.hobbyathletes.Object.UserCredentials;
import com.hobbyathletes.hobbyathletes.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginUserCredentials {

public static UserCredentials loginTo(String str_url, String str_url_login, String str_username, String str_password, String str_fb_id){
        try{
            JSONObject json_response = null;
            int int_fb = 0;

            if(str_username != null && str_password != null) {
                System.out.println("---login-start---");
                System.out.println("URL : " + str_url_login + " Credential : " + str_username + " - " + str_password);

                List<NameValuePair> nameValuePairs;
                //HttpResponse response;

                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(str_url_login); // make sure the url is correct.
                //add your data
                nameValuePairs = new ArrayList<NameValuePair>(2);
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                nameValuePairs.add(new BasicNameValuePair("username", str_username));
                nameValuePairs.add(new BasicNameValuePair("password", str_password));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //Execute HTTP Post Request
                //response = httpclient.execute(httppost);

                // edited by James from coderzheaven.. from here....
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                final String str_response = httpclient.execute(httppost, responseHandler);

            /*
            List<Cookie> cookies = httpclient.getCookieStore().getCookies();
            System.out.println("cookie0:" + cookies);
            if(cookies != null)
            {
                for(Cookie cookie : cookies)
                {
                    String cookieString = cookie.getName() + "=" + cookie.getValue() + "; domain=" + cookie.getDomain();
                    CookieManager.getInstance().setCookie(cookie.getDomain(), cookieString);
                    System.out.println("cookie");
                }
            }
*/
                System.out.println("Response : " + str_response);

                json_response = new JSONObject(str_response);
                int_fb = 0;
            }
            if(str_fb_id != null){
                json_response = Json.getJSONFromUrl(str_url_login + "?fbID=" + str_fb_id);
                if(json_response.getString("success").equals("0")){
                    System.out.println("Facebook login failed with ID: " + str_fb_id);
                }
                int_fb = 1;
            }

            if(json_response.getString("success").equals("1")){

                UserCredentials uC = new UserCredentials();

                uC.setUsername(json_response.getString("username"));
                uC.setMd5password(json_response.getString("pwd_md5"));
                uC.setUserId(Integer.parseInt(json_response.getString("userID")));
                uC.setActive(Integer.parseInt(json_response.getString("active")));
                uC.setSuccess(Integer.parseInt(json_response.getString("success")));
                uC.setFirstname(json_response.getString("firstname"));
                uC.setLastname(json_response.getString("lastname"));
                uC.setGender(json_response.getString("gender"));
                uC.setCountry(json_response.getString("country"));
                uC.setCity(json_response.getString("city"));
                uC.setBday(json_response.getString("bday"));
                uC.setLast_modified(json_response.getString("last_modified"));
                uC.setAboutme(Html.fromHtml(json_response.getString("aboutme")).toString());
                uC.setIntFb(int_fb);

                String fragment = "/profile/";

                uC.setThumb(json_response.getString("thumb"));
                if(!json_response.getString("thumb").equals("")) {
                    uC.setImageThump(loadBitmap(str_url + fragment + uC.getThumb()));
                }

                uC.setPic(json_response.getString("pic"));
                if(!json_response.getString("pic").equals("")) {
                    uC.setImagePic(loadBitmap(str_url + fragment + uC.getPic()));
                }
                else {
                        //no profile pic
                        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.unknown);
                        uC.setImagePic(bitmap);

                        //Drawable myDrawable = Resources.getSystem().getDrawable(R.drawable.unknown);
                        //uC.setImagePic(((BitmapDrawable) myDrawable).getBitmap());

                }

                System.out.println(json_response.getString("thumb"));
                System.out.println("---login-end---");

                return uC;
            }
        }catch(Exception e){
            //dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    return null;
    }

    private static Bitmap loadBitmap(String url) {
        // initialize the default HTTP client object
        final DefaultHttpClient client = new DefaultHttpClient();

        Bitmap image = null;

        //forming a HttoGet request
        final HttpGet getRequest = new HttpGet(url);
        try {

            HttpResponse response = client.execute(getRequest);

            //check 200 OK for success
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);
                return null;

            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    // getting contents from the stream
                    inputStream = entity.getContent();

                    // decoding stream data back into image Bitmap that android understands
                    image = BitmapFactory.decodeStream(inputStream);


                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // You Could provide a more explicit error message for IOException
            getRequest.abort();
            Log.e("ImageDownloader", "Something went wrong while" +
                    " retrieving bitmap from " + url + " " + e.toString());
        }

        return image;

    }

    public static boolean passwordValid(String str_url, UserCredentials uC){
        try{
            String url = str_url + "?username=" + uC.getUsername() + "&password=" + uC.getMd5password();
            System.out.println("URL : " + url);
            JSONObject json_pw = Json.getJSONFromUrl(url);

            System.out.println("json: " + json_pw.getString("success"));
            return Integer.parseInt(json_pw.getString("success")) == 1;

        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
        return false;
    }

}
