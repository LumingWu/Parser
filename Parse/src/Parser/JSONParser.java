package Parser;

import Component.JSONObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.LinkedList;
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

    public static Object[] parse(String file, int start) {
        char c = file.charAt(start);
        switch (c) {
            case '{':
                break;
            case '[':
                LinkedList list = new LinkedList();
                int state = 0;
                int i = start + 1;
                while (true) {
                    char d = file.charAt(i);
                    switch (state) {
                        case 0:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                if (d == ']') {
                                    return new Object[]{list.toArray(), i + 1};
                                } else {
                                    Object[] pair = parse(file, i);
                                    list.add(pair[0]);
                                    i = (int) pair[1] - 1;
                                    state = 1;
                                }
                            }
                            break;
                        case 1:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                switch (d) {
                                    case ']':
                                        return new Object[]{list.toArray(), i + 1};
                                    case ',':
                                        state = 0;
                                        break;
                                    default:
                                        try {
                                            throw new ParseException("Semantic error: Cannot determine the type of data.", i);
                                        } catch (ParseException ex) {
                                            Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
                                }
                            }
                            break;
                    }
                    i += 1;
                }
            case '\"':
                int i2 = start + 1;
                while (file.charAt(i2) != '\"') {
                    i2 = i2 + 1;
                }
                if (start + 1 == i2) {
                    return new Object[]{"", i2 + 1};
                } else {
                    return new Object[]{file.substring(start + 1, i2), i2 + 1};
                }
            case 't':
                if (file.charAt(start + 1) == 'r' && file.charAt(start + 2) == 'u' && file.charAt(start + 3) == 'e') {
                    return new Object[]{true, start + 4};
                } else {
                    try {
                        throw new ParseException("Semantic error: Cannot determine the type of data.", start);
                    } catch (ParseException ex) {
                        Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            case 'f':
                if (file.charAt(start + 1) == 'a' && file.charAt(start + 2) == 'l' && file.charAt(start + 3) == 's' && file.charAt(start + 4) == 'e') {
                    return new Object[]{false, start + 5};
                } else {
                    try {
                        throw new ParseException("Semantic error: Cannot determine the type of data.", start);
                    } catch (ParseException ex) {
                        Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            case 'n':
                if (file.charAt(start + 1) == 'u' && file.charAt(start + 2) == 'l' && file.charAt(start + 3) == 'l') {
                    return new Object[]{null, start + 4};
                } else {
                    try {
                        throw new ParseException("Semantic error: Cannot determine the type of data.", start);
                    } catch (ParseException ex) {
                        Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            default:
                if ((c > 47 && c < 58) || c == '-') {
                    int i3 = start + 1;
                    int state2 = 0;
                    boolean search2 = true;
                    Object number = null;
                    int exponentStart = 0;
                    while (search2) {
                        char d = file.charAt(i3);
                        switch (state2) {
                            case 0:
                                if (d > 47 && d < 58) {

                                } else if (d == '.') {
                                    state2 = 1;
                                } else if (d == 'E' || d == 'e') {
                                    state2 = 2;
                                    number = new BigInteger(file.substring(start, i3));
                                } else {
                                    return new Object[]{Integer.parseInt(file.substring(start, i3)), i3};
                                }
                                break;
                            case 1:
                                if (d > 47 && d < 58) {

                                } else if (d == 'E' || d == 'e') {
                                    state2 = 3;
                                    number = new BigDecimal(file.substring(start, i3));
                                } else {
                                    return new Object[]{Double.parseDouble(file.substring(start, i3)), i3};
                                }
                                break;
                            case 2:
                                switch (d) {
                                    case '+':
                                        exponentStart = i3 + 1;
                                        break;
                                    case '-':
                                        exponentStart = i3;
                                        break;
                                    default:
                                        try {
                                            throw new ParseException("Semantic error: Expected the sign of the exponent.", i3);
                                        } catch (ParseException ex) {
                                            Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
                                }
                                state2 = 4;
                                break;
                            case 3:
                                switch (d) {
                                    case '+':
                                        exponentStart = i3 + 1;
                                        break;
                                    case '-':
                                        exponentStart = i3;
                                        break;
                                    default:
                                        try {
                                            throw new ParseException("Semantic error: Expected the sign of the exponent.", i3);
                                        } catch (ParseException ex) {
                                            Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
                                }
                                state2 = 5;
                                break;
                            case 4:
                                if (!(d > 47 && d < 58)) {
                                    return new Object[]{((BigInteger) number).multiply(new BigInteger("10").pow(Integer.parseInt(file.substring(exponentStart, i3)))), i3};
                                }
                                break;
                            case 5:
                                if (!(d > 47 && d < 58)) {
                                    return new Object[]{((BigDecimal) number).multiply(new BigDecimal("10").pow(Integer.parseInt(file.substring(exponentStart, i3)))), i3};
                                }
                                break;
                        }
                        i3 += 1;
                    }
                } else {
                    try {
                        throw new ParseException("Semantic error: Cannot determine the type of data.", start);
                    } catch (ParseException ex) {
                        Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }
        return null;
    }
}
