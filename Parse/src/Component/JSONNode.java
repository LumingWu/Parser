package Component;

public class JSONNode {
    
    private String _name;
    private JSONNode _value;
    private JSONNodeType _type;
    
    public JSONNode(){
        
    }
    
    public String getName(){
        return _name;
    }
    
    public void setName(String name){
        _name = name;
    }
    
    public JSONNode getValue(){
        return _value;
    }
    
    public void setValue(JSONNode value){
        _value = value;
    }
    
    public JSONNodeType getType(){
        return _type;
    }
    
    public void setType(JSONNodeType type){
        _type = type;
    }
    
    public void evaluate(){
        
    }
}
