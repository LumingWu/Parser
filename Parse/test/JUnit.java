
import Parser.JSONParser;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class JUnit {

    private LinkedList<Integer> errorList;
    private boolean check;
    private Object o;

    public JUnit() {
    }

    @Before
    public void setUp() {
        errorList = new LinkedList<Integer>();
        check = true;
        o = null;
    }

    @After
    public void tearDown() {
        errorList.clear();
    }

    @Test
    public void nullTest(){
        o = JSONParser.parse("null ");
        if (o != null) {
            check = false;
            errorList.addLast(1);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void trueTest(){
        o = JSONParser.parse("true ");
        if(!(o instanceof Boolean)){
            check = false;
            errorList.addLast(1);
        }
        if((boolean)o != true){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void falseTest(){
        o = JSONParser.parse("false ");
        if(!(o instanceof Boolean)){
            check = false;
            errorList.addLast(1);
        }
        if((boolean)o != false){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void stringTest(){
        o = JSONParser.parse("\"string\" ");
        if(!(o instanceof String)){
            check = false;
            errorList.addLast(1);
        }
        if(!((String)o).equals("string")){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void emptyStringTest(){
        o = JSONParser.parse("\"\" ");
        if(!(o instanceof String)){
            check = false;
            errorList.addLast(1);
        }
        if(!((String)o).equals("")){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void integerTest(){
        o = JSONParser.parse("123 ");
        if(!(o instanceof Integer)){
            check = false;
            errorList.addLast(1);
        }
        if((int)o != 123){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void negativeIntegerTest(){
        o = JSONParser.parse("-123 ");
        if(!(o instanceof Integer)){
            check = false;
            errorList.addLast(1);
        }
        if((int)o != -123){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void eIntegerTest(){
        o = JSONParser.parse("123E+10 ");
        if(!(o instanceof BigInteger)){
            check = false;
            errorList.addLast(1);
        }
        if(!((BigInteger)o).equals(new BigInteger("1230000000000"))){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void doubleTest(){
        o = JSONParser.parse("1.23 ");
        if(!(o instanceof Double)){
            check = false;
            errorList.addLast(1);
        }
        if((double)o != 1.23){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void negativeDoubleTest(){
        o = JSONParser.parse("-1.23 ");
        if(!(o instanceof Double)){
            check = false;
            errorList.addLast(1);
        }
        if((double)o != -1.23){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void eDoubleTest(){
        o = JSONParser.parse("1.23E+12 ");
        if(!(o instanceof BigDecimal)){
            check = false;
            errorList.addLast(1);
        }
        if(!((BigDecimal)o).equals(new BigDecimal("1.23").multiply(new BigDecimal("10").pow(12)))){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
}
