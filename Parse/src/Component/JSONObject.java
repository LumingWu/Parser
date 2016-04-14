package Component;

public class JSONObject implements JSONNode{
    
    private JSONNode _value;
    
    public JSONObject(){}

    @Override
    public JSONNode getValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValue(JSONNode value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONNodeType getType() {
        return JSONNodeType.OBJECT;
    }

    @Override
    public void evaluate(String expression) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
