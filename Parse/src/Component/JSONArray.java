package Component;

import java.util.HashMap;

public class JSONArray implements JSONNode{
    
    private JSONNode _value;
    private HashMap<String, JSONNode> _childs = new HashMap<String, JSONNode>();
    
    public JSONArray(){}

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
        return JSONNodeType.ARRAY;
    }

    @Override
    public void evaluate(String expression, int start, int end) {
    }
}
