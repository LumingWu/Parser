package Parser;

import Component.JSONNode;
import Component.JSONObject;
import java.util.List;

public class JSONParser {

    public JSONParser(){
        
    }
    
    public static JSONNode parse(String[] file){
        StringBuilder b = new StringBuilder();
        for(String s : file){
            b.append(s);
        }
        return parse(b.toString());
    }
    
    public static JSONNode parse(List<String> file){
        StringBuilder b = new StringBuilder();
        for(String s : file){
            b.append(s);
        }
        return parse(b.toString());
    }
    
    public static JSONNode parse(String file){
        if(file.startsWith("{") && file.endsWith("}")){
            return new JSONObject();
        }
        else if(file.startsWith("[") && file.endsWith("]")){
            
        }
        return null;
    }
}
