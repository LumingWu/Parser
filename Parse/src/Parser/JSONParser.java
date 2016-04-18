package Parser;

import Component.JSONArray;
import Component.JSONNode;
import Component.JSONObject;
import java.text.ParseException;
import java.util.HashMap;
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
            valueAndEnd[0] = file.substring(start + 1, i);
            valueAndEnd[1] = i + 1;
        } else if ((c > 47 && c < 58) || c == '-') {
            int i = start + 1;
            int state = 0;
            boolean search = true;
            while (search) {
                char d = file.charAt(i);
                switch (state) {
                    case 0:
                        if (d > 47 && d < 58) {

                        } else if (d == '.') {
                            state = 2;
                        } else if (d == 'E' || d == 'e') {
                            state = 3;
                        } else {
                            search = false;
                            valueAndEnd[0] = Integer.parseInt(file.substring(start, i));
                            valueAndEnd[1] = i;
                        }
                        break;
                    case 1:
                        if (d > 47 && d < 58) {

                        } else if (d == 'E' || d == 'e') {
                            state = 4;
                        } else {
                            search = false;
                            valueAndEnd[0] = Double.parseDouble(file.substring(start, i));
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
                throw new ParseException("Syntax error: Cannot determine the type of data.", start);
            } catch (ParseException ex) {
                Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valueAndEnd;
    }
}
