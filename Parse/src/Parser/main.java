package Parser;

import java.math.BigDecimal;

public class main {
    public static void main(String[] args){
        System.out.println(JSONParser.stringify(new BigDecimal("-90000000")));
    }
}
