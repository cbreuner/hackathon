package edu.csumb.partyon.utils;

import java.util.List;

/**
 * Created by Tobias on 22.11.2015.
 */
public class Utils {

    public static String join(List<String> list, char separator){
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < list.size(); i++){
            sb.append(list.get(i));
            if(i < list.size() -1)
                sb.append(separator);
        }
        return sb.toString();
    }
}
