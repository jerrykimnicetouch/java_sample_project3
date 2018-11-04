package com.jhc.chart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class MyPatternReplace {
 
    public String replaceWithPattern(String str,String replace){
         
        Pattern ptn = Pattern.compile("\\s+");
        Matcher mtch = ptn.matcher(str);
        return mtch.replaceAll(replace);
    }
    
    
}