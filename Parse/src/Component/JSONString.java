package Component;

public class JSONString implements JSONNode{
    
    private String _value;;
    
    public JSONString(){}
    
    @Override
    public JSONNode getValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValue(JSONNode value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getString(){
        return _value;
    }
    
    public void setString(String s){
        _value = s;
    }

    @Override
    public JSONNodeType getType() {
        return JSONNodeType.STRING;
    }

    @Override
    public void evaluate(String expression) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
