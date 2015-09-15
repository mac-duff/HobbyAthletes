package com.hobbyathletes.hobbyathletes.Object;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyEventRefClass implements Serializable, Cloneable {

    /*"name":"Dextro Energy Triathlon 2009",
    "location":"Germany, Hamburg",
    "date":"2009-07-26",
    "type":"triathlon",
    "theme":"http://hobbyathletes.com/en/imgs/triathlon_34.png",
    "BIB":"",
    "link":"http://hobbyathletes.com/tri/Dextro-Energy-Triathlon-2009/main-169.html",
    "myevents_ref":"160",
    "swim_dist":"?",
    "cycle_dist":"?","run_dist":"?","swim_time":"?","cycle_time":"?","run_time":"?","trans1_time":"?",
    "trans2_time":"?","total_time":"?","total_dist":"?"}]}
     */

    private Integer myevents_ref;
    private String name;
    private String location;
    private Date date;
    private String type;
    private String theme;
    private String link;
    private Integer bib;
    private String remarks;
    private String swim_dist;
    private String cycle_dist;
    private String run_dist;
    private String swim_time;
    private String cycle_time;
    private String run_time;
    private String trans1_time;
    private String trans2_time;
    private String total_time;
    private String total_dist;
    private ArrayList<HashMap<String, String>> image = new ArrayList<HashMap<String, String>>();

    private List<MyEventRefChangedValues> elements = new ArrayList<> ();

    MyEventRefName myEventRefName = new MyEventRefName();

    public static class MyEventRefName implements Serializable{

        final String bib = "My Startnumber";
        final String swim_dist = "Distance Swim";
        final String cycle_dist = "Distance Cycle";
        final String run_dist = "Distance Run";
        final String swim_time = "Swim time";
        final String cycle_time = "Cycle time";
        final String run_time = "Run time";
        final String trans1_time = "Transition 1 time";
        final String trans2_time = "Transition 2 time";
        final String total_time = "Total Time";
        final String total_dist = "Total Distance";

        public String getBib() {
            return bib;
        }

        public String getSwim_dist() {
            return swim_dist;
        }

        public String getCycle_dist() {
            return cycle_dist;
        }

        public String getRun_dist() {
            return run_dist;
        }

        public String getSwim_time() {
            return swim_time;
        }

        public String getCycle_time() {
            return cycle_time;
        }

        public String getRun_time() {
            return run_time;
        }

        public String getTrans1_time() {
            return trans1_time;
        }

        public String getTrans2_time() {
            return trans2_time;
        }

        public String getTotal_time() {
            return total_time;
        }

        public String getTotal_dist() {
            return total_dist;
        }

        public String getColumnName(String str_method){

            str_method = str_method.toLowerCase();

            for(Field f : this.getClass().getDeclaredFields()) {

                if (f.getName().equals(str_method)) {

                    Object value = new Object();
                    try {
                        value = f.get(this);
                        if(value != null && value.toString().length() != 0){
                            return value.toString();
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("MethodClass "+f.getName() + " = " + value);

                }
            }


            if(bib.getClass().getFields().getClass().getName().equals(str_method)){
                return bib;
            }
            if(swim_dist.toString().equals(str_method)){
                return swim_dist;
            }
            if(cycle_dist.getClass().getName().equals(str_method)){
                return cycle_dist;
            }
            if(run_dist.getClass().getName().equals(str_method)){
                return run_dist;
            }
            if(swim_time.getClass().getName().equals(str_method)){
                return swim_time;
            }
            if(cycle_time.getClass().getName().equals(str_method)){
                return cycle_time;
            }
            if(run_time.getClass().getName().equals(str_method)){
                return run_time;
            }
            if(trans1_time.getClass().getName().equals(str_method)){
                return trans1_time;
            }
            if(trans2_time.getClass().getName().equals(str_method)){
                return trans2_time;
            }
            if(total_time.getClass().getName().equals(str_method)){
                return total_time;
            }
            if(total_dist.getClass().getName().equals(str_method)){
                return total_dist;
            }

            return str_method;
        }
    }

    public MyEventRefClass(){

    }

    public MyEventRefClass(String name, String location, String date, String type, String theme, String link, Integer myevents_ref, Integer bib, String remarks, String swim_dist, String cycle_dist, String run_dist, String swim_time, String cycle_time, String run_time, String trans1_time, String trans2_time, String total_time, String total_dist, ArrayList<HashMap<String, String>> image) {
        this.name = name;
        this.location = location;
        setDate(date);
        this.type = type;
        this.theme = theme;
        this.link = link;
        this.myevents_ref = myevents_ref;
        this.bib = bib;
        this.remarks = remarks;
        this.swim_dist = swim_dist;
        this.cycle_dist = cycle_dist;
        this.run_dist = run_dist;
        this.swim_time = swim_time;
        this.cycle_time = cycle_time;
        this.run_time = run_time;
        this.trans1_time = trans1_time;
        this.trans2_time = trans2_time;
        this.total_time = total_time;
        this.total_dist = total_dist;
        if(!image.isEmpty()) {
            this.image = image;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        Date fulldate = null;

        try {
            fulldate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date+" "+"00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = fulldate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getMyevents_ref() {
        return myevents_ref;
    }

    public void setMyevents_ref(Integer myevents_ref) {
        this.myevents_ref = myevents_ref;
    }

    public Integer getBib() {
        return bib;
    }

    public void setBib(Integer bib) {
        this.bib = bib;
    }

    public String getSwim_dist() {
        return swim_dist;
    }

    public void setSwim_dist(String swim_dist) {
        this.swim_dist = swim_dist;
    }

    public String getCycle_dist() {
        return cycle_dist;
    }

    public void setCycle_dist(String cycle_dist) {
        this.cycle_dist = cycle_dist;
    }

    public String getRun_dist() {
        return run_dist;
    }

    public void setRun_dist(String run_dist) {
        this.run_dist = run_dist;
    }

    public String getSwim_time() {
        return swim_time;
    }

    public void setSwim_time(String swim_time) {
        this.swim_time = swim_time;
    }

    public String getCycle_time() {
        return cycle_time;
    }

    public void setCycle_time(String cycle_time) {
        this.cycle_time = cycle_time;
    }

    public String getRun_time() {
        return run_time;
    }

    public void setRun_time(String run_time) {
        this.run_time = run_time;
    }

    public String getTrans1_time() {
        return trans1_time;
    }

    public void setTrans1_time(String trans1_time) {
        this.trans1_time = trans1_time;
    }

    public String getTrans2_time() {
        return trans2_time;
    }

    public void setTrans2_time(String trans2_time) {
        this.trans2_time = trans2_time;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getTotal_dist() {
        return total_dist;
    }

    public void setTotal_dist(String total_dist) {
        this.total_dist = total_dist;
    }

    public ArrayList<HashMap<String, String>> getImage() {
        return image;
    }

    public void setImage(ArrayList<HashMap<String, String>> image) {
        if(image != null) {
            this.image = image;
        } else {
            this.image = new ArrayList<HashMap<String, String>>();
        }
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String toString(){
        return " <name>: " + name
            + " <location> : " +location
            + " <date> : " + date
            + " <type> : " + type
            + " <theme> : " + theme
            + " <link> : " + link
            + " <ref> : " + myevents_ref
            + " <bib> : " + bib
            + " <remarks> : " + remarks
            + " <swim_dist> : " + swim_dist
            + " <cycle_dist> : " + cycle_dist
            + " <run_dist> : " + run_dist
            + " <swim_time> : " + swim_time
            + " <cycle_time> : " + cycle_time
            + " <cycle_time> : " + run_time
            + " <trans1_time> : " + trans1_time
            + " <trans2_time> : " + trans2_time
            + " <total_time> : " + total_time
            + " <total_dist> : " + total_dist;
    }

    public int compareTo(Object other) {

        MyEventRefClass other_merc = (MyEventRefClass)other;

        int result = (this.getName().compareTo(other_merc.getName())) ;

        if (result == 0){
            result = (this.getLocation().compareTo(other_merc.getLocation())) ;
            if (result == 0){
                result = (this.getDate().compareTo(other_merc.getDate())) ;
                if (result == 0){
                    result = (this.getType().compareTo(other_merc.getType())) ;
                    if (result == 0){
                        result = (this.getTheme().compareTo(other_merc.getTheme())) ;
                        if (result == 0){
                            result = (this.getLink().compareTo(other_merc.getLink())) ;
                            if (result == 0){
                                result = (this.getMyevents_ref().compareTo(other_merc.getMyevents_ref())) ;
                                if (result == 0){
                                    result = (this.getBib().compareTo(other_merc.getBib())) ;
                                    if (result == 0){
                                        result = (this.getRemarks().compareTo(other_merc.getRemarks())) ;
                                        if (result == 0){
                                            result = (this.getSwim_dist().compareTo(other_merc.getSwim_dist())) ;
                                            if (result == 0){
                                                result = (this.getCycle_dist().compareTo(other_merc.getCycle_dist())) ;
                                                if (result == 0){
                                                    result = (this.getRun_dist().compareTo(other_merc.getRun_dist())) ;
                                                    if (result == 0){
                                                        result = (this.getSwim_time().compareTo(other_merc.getSwim_time())) ;
                                                        if (result == 0){
                                                            result = (this.getCycle_time().compareTo(other_merc.getCycle_time())) ;
                                                            if (result == 0){
                                                                result = (this.getRun_time().compareTo(other_merc.getRun_time())) ;
                                                                if (result == 0){
                                                                    result = (this.getTrans1_time().compareTo(other_merc.getTrans1_time())) ;
                                                                    if (result == 0){
                                                                        result = (this.getTrans2_time().compareTo(other_merc.getTrans2_time())) ;
                                                                        if (result == 0){
                                                                            result = (this.getTotal_time().compareTo(other_merc.getTotal_time())) ;
                                                                            if (result == 0){
                                                                                result = (this.getTotal_dist().compareTo(other_merc.getTotal_dist())) ;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
/*
        int val = this.str_brand.compareTo(ocar.str_brand);
        if (val == 0) {
            val = this.str_name.compareTo(ocar.str_name);
        }
        return val;
        */
    }

    public boolean isEmpty(){
        return (myevents_ref == null || name == "");
    }

    public List<MyEventRefChangedValues> findMatchingValues(MyEventRefClass secondInstance) {
        try {
            Class firstClass = this.getClass();
            Method[] firstClassMethodsArr = firstClass.getMethods();

            Class secondClass = secondInstance.getClass();
                Method[] secondClassMethodsArr = secondClass.getMethods();


                for (int i = 0; i < firstClassMethodsArr.length; i++) {
                Method firstClassMethod = firstClassMethodsArr[i];
                // target getter methods.
                if(firstClassMethod.getName().startsWith("get")
                        && ((firstClassMethod.getParameterTypes()).length == 0)
                        && (!(firstClassMethod.getName().equals("getClass")))
                        ){

                    Object firstValue;
                    firstValue = firstClassMethod.invoke(this, null);

                    for (int j = 0; j < secondClassMethodsArr.length; j++) {
                        Method secondClassMethod = secondClassMethodsArr[j];
                        if(secondClassMethod.getName().equals(firstClassMethod.getName())){

                            Object secondValue = secondClassMethod.invoke(secondInstance, null);

                            if(!firstValue.equals(secondValue)){
                                System.out.println("Method: "+firstClassMethod.getName().substring(3) + " Value1: "+firstValue+ " Value2: " + secondValue);
                                elements.add(new MyEventRefChangedValues(myEventRefName.getColumnName(firstClassMethod.getName().substring(3)), firstValue.toString(), secondValue.toString()));
                            }
                        }
                    }
                }
            }

           return elements;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class MyEventRefChangedValues implements Serializable {
        final String str_name;
        String str_value_old;
        String str_value_new;

        public MyEventRefChangedValues (String str_name, String str_value_old, String str_value_new) {
            this.str_name = str_name;
            this.str_value_old = str_value_old;
            this.str_value_new = str_value_new;
        }

        public String getName() {
            return str_name;
        }

        public String getValueOld() {
            return str_value_old;
        }

        public void setValueOld(String str_value_old) {
            this.str_value_old = str_value_old;
        }

        public String getValueNew() {
            return str_value_new;
        }

        public void setValueNew(String str_value_new) {
            this.str_value_new = str_value_new;
        }

    }

    public Object clone() throws CloneNotSupportedException {

        return super.clone();

    }

    public MyEventRefName getMyEventRefName(){
        return myEventRefName;
    }

}
