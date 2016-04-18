package Component;

import java.util.HashMap;

public class JSONArray implements JSONNode{
    
    private JSONNode _value;
    private HashMap<String, JSONNode> _childs = new HashMap<String, JSONNode>();
    
    public JSONArray(){}

    @Override
    public JSONNodeType getType() {
        return JSONNodeType.ARRAY;
    }

    @Override
    public void evaluate(String expression, int start, int end) {
    }
}
