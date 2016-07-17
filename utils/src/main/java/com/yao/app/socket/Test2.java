package com.yao.app.socket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {

    public static void main(String[] args) {
        String respContent = "<input type=\"hidden\" name=\"lt\" value=\"LT-10122-GBwIdQnoKAeLeUFWF04jZ1nQAmay7W\" />\n\t\t\t \t\t";
        Pattern p = Pattern.compile("name=\"lt\"\\s+value=\"(\\w+-\\d+-\\w+)\"");
        
//        String respContent = "<input type=\"hidden\" name=\"lt\" value=\"LT-6551-p5p6SSzuGRiFVlzmlPEUPsmeZAEzu0\">";
//        Pattern p = Pattern.compile("name=\"(\\w{2})\"\\s+");
        Matcher m = p.matcher(respContent);
        if(m.find()){
            System.out.println("-------1----------");
            System.out.println(m.group(1));
        }
        System.out.println("-------2----------");
    }

}
