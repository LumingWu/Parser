package Component;

public interface JSONNode {
    
    public JSONNode getValue();
    
    public void setValue(JSONNode value);
    
    public JSONNodeType getType();
    
    public void evaluate(String expression);
    
}
