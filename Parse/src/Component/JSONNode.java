package Component;

public interface JSONNode {
    
    public JSONNode getValue();
    
    public void setValue(JSONNode value);
    
    public JSONNode getChild(String name);
    
    public void setChild(String name, JSONNode child);
    
    public JSONNodeType getType();
    
    public void evaluate(String expression, int start, int end);
    
}
