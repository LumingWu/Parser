package Component;

public class JSONString implements JSONNode{
    
    private String _value;;
    
    public JSONString(){}
        
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
    public void evaluate(String expression, int start, int end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
