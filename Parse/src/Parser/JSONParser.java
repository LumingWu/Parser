package Parser;

import Component.JSONObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONParser {

    public JSONParser() {

    }

    public static Object parse(String[] file) {
        StringBuilder b = new StringBuilder();
        for (String s : file) {
            b.append(s);
        }
        return parse(b.toString());
    }

    public static Object parse(List<String> file) {
        StringBuilder b = new StringBuilder();
        for (String s : file) {
            b.append(s);
        }
        return parse(b.toString());
    }

    public static Object parse(String file) {
        return parse(file, 0)[0];
    }

    private static Object[] parse(String file, int start) {
        Object[] valueAndEnd = new Object[2];
        char c = file.charAt(start);
        if (c == '{') {

        } else if (c == '[') {

        } else if (c == '\"') {
            int i = start + 1;
            while (file.charAt(i) != '\"') {
                i = i + 1;
            }
            if(start + 1 == i){
                valueAndEnd[0] = "";
            }
            else{
                valueAndEnd[0] = file.substring(start + 1, i);
            }
            valueAndEnd[1] = i + 1;
        } else if ((c > 47 && c < 58) || c == '-') {
            int i = start + 1;
            int state = 0;
            boolean search = true;
            Object number = null;
            int exponentStart = 0;
            while (search) {
                char d = file.charAt(i);
                switch (state) {
                    case 0:
                        if (d > 47 && d < 58) {

                        } else if (d == '.') {
                            state = 1;
                        } else if (d == 'E' || d == 'e') {
                            state = 2;
                            number = new BigInteger(file.substring(start, i));
                        } else {
                            search = false;
                            valueAndEnd[0] = Integer.parseInt(file.substring(start, i));
                            valueAndEnd[1] = i;
                        }
                        break;
                    case 1:
                        if (d > 47 && d < 58) {

                        } else if (d == 'E' || d == 'e') {
                            state = 3;
                            number = new BigDecimal(file.substring(start, i));
                        } else {
                            search = false;
                            valueAndEnd[0] = Double.parseDouble(file.substring(start, i));
                            valueAndEnd[1] = i;
                        }
                        break;
                    case 2:
                        if (d == '+') {
                            exponentStart = i + 1;
                        } else if (d == '-') {
                            exponentStart = i;
                        } else {
                            try {
                                throw new ParseException("Semantic error: Expected the sign of the exponent.", i);
                            } catch (ParseException ex) {
                                Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        state = 4;
                        break;
                    case 3:
                        if (d == '+') {
                            exponentStart = i + 1;
                        } else if (d == '-') {
                            exponentStart = i;
                        } else {
                            try {
                                throw new ParseException("Semantic error: Expected the sign of the exponent.", i);
                            } catch (ParseException ex) {
                                Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        state = 5;
                        break;
                    case 4:
                        if (!(d > 47 && d < 58)) {
                            search = false;
                            valueAndEnd[0] = ((BigInteger) number).multiply(new BigInteger("10").pow(Integer.parseInt(file.substring(exponentStart, i))));
                            valueAndEnd[1] = i;
                        }
                        break;
                    case 5:
                        if (!(d > 47 && d < 58)) {
                            search = false;
                            valueAndEnd[0] = ((BigDecimal) number).multiply(new BigDecimal("10").pow(Integer.parseInt(file.substring(exponentStart, i))));
                            valueAndEnd[1] = i;
                        }
                        break;
                }
                i += 1;
            }
        } else if (c == 't') {
            if (file.charAt(start + 1) == 'r' && file.charAt(start + 2) == 'u' && file.charAt(start + 3) == 'e') {
                valueAndEnd[0] = true;
                valueAndEnd[1] = start + 4;
            }
        } else if (c == 'f') {
            if (file.charAt(start + 1) == 'a' && file.charAt(start + 2) == 'l' && file.charAt(start + 3) == 's' && file.charAt(start + 4) == 'e') {
                valueAndEnd[0] = false;
                valueAndEnd[1] = start + 5;
            }
        } else if (c == 'n') {
            if (file.charAt(start + 1) == 'u' && file.charAt(start + 2) == 'l' && file.charAt(start + 3) == 'l') {
                valueAndEnd[0] = null;
                valueAndEnd[1] = start + 4;
            }
        } else {
            try {
                throw new ParseException("Semantic error: Cannot determine the type of data.", start);
            } catch (ParseException ex) {
                Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valueAndEnd;
    }
}
