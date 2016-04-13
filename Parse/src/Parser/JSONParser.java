package Parser;

import Component.JSONNode;
import Component.JSONObject;
import java.util.List;
import java.util.regex.Pattern;

public class JSONParser {

    public JSONParser(){
        
    }
    
    public static JSONNode parse(String[] file){
        StringBuffer b = new StringBuffer();
        for(String s : file){
            b.append(s);
        }
        return parse(b.toString());
    }
    
    public static JSONNode parse(List<String> file){
        StringBuffer b = new StringBuffer();
        for(String s : file){
            b.append(s);
        }
        return parse(b.toString());
    }
    
    public static JSONNode parse(String file){
        if(file.startsWith("{") && file.endsWith("}")){
            
        }
        else if(file.startsWith("[") && file.endsWith("]")){
            
        }
        return null;
    }
}
