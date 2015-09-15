package com.hobbyathletes.hobbyathletes.Framework;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hobbyathletes.hobbyathletes.Object.MyEventRefClass;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tool {

    public static Bitmap getImageFromByte(byte[] image, int offset, int length) {

        if(image!=null) {
            return (BitmapFactory.decodeByteArray(image, offset, length));
        }
        else {
            return null;
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public static Intent addEventCalendar(Intent intent, MyEventRefClass mER){

        Date fulldate = null;

        try {
            fulldate = new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(mER.getDate().toString()+"-"+"00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", fulldate.getTime() );
        intent.putExtra("allDay", true);
        //intent.putExtra("rrule", "FREQ=YEARLY");
        //intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", mER.getName());
        intent.putExtra("description", mER.getType() + "\n" + mER.getLink());
        intent.putExtra("eventLocation", mER.getLocation());

        return intent;
     }

    public static Bitmap getCountryFlag(String str_country, Resources res, int im ){

        final int flag_width = 16;
        final int flag_height = 11;

        InputStream is = res.openRawResource(im);
        Bitmap bm_flag = BitmapFactory.decodeStream(is);

        int[] cutPosition = getCountryCutPosition(str_country.trim().replaceAll(" ", "_"));
        Bitmap resizedBitmap = null;

        if (cutPosition != null) {
            Bitmap croppedBitmap = Bitmap.createBitmap(bm_flag, cutPosition[0], cutPosition[1], flag_width, flag_height);
            resizedBitmap = Bitmap.createScaledBitmap(croppedBitmap, croppedBitmap.getWidth() * 3, croppedBitmap.getHeight() * 3, false);
        }
            return resizedBitmap;
    }

    private static int[] getCountryCutPosition(String str_country) {

        int[] cutPosition;

            if (str_country.equals("Andorra")) { cutPosition = new int[]{16, 0}; }
            else if (str_country.equals("United_Arab_Emirates")) { cutPosition = new int[]{32, 0}; }
            else if (str_country.equals("Afghanistan")) { cutPosition = new int[]{48, 0}; }
            else if (str_country.equals("Antigua_and_Barbuda")) { cutPosition = new int[]{64, 0}; }
            else if (str_country.equals("Anguilla")) { cutPosition = new int[]{80, 0}; }
            else if (str_country.equals("Albania")) { cutPosition = new int[]{96, 0}; }
            else if (str_country.equals("Armenia")) { cutPosition = new int[]{112, 0}; }
            else if (str_country.equals("Netherlands_Antilles")) { cutPosition = new int[]{128, 0}; }
            else if (str_country.equals("Angola")) { cutPosition = new int[]{144, 0}; }
            else if (str_country.equals("Argentina")) { cutPosition = new int[]{160, 0}; }
            else if (str_country.equals("American_Samoa")) { cutPosition = new int[]{176, 0}; }
            else if (str_country.equals("Austria")) { cutPosition = new int[]{192, 0}; }
            else if (str_country.equals("Australia")) { cutPosition = new int[]{208, 0}; }
            else if (str_country.equals("Aruba")) { cutPosition = new int[]{224, 0}; }
            else if (str_country.equals("Azerbaijan")) { cutPosition = new int[]{240, 0}; }
            else if (str_country.equals("Bosnia_Herzegovina")) { cutPosition = new int[]{0, 11}; }
            else if (str_country.equals("Barbados")) { cutPosition = new int[]{16, 11}; }
            else if (str_country.equals("Bangladesh")) { cutPosition = new int[]{32, 11}; }
            else if (str_country.equals("Belgium")) { cutPosition = new int[]{48, 11}; }
            else if (str_country.equals("Burkina_Faso")) { cutPosition = new int[]{64, 11}; }
            else if (str_country.equals("Bulgaria")) { cutPosition = new int[]{80, 11}; }
            else if (str_country.equals("Bahrain")) { cutPosition = new int[]{96, 11}; }
            else if (str_country.equals("Burundi")) { cutPosition = new int[]{112, 11}; }
            else if (str_country.equals("Benin")) { cutPosition = new int[]{128, 11}; }
            else if (str_country.equals("Bermuda")) { cutPosition = new int[]{144, 11}; }
            else if (str_country.equals("Brunei")) { cutPosition = new int[]{160, 11}; }
            else if (str_country.equals("Bolivia")) { cutPosition = new int[]{176, 11}; }
            else if (str_country.equals("Brazil")) { cutPosition = new int[]{192, 11}; }
            else if (str_country.equals("Bahamas")) { cutPosition = new int[]{208, 11}; }
            else if (str_country.equals("Bhutan")) { cutPosition = new int[]{224, 11}; }
            else if (str_country.equals("Bouvet_Islands")) { cutPosition = new int[]{240, 11}; }
            else if (str_country.equals("Botswana")) { cutPosition = new int[]{0, 22}; }
            else if (str_country.equals("Belarus")) { cutPosition = new int[]{16, 22}; }
            else if (str_country.equals("Belize")) { cutPosition = new int[]{32, 22}; }
            else if (str_country.equals("Canada")) { cutPosition = new int[]{48, 22}; }
            else if (str_country.equals("Catalonia")) { cutPosition = new int[]{64, 22}; }
            else if (str_country.equals("Democratic_Republic_of_Congo")) { cutPosition = new int[]{80, 22}; }
            else if (str_country.equals("Central_African_Republic")) { cutPosition = new int[]{96, 22}; }
            else if (str_country.equals("Congo")) { cutPosition = new int[]{112, 22}; }
            else if (str_country.equals("Switzerland")) { cutPosition = new int[]{128, 22}; }
            else if (str_country.equals("Ivory_Coast")) { cutPosition = new int[]{144, 22}; }
            else if (str_country.equals("Cook_Islands")) { cutPosition = new int[]{160, 22}; }
            else if (str_country.equals("Chile")) { cutPosition = new int[]{176, 22}; }
            else if (str_country.equals("Cameroon")) { cutPosition = new int[]{192, 22}; }
            else if (str_country.equals("China")) { cutPosition = new int[]{208, 22}; }
            else if (str_country.equals("Colombia")) { cutPosition = new int[]{224, 22}; }
            else if (str_country.equals("Costa_Rica")) { cutPosition = new int[]{240, 22}; }
            else if (str_country.equals("Cuba")) { cutPosition = new int[]{0, 33}; }
            else if (str_country.equals("Cape_Verde")) { cutPosition = new int[]{16, 33}; }
            else if (str_country.equals("Cyprus")) { cutPosition = new int[]{32, 33}; }
            else if (str_country.equals("Czech_Republic")) { cutPosition = new int[]{48, 33}; }
            else if (str_country.equals("Germany")) { cutPosition = new int[]{64, 33}; }
            else if (str_country.equals("Djibouti")) { cutPosition = new int[]{80, 33}; }
            else if (str_country.equals("Denmark")) { cutPosition = new int[]{96, 33}; }
            else if (str_country.equals("Dominica")) { cutPosition = new int[]{112, 33}; }
            else if (str_country.equals("Dominican_Republic")) { cutPosition = new int[]{128, 33}; }
            else if (str_country.equals("Algeria")) { cutPosition = new int[]{144, 33}; }
            else if (str_country.equals("Ecuador")) { cutPosition = new int[]{160, 33}; }
            else if (str_country.equals("Estonia")) { cutPosition = new int[]{176, 33}; }
            else if (str_country.equals("Egypt")) { cutPosition = new int[]{192, 33}; }
            else if (str_country.equals("Western_Sahara")) { cutPosition = new int[]{208, 33}; }
            else if (str_country.equals("England")) { cutPosition = new int[]{224, 33}; }
            else if (str_country.equals("Eritrea")) { cutPosition = new int[]{240, 33}; }
            else if (str_country.equals("Spain")) { cutPosition = new int[]{0, 44}; }
            else if (str_country.equals("Ethiopia")) { cutPosition = new int[]{16, 44}; }
            else if (str_country.equals("European_Union")) { cutPosition = new int[]{32, 44}; }
            else if (str_country.equals("Finland")) { cutPosition = new int[]{48, 44}; }
            else if (str_country.equals("Fiji")) { cutPosition = new int[]{64, 44}; }
            else if (str_country.equals("Falkland_Islands")) { cutPosition = new int[]{80, 44}; }
            else if (str_country.equals("Micronesia")) { cutPosition = new int[]{96, 44}; }
            else if (str_country.equals("Faroe_Islands")) { cutPosition = new int[]{112, 44}; }
            else if (str_country.equals("France")) { cutPosition = new int[]{128, 44}; }
            else if (str_country.equals("Gabon")) { cutPosition = new int[]{144, 44}; }
            else if (str_country.equals("United_Kingdom")) { cutPosition = new int[]{160, 44}; }
            else if (str_country.equals("Grenada")) { cutPosition = new int[]{176, 44}; }
            else if (str_country.equals("Georgia")) { cutPosition = new int[]{192, 44}; }
            else if (str_country.equals("French_Guinea")) { cutPosition = new int[]{208, 44}; }
            else if (str_country.equals("Ghana")) { cutPosition = new int[]{224, 44}; }
            else if (str_country.equals("Gibraltar")) { cutPosition = new int[]{240, 44}; }
            else if (str_country.equals("Greenland")) { cutPosition = new int[]{0, 55}; }
            else if (str_country.equals("Gambia")) { cutPosition = new int[]{16, 55}; }
            else if (str_country.equals("Guinea")) { cutPosition = new int[]{32, 55}; }
            else if (str_country.equals("Guadeloupe")) { cutPosition = new int[]{48, 55}; }
            else if (str_country.equals("Equatorial_Guinea")) { cutPosition = new int[]{64, 55}; }
            else if (str_country.equals("Greece")) { cutPosition = new int[]{80, 55}; }
            else if (str_country.equals("South_Georgia_and_the_South_Sandwich_Islands")) { cutPosition = new int[]{96, 55}; }
            else if (str_country.equals("Guatemala")) { cutPosition = new int[]{112, 55}; }
            else if (str_country.equals("Guam")) { cutPosition = new int[]{128, 55}; }
            else if (str_country.equals("Guinea-Bissau")) { cutPosition = new int[]{144, 55}; }
            else if (str_country.equals("Guyana")) { cutPosition = new int[]{160, 55}; }
            else if (str_country.equals("Hongkong")) { cutPosition = new int[]{176, 55}; }
            else if (str_country.equals("Heard_Island_and_Mc_Donald_Islands")) { cutPosition = new int[]{192, 55}; }
            else if (str_country.equals("Honduras")) { cutPosition = new int[]{208, 55}; }
            else if (str_country.equals("Croatia")) { cutPosition = new int[]{224, 55}; }
            else if (str_country.equals("Haiti")) { cutPosition = new int[]{240, 55}; }
            else if (str_country.equals("Hungary")) { cutPosition = new int[]{0, 66}; }
            else if (str_country.equals("Indonesia")) { cutPosition = new int[]{16, 66}; }
            else if (str_country.equals("Ireland")) { cutPosition = new int[]{32, 66}; }
            else if (str_country.equals("Israel")) { cutPosition = new int[]{48, 66}; }
            else if (str_country.equals("Isle_of_Man")) { cutPosition = new int[]{48, 88}; }
            else if (str_country.equals("India")) { cutPosition = new int[]{64, 66}; }
            else if (str_country.equals("British_Indian_Ocean_Territory")) { cutPosition = new int[]{80, 66}; }
            else if (str_country.equals("Iraq")) { cutPosition = new int[]{96, 66}; }
            else if (str_country.equals("Iran")) { cutPosition = new int[]{112, 66}; }
            else if (str_country.equals("Iceland")) { cutPosition = new int[]{128, 66}; }
            else if (str_country.equals("Italy")) { cutPosition = new int[]{144, 66}; }
            else if (str_country.equals("Jamaica")) { cutPosition = new int[]{160, 66}; }
            else if (str_country.equals("Jordan")) { cutPosition = new int[]{176, 66}; }
            else if (str_country.equals("Japan")) { cutPosition = new int[]{192, 66}; }
            else if (str_country.equals("Kenya")) { cutPosition = new int[]{208, 66}; }
            else if (str_country.equals("Kyrgystan")) { cutPosition = new int[]{224, 66}; }
            else if (str_country.equals("Cambodia")) { cutPosition = new int[]{240, 66}; }
            else if (str_country.equals("Kiribati")) { cutPosition = new int[]{0, 77}; }
            else if (str_country.equals("Comoros")) { cutPosition = new int[]{16, 77}; }
            else if (str_country.equals("Saint_Kitts_and_Nevis")) { cutPosition = new int[]{32, 77}; }
            else if (str_country.equals("Korea_North")) { cutPosition = new int[]{48, 77}; }
            else if (str_country.equals("Korea_South")) { cutPosition = new int[]{64, 77}; }
            else if (str_country.equals("Kuwait")) { cutPosition = new int[]{80, 77}; }
            else if (str_country.equals("Cayman_Islands")) { cutPosition = new int[]{96, 77}; }
            else if (str_country.equals("Kazakhstan")) { cutPosition = new int[]{112, 77}; }
            else if (str_country.equals("Laos")) { cutPosition = new int[]{128, 77}; }
            else if (str_country.equals("Lebanon")) { cutPosition = new int[]{144, 77}; }
            else if (str_country.equals("Saint_Lucia")) { cutPosition = new int[]{160, 77}; }
            else if (str_country.equals("Liechtenstein")) { cutPosition = new int[]{176, 77}; }
            else if (str_country.equals("Sri_Lanka")) { cutPosition = new int[]{192, 77}; }
            else if (str_country.equals("Liberia")) { cutPosition = new int[]{208, 77}; }
            else if (str_country.equals("Lesotho")) { cutPosition = new int[]{224, 77}; }
            else if (str_country.equals("Lithuania")) { cutPosition = new int[]{240, 77}; }
            else if (str_country.equals("Luxembourg")) { cutPosition = new int[]{0, 88}; }
            else if (str_country.equals("Latvia")) { cutPosition = new int[]{16, 88}; }
            else if (str_country.equals("Libya")) { cutPosition = new int[]{32, 88}; }
            else if (str_country.equals("Morocco")) { cutPosition = new int[]{48, 88}; }
            else if (str_country.equals("Monaco")) { cutPosition = new int[]{64, 88}; }
            else if (str_country.equals("Moldova")) { cutPosition = new int[]{80, 88}; }
            else if (str_country.equals("Montenegro")) { cutPosition = new int[]{96, 88}; }
            else if (str_country.equals("Madagascar")) { cutPosition = new int[]{112, 88}; }
            else if (str_country.equals("Marshall_Islands")) { cutPosition = new int[]{128, 88}; }
            else if (str_country.equals("Macedonia")) { cutPosition = new int[]{144, 88}; }
            else if (str_country.equals("Mali")) { cutPosition = new int[]{160, 88}; }
            else if (str_country.equals("Myanmar")) { cutPosition = new int[]{176, 88}; }
            else if (str_country.equals("Mongolia")) { cutPosition = new int[]{192, 88}; }
            else if (str_country.equals("Macao")) { cutPosition = new int[]{208, 88}; }
            else if (str_country.equals("Northern_Mariana_Islands")) { cutPosition = new int[]{224, 88}; }
            else if (str_country.equals("Martinique")) { cutPosition = new int[]{240, 88}; }
            else if (str_country.equals("Mauritania")) { cutPosition = new int[]{0, 99}; }
            else if (str_country.equals("Montserrat")) { cutPosition = new int[]{16, 99}; }
            else if (str_country.equals("Malta")) { cutPosition = new int[]{32, 99}; }
            else if (str_country.equals("Mauritius")) { cutPosition = new int[]{48, 99}; }
            else if (str_country.equals("Maldives")) { cutPosition = new int[]{64, 99}; }
            else if (str_country.equals("Malawi")) { cutPosition = new int[]{80, 99}; }
            else if (str_country.equals("Mexico")) { cutPosition = new int[]{96, 99}; }
            else if (str_country.equals("Malaysia")) { cutPosition = new int[]{112, 99}; }
            else if (str_country.equals("Mozambique")) { cutPosition = new int[]{128, 99}; }
            else if (str_country.equals("Namibia")) { cutPosition = new int[]{144, 99}; }
            else if (str_country.equals("New_Caledonia")) { cutPosition = new int[]{160, 99}; }
            else if (str_country.equals("Niger")) { cutPosition = new int[]{176, 99}; }
            else if (str_country.equals("Norfolk_Island")) { cutPosition = new int[]{192, 99}; }
            else if (str_country.equals("Nigeria")) { cutPosition = new int[]{208, 99}; }
            else if (str_country.equals("Nicaragua")) { cutPosition = new int[]{224, 99}; }
            else if (str_country.equals("Netherlands")) { cutPosition = new int[]{240, 99}; }
            else if (str_country.equals("Norway")) { cutPosition = new int[]{0, 110}; }
            else if (str_country.equals("Nepal")) { cutPosition = new int[]{16, 110}; }
            else if (str_country.equals("Nauru")) { cutPosition = new int[]{32, 110}; }
            else if (str_country.equals("Niue")) { cutPosition = new int[]{48, 110}; }
            else if (str_country.equals("New_Zealand")) { cutPosition = new int[]{64, 110}; }
            else if (str_country.equals("Oman")) { cutPosition = new int[]{80, 110}; }
            else if (str_country.equals("Panama")) { cutPosition = new int[]{96, 110}; }
            else if (str_country.equals("Peru")) { cutPosition = new int[]{112, 110}; }
            else if (str_country.equals("French_Polynesia")) { cutPosition = new int[]{128, 110}; }
            else if (str_country.equals("Papua_New_Guinea")) { cutPosition = new int[]{144, 110}; }
            else if (str_country.equals("Philippines")) { cutPosition = new int[]{160, 110}; }
            else if (str_country.equals("Pakistan")) { cutPosition = new int[]{176, 110}; }
            else if (str_country.equals("Poland")) { cutPosition = new int[]{192, 110}; }
            else if (str_country.equals("Saint_Pierre_and_Miquelon")) { cutPosition = new int[]{208, 110}; }
            else if (str_country.equals("Pitcairn")) { cutPosition = new int[]{224, 110}; }
            else if (str_country.equals("Puerto_Rico")) { cutPosition = new int[]{240, 110}; }
            else if (str_country.equals("Palestina")) { cutPosition = new int[]{0, 121}; }
            else if (str_country.equals("Portugal")) { cutPosition = new int[]{16, 121}; }
            else if (str_country.equals("Palau")) { cutPosition = new int[]{32, 121}; }
            else if (str_country.equals("Paraguay")) { cutPosition = new int[]{48, 121}; }
            else if (str_country.equals("Qadar")) { cutPosition = new int[]{64, 121}; }
            else if (str_country.equals("Reunion")) { cutPosition = new int[]{80, 121}; }
            else if (str_country.equals("Romania")) { cutPosition = new int[]{96, 121}; }
            else if (str_country.equals("Serbia")) { cutPosition = new int[]{112, 121}; }
            else if (str_country.equals("Russian_Federation")) { cutPosition = new int[]{128, 121}; }
            else if (str_country.equals("Rwanda")) { cutPosition = new int[]{144, 121}; }
            else if (str_country.equals("Saudi_Arabia")) { cutPosition = new int[]{160, 121}; }
            else if (str_country.equals("Solomon_Islands")) { cutPosition = new int[]{176, 121}; }
            else if (str_country.equals("Seychelles")) { cutPosition = new int[]{192, 121}; }
            else if (str_country.equals("scotland")) { cutPosition = new int[]{208, 121}; }
            else if (str_country.equals("Sudan")) { cutPosition = new int[]{224, 121}; }
            else if (str_country.equals("Sweden")) { cutPosition = new int[]{240, 121}; }
            else if (str_country.equals("Singapore")) { cutPosition = new int[]{0, 132}; }
            else if (str_country.equals("Saint_Helena")) { cutPosition = new int[]{16, 132}; }
            else if (str_country.equals("Slovenia")) { cutPosition = new int[]{32, 132}; }
            else if (str_country.equals("Slovakia")) { cutPosition = new int[]{48, 132}; }
            else if (str_country.equals("Sierra_Leane")) { cutPosition = new int[]{64, 132}; }
            else if (str_country.equals("San_Marino")) { cutPosition = new int[]{80, 132}; }
            else if (str_country.equals("Senegal")) { cutPosition = new int[]{96, 132}; }
            else if (str_country.equals("Somalia")) { cutPosition = new int[]{112, 132}; }
            else if (str_country.equals("Suriname")) { cutPosition = new int[]{128, 132}; }
            else if (str_country.equals("ss")) { cutPosition = new int[]{144, 132}; }
            else if (str_country.equals("Sao_Tome_and_Principe")) { cutPosition = new int[]{160, 132}; }
            else if (str_country.equals("El_Salvador")) { cutPosition = new int[]{176, 132}; }
            else if (str_country.equals("Syria")) { cutPosition = new int[]{192, 132}; }
            else if (str_country.equals("Swaziland")) { cutPosition = new int[]{208, 132}; }
            else if (str_country.equals("Turk_and_Caicos_Islands")) { cutPosition = new int[]{224, 132}; }
            else if (str_country.equals("Chad")) { cutPosition = new int[]{240, 132}; }
            else if (str_country.equals("French_Southern_Territories")) { cutPosition = new int[]{0, 143}; }
            else if (str_country.equals("Togo")) { cutPosition = new int[]{16, 143}; }
            else if (str_country.equals("Thailand")) { cutPosition = new int[]{32, 143}; }
            else if (str_country.equals("Tajikistan")) { cutPosition = new int[]{48, 143}; }
            else if (str_country.equals("Tokelau")) { cutPosition = new int[]{64, 143}; }
            else if (str_country.equals("Timor-Leste")) { cutPosition = new int[]{80, 143}; }
            else if (str_country.equals("Turkmenistan")) { cutPosition = new int[]{96, 143}; }
            else if (str_country.equals("Tunesia")) { cutPosition = new int[]{112, 143}; }
            else if (str_country.equals("Tonga")) { cutPosition = new int[]{128, 143}; }
            else if (str_country.equals("Turkey")) { cutPosition = new int[]{144, 143}; }
            else if (str_country.equals("Trinidad_and_Tobago")) { cutPosition = new int[]{160, 143}; }
            else if (str_country.equals("Tuvalu")) { cutPosition = new int[]{176, 143}; }
            else if (str_country.equals("Taiwan")) { cutPosition = new int[]{192, 143}; }
            else if (str_country.equals("Tanzania")) { cutPosition = new int[]{208, 143}; }
            else if (str_country.equals("Ukraine")) { cutPosition = new int[]{224, 143}; }
            else if (str_country.equals("Uganda")) { cutPosition = new int[]{240, 143}; }
            else if (str_country.equals("United_States_Minor_Outlying_Islands")) { cutPosition = new int[]{0, 154}; }
            else if (str_country.equals("United_States")) { cutPosition = new int[]{16, 154}; }
            else if (str_country.equals("Uruguay")) { cutPosition = new int[]{32, 154}; }
            else if (str_country.equals("Uzbekistan")) { cutPosition = new int[]{48, 154}; }
            else if (str_country.equals("Vatican_City")) { cutPosition = new int[]{64, 154}; }
            else if (str_country.equals("Saint_Vincent_and_the_Grenadines")) { cutPosition = new int[]{80, 154}; }
            else if (str_country.equals("Venezuela")) { cutPosition = new int[]{96, 154}; }
            else if (str_country.equals("British_Virgin_Islands")) { cutPosition = new int[]{112, 154}; }
            else if (str_country.equals("Virgin_Islands")) { cutPosition = new int[]{128, 154}; }
            else if (str_country.equals("Vietnam")) { cutPosition = new int[]{144, 154}; }
            else if (str_country.equals("Vanuatu")) { cutPosition = new int[]{160, 154}; }
            else if (str_country.equals("Wales")) { cutPosition = new int[]{176, 154}; }
            else if (str_country.equals("Wallis_and_Fortuna")) { cutPosition = new int[]{192, 154}; }
            else if (str_country.equals("Samoa")) { cutPosition = new int[]{208, 154}; }
            else if (str_country.equals("Yemen")) { cutPosition = new int[]{224, 154}; }
            else if (str_country.equals("Mayotte")) { cutPosition = new int[]{240, 154}; }
            else if (str_country.equals("South_Africa")) { cutPosition = new int[]{0, 165}; }
            else if (str_country.equals("Zambia")) { cutPosition = new int[]{16, 165}; }
            else if (str_country.equals("Zimbabwe")) { cutPosition = new int[]{32, 165}; }

            else {
            System.out.println("Unknown country: " + str_country);
            cutPosition = null;
            }

        return cutPosition;

    }

    public static Date convertToDate(String input) {

        List<SimpleDateFormat>
        dateFormats = new ArrayList<SimpleDateFormat>() {{
            add(new SimpleDateFormat("M/dd/yyyy"));
            add(new SimpleDateFormat("dd.M.yyyy"));
            add(new SimpleDateFormat("M/dd/yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.M.yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.MMM.yyyy"));
            add(new SimpleDateFormat("dd-MMM-yyyy"));
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
            add(new SimpleDateFormat("yyyy-MM-dd-HH:mm"));
        }
        };

        Date date = null;
        if(null == input) {
            return null;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = format.parse(input);
            } catch (ParseException e) {
                System.out.println("Cant format date");
            }
            if (date != null) {
                break;
            }
        }

        return date;
    }

    public static Bitmap cutCircle(Bitmap bitmapimg){

        bitmapimg = cropImage(bitmapimg, 150, 150);

        Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(),
                bitmapimg.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmapimg.getWidth(),
                bitmapimg.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmapimg.getWidth() / 2,
                bitmapimg.getHeight() / 2, bitmapimg.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
        //return Bitmap.createScaledBitmap(output, output.getWidth() * 3, output.getHeight() * 3, false);
    }


    public static Bitmap cropImage(Bitmap srcBmp, int maxWidth, int maxHeight)
    {
        if (srcBmp.getWidth() <= maxWidth && srcBmp.getHeight() <= maxHeight){
            return Bitmap.createScaledBitmap(srcBmp, maxWidth, maxHeight, true);
        }

        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            //System.out.println(">=");
            return Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    //srcBmp.getHeight(),
                    //srcBmp.getHeight()
                    maxWidth,
                    maxHeight
            );

        }else{
            //System.out.println("else <");
            return Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    //srcBmp.getHeight(),
                    //srcBmp.getHeight()
                    maxWidth,
                    maxHeight
            );
        }
    }

    public static ArrayList<String[]> getClassProperties(Class cls, String... str_not_in) {

        ArrayList<String[]> outerArr = new ArrayList<String[]>();

        for (Method method : cls.getMethods()) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {

                String str_in[] = {methodName.substring(3).toLowerCase(), method.getReturnType().toString().split("\\.")[2]};
                boolean foundName = true;

                if (!outerArr.contains(str_in) && !str_in.equals(cls.toString())) {

                    for(int i=0; i<str_not_in.length; i++){
                        if(str_not_in[i].toLowerCase().equals(str_in[0].toLowerCase()) || str_not_in[i].toLowerCase().equals(str_in[1].toLowerCase())){
                            foundName = false;
                        }
                    }
                    if (foundName){
                        outerArr.add(str_in);
                    }

                }
            }
        }
        return outerArr;

    }

    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String isNull(String str_value){

        return (str_value == null || str_value.equals("") ? null : str_value);

    }

    public static String stringToMd5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
