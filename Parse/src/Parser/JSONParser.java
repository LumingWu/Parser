package Parser;

import Component.JSONArray;
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
            JSONObject o = new JSONObject();
            o.evaluate(file, 0, file.length());
            return o;
        }
        else if(file.startsWith("[") && file.endsWith("]")){
            JSONArray a = new JSONArray();
            a.evaluate(file, 0, file.length());
            return a;
        }
        return null;
    }
}
