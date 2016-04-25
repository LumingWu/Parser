
import Parser.JSONParser;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
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
    
    @Test
    public void emptyArrayTest(){
        o = JSONParser.parse("[]");
        if(!(o instanceof Object[])){
            System.out.println(o.getClass());
            check = false;
            errorList.addLast(1);
        }
        if(((Object[])o).length != 0){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void array1DTest(){
        o = JSONParser.parse("[ true ,\tfalse\t,\n\"String\", 123, 1.23]");
        if(!(o instanceof Object[])){
            check = false;
            errorList.addLast(1);
        }
        if(((Object[])o).length != 5){
            check = false;
            errorList.addLast(2);
        }
        if((boolean)((Object[])o)[0] != true){
            check = false;
            errorList.addLast(3);
        }
        if((boolean)((Object[])o)[1] != false){
            check = false;
            errorList.addLast(4);
        }
        if(!"String".equals((String)((Object[])o)[2])){
            check = false;
            errorList.addLast(5);
        }
        if((int)((Object[])o)[3] != 123){
            check = false;
            errorList.addLast(6);
        }
        if((double)((Object[])o)[4] != 1.23){
            check = false;
            errorList.addLast(7);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void array2DTest(){
        o = JSONParser.parse("[[],[123]]");
        if(((Object[])((Object[])o)[0]).length != 0){
            check = false;
            errorList.addLast(1);
        }
        if((int)((Object[])((Object[])o)[1])[0] != 123){
            check = false;
            errorList.addLast(2);
        }
        assertTrue(errorList.toString(), check);
    }
    
    @Test
    public void objectTest(){
        o = JSONParser.parse("{\"x\":123,\"y\":1.23, \"z\":true ,\"a\":\nfalse\n,\"b\":null}");
        if((int)((HashMap)o).get("x") != 123){
            check = false;
            errorList.addLast(1);
        }
        if((double)((HashMap)o).get("y") != 1.23){
            check = false;
            errorList.addLast(2);
        }
        if((boolean)((HashMap)o).get("z") != true){
            check = false;
            errorList.addLast(3);
        }
        if((boolean)((HashMap)o).get("a") != false){
            check = false;
            errorList.addLast(4);
        }
        if(((HashMap)o).get("b") != null){
            check = false;
            errorList.addLast(5);
        }
        assertTrue(errorList.toString(), check);
    }
}
