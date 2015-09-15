package com.hobbyathletes.hobbyathletes.Object;

import android.graphics.Bitmap;
import com.hobbyathletes.hobbyathletes.Framework.Tool;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserCredentials implements Serializable  {

    private String username;
    private String md5password;
    private Integer active;
    private Integer success;
    private Integer userId;

    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String country;
    private String city;
    private Date bday;
    private String aboutme;

    private Date last_modified;

    private String thumb;
    private byte[] imageThump;
    private String pic;
    private byte[] imagePic;

    private int intFb;
    private ArrayList<MyEventClass> myEventList = new ArrayList<MyEventClass>();

    public void userCredentials(){
        this.username = null;
        this.md5password = null;
        this.active = 0;
        this.success = 0;
        this.userId = null;
        this.thumb = "";
        this.imageThump = null;
        this.imagePic = null;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMd5password() {
        return md5password;
    }

    public void setMd5password(String md5password) {
        this.md5password = md5password;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Bitmap getImageThump() {
        if(this.imageThump!=null) {
            //return (BitmapFactory.decodeByteArray(this.image, 0, this.image.length));
            return Tool.getImageFromByte(this.imageThump, 0, this.imageThump.length);
        }
        else {
            return null;
        }
    }

    public void setImageThump(Bitmap imageThump) {
        this.imageThump = Tool.getBitmapAsByteArray(imageThump);
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Bitmap getImagePic() {
        //return image;
        if(this.imagePic!=null) {
            //return (BitmapFactory.decodeByteArray(this.image, 0, this.image.length));
            return Tool.getImageFromByte(this.imagePic, 0, this.imagePic.length);
        }
        else {
            return null;
        }
    }

    public void setImagePic(Bitmap imagePic) {
        this.imagePic = Tool.getBitmapAsByteArray(imagePic);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    public void setBday(String bday) {

        Date fulldate = null;

        try {
           fulldate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(bday + " " + "00:00");
           // fulldate = Tool.convertToDate(bday);
        } catch (ParseException e) {
            e.printStackTrace();
         }
        //System.out.println("Bday: " + fulldate.toString());
        this.bday = fulldate;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }

    public void setLast_modified(String last_modified) {

        Date fulldate = null;

        try {
            fulldate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(last_modified);
          } catch (ParseException e) {
            e.printStackTrace();
        }

        this.last_modified = fulldate;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public int getIntFb() {
        return intFb;
    }

    public void setIntFb(int intFb) {
        this.intFb = intFb;
    }

    public boolean isEmpty(){
        return (userId == null && username == null);
    }

    public ArrayList<MyEventClass> getMyEventList() {
        return myEventList;
    }

    public void setMyEventList(ArrayList<MyEventClass> myEventList) {
        this.myEventList = myEventList;
    }

    public void addMyEvent(MyEventClass myEvent) {
        this.myEventList.add(myEvent);
    }

    public MyEventClass getMyEvent(int index) {
        return this.myEventList.get(index);
    }
}
