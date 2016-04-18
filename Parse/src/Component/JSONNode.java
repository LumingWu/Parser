package Component;

public interface JSONNode {
    
    public JSONNodeType getType();
    
    public void evaluate(String expression, int start, int end);
    
}
