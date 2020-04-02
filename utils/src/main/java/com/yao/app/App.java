package com.yao.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        // float和double只能用来做科学计算或者是工程计算，在商业计算中我们要用 java.math.BigDecimal。
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
        
        Float t = 0.62342323f;
        System.out.println(t);


        String RFC1123_DATE = "EEE, dd MMM yyyy HH:mm:ss zzz";
        SimpleDateFormat format = new SimpleDateFormat(RFC1123_DATE, Locale.US);
        System.out.println(format.format(new Date()));

        SimpleDateFormat format2 = new SimpleDateFormat(RFC1123_DATE, Locale.CHINA);
        System.out.println(format2.format(new Date()));

        String dateStr = "Wed, 30 Aug 2017 20:01:15 CST";
        try {
            Date date = format.parse(dateStr);
            System.out.println(format2.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
