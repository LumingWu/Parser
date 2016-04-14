package Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONObject implements JSONNode {

    private JSONNode _value;
    private HashMap<String, JSONNode> _childs = new HashMap<String, JSONNode>();

    public JSONObject() {
    }

    @Override
    public JSONNode getValue() {
        return _value;
    }

    @Override
    public void setValue(JSONNode value) {
        _value = value;
    }

    @Override
    public JSONNode getChild(String name) {
        return _childs.get(name);
    }

    @Override
    public void setChild(String name, JSONNode child) {
        _childs.put(name, child);
    }

    @Override
    public JSONNodeType getType() {
        return JSONNodeType.OBJECT;
    }

    @Override
    public void evaluate(String expression, int start, int end) {
        int state = 0;
        int startIndex = 0;
        int nestCounter = 0;
        int i = start + 1;
        String name = "";
        
        
        while (i < end - 1) {
            char c = expression.charAt(i);
            switch (state) {
                case 0:
                    if (c != ' ' && c != '\t' && c != '\n') {
                        if (c == '\"') {
                            state = 1;
                            startIndex = i;
                        }
                        else{
                            try {
                                throw new ParseException("\'\"\' is not found.", i);
                            } catch (ParseException ex) {
                                Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    break;
                case 1:
                    if(c == '\"'){
                        state = 2;
                        if(startIndex + 1 == i){
                            name = "";
                        }
                        else{
                            name = expression.substring(startIndex + 1, i);
                        }
                    }
                    break;
                case 2:
                    if (c != ' ' && c != '\t' && c != '\n') {
                        if (c == ':') {
                            state = 3;
                        }
                        else{
                            try {
                                throw new ParseException("\':\' is not found.", i);
                            } catch (ParseException ex) {
                                Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    break;
                case 3:
                    if (c != ' ' && c != '\t' && c != '\n') {
                        if (c == '\"') {
                            state = 4;
                            startIndex = i;
                        }
                        else if(c == '{'){
                            state = 5;
                            nestCounter = 0;
                            startIndex = i;
                        }
                        else if(c == '['){
                            state = 6;
                            nestCounter = 0;
                            startIndex = i;
                        }
                        else{
                            try {
                                throw new ParseException("\'\"\' and \'{\' and \'[\' are not found.", i);
                            } catch (ParseException ex) {
                                Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    break;
                case 4:
                    if(c == '\"'){
                        state = 7;
                        JSONString s = new JSONString();
                        if(startIndex + 1 == i){
                            s.setString("");
                        }
                        else{
                            s.setString(expression.substring(startIndex + 1, i));
                        }
                        _childs.put(name, s);
                    }
                    break;
                case 5:
                        if(c == '{'){
                            nestCounter += 1;
                        }
                        else if(c == '}'){
                            nestCounter -= 1;
                        }
                        if(nestCounter == -1){
                            JSONObject o = new JSONObject();
                            if(startIndex + 1 != i){
                                o.evaluate(expression, startIndex, i);
                            }
                            _childs.put(name, o);
                        }
                    break;
            }
            i += 1;
        }
        
    }

}
