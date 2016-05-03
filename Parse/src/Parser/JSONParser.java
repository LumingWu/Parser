package Parser;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        char c = file.charAt(start);
        switch (c) {
            case '{':
                HashMap<String, Object> map = new HashMap<String, Object>();
                int stateO = 0;
                int iO = start + 1;
                String nameO = null;
                while (true) {
                    char d = file.charAt(iO);
                    switch (stateO) {
                        case 0:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                switch (d) {
                                    case '}':
                                        return new Object[]{map, iO + 1};
                                    case '\"':
                                        Object[] pair = parse(file, iO);
                                        nameO = (String) pair[0];
                                        iO = (int) pair[1] - 1;
                                        stateO = 1;
                                        break;
                                    default:
                                        throwParseException("Parsing an JSON Object, but the parser found wrong character "
                                                + "while looking for the name of a value or end of the JSON Object.", iO);
                                }
                            }
                            break;
                        case 1:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                if (d == ':') {
                                    stateO = 2;
                                } else {
                                    throwParseException("Parsing an JSON Object, but the parser found wrong character "
                                            + "while looking for the charcter \':\'.", iO);
                                }
                            }
                            break;
                        case 2:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                Object[] pair = parse(file, iO);
                                map.put(nameO, pair[0]);
                                iO = (int) pair[1] - 1;
                                stateO = 3;
                            }
                            break;
                        case 3:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                switch (d) {
                                    case '}':
                                        return new Object[]{map, iO + 1};
                                    case ',':
                                        stateO = 0;
                                        break;
                                    default:
                                        throwParseException("Parsing an JSON Object, but the parser found wrong character "
                                                + "while looking for the charcter \',\' or the end of the JSON Object.", iO);
                                }
                            }
                            break;
                    }
                    iO += 1;
                }
            case '[':
                LinkedList list = new LinkedList();
                int stateA = 0;
                int iA = start + 1;
                while (true) {
                    char d = file.charAt(iA);
                    switch (stateA) {
                        case 0:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                if (d == ']') {
                                    return new Object[]{list.toArray(), iA + 1};
                                } else {
                                    Object[] pair = parse(file, iA);
                                    list.add(pair[0]);
                                    iA = (int) pair[1] - 1;
                                    stateA = 1;
                                }
                            }
                            break;
                        case 1:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                switch (d) {
                                    case ']':
                                        return new Object[]{list.toArray(), iA + 1};
                                    case ',':
                                        stateA = 0;
                                        break;
                                    default:
                                        throwParseException("Parsing an Arrays, but the parser found wrong character "
                                                + "while looking for additional value or end of the Arrays.", iA);
                                }
                            }
                            break;
                    }
                    iA += 1;
                }
            case '\"':
                int iS = start + 1;
                while (file.charAt(iS) != '\"') {
                    iS = iS + 1;
                    if (iS == file.length()) {
                        throwParseException("Parsing a String, but the parser reached the end of the file.", start);
                    }
                }
                if (start + 1 == iS) {
                    return new Object[]{"", iS + 1};
                } else {
                    return new Object[]{file.substring(start + 1, iS), iS + 1};
                }
            case 't':
                if (file.charAt(start + 1) == 'r' && file.charAt(start + 2) == 'u' && file.charAt(start + 3) == 'e') {
                    return new Object[]{true, start + 4};
                } else {
                    throwParseException("Expected value true.", start);
                }
            case 'f':
                if (file.charAt(start + 1) == 'a' && file.charAt(start + 2) == 'l' && file.charAt(start + 3) == 's' && file.charAt(start + 4) == 'e') {
                    return new Object[]{false, start + 5};
                } else {
                    throwParseException("Expected value false.", start);
                }
            case 'n':
                if (file.charAt(start + 1) == 'u' && file.charAt(start + 2) == 'l' && file.charAt(start + 3) == 'l') {
                    return new Object[]{null, start + 4};
                } else {
                    throwParseException("Expected value null.", start);
                }
            default:
                if ((c > 47 && c < 58) || c == '-') {
                    int iN = start + 1;
                    int stateN = 0;
                    Object number = null;
                    int exponentStart = 0;
                    while (true) {
                        char d = file.charAt(iN);
                        switch (stateN) {
                            case 0:
                                if (d > 47 && d < 58) {

                                } else if (d == '.') {
                                    stateN = 1;
                                } else if (d == 'E' || d == 'e') {
                                    stateN = 2;
                                    number = new BigDecimal(file.substring(start, iN));
                                } else {
                                    return new Object[]{Integer.parseInt(file.substring(start, iN)), iN};
                                }
                                break;
                            case 1:
                                if (d > 47 && d < 58) {

                                } else if (d == 'E' || d == 'e') {
                                    stateN = 2;
                                    number = new BigDecimal(file.substring(start, iN));
                                } else {
                                    return new Object[]{Double.parseDouble(file.substring(start, iN)), iN};
                                }
                                break;
                            case 2:
                                switch (d) {
                                    case '+':
                                        exponentStart = iN + 1;
                                        stateN = 3;
                                        break;
                                    case '-':
                                        exponentStart = iN + 1;
                                        stateN = 4;
                                        break;
                                    default:
                                        throwParseException("Expected the sign of the exponent.", iN);
                                }
                                break;
                            case 3:
                                if (!(d > 47 && d < 58)) {
                                    return new Object[]{((BigDecimal) number).multiply(new BigDecimal("10").pow(Integer.parseInt(file.substring(exponentStart, iN)))), iN};
                                }
                                break;
                            case 4:
                                if (!(d > 47 && d < 58)) {
                                    return new Object[]{((BigDecimal) number).multiply(new BigDecimal("0.1").pow(Integer.parseInt(file.substring(exponentStart, iN)))), iN};
                                }
                                break;
                        }
                        iN += 1;
                    }
                } else {
                    throwParseException("Cannot determine the type of the data.", start);
                }
        }
        return null;
    }

    private static void throwParseException(String text, int location) {
        try {
            throw new ParseException(text, location);
        } catch (ParseException ex) {
            Logger.getLogger(JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String stringify(Object o) {
        StringBuilder builder = new StringBuilder();
        stringify(o, builder);
        return builder.toString();
    }

    private static void stringify(Object o, StringBuilder b) {
        if (o == null) {
            b.append('n').append('u').append('l').append('l');
        } else {
            switch (o.getClass().getSimpleName()) {
                case "HashMap":
                    b.append("{");
                    Iterator<Map.Entry> i = ((HashMap) o).entrySet().iterator();
                    if (i.hasNext()) {
                        Map.Entry e = i.next();
                        if(!(e.getKey() instanceof String)){
                            throwTypeException("Name value pair should have String as type of the name, not "
                                    + e.getKey().getClass().getSimpleName());
                        }
                        b.append('\"');
                        b.append(e.getKey()).append('\"').append(':');
                        stringify(e.getValue(), b);
                        while (i.hasNext()) {
                            e = i.next();
                            b.append(',').append('\"');
                            b.append(e.getKey()).append('\"').append(':');
                            stringify(e.getValue(), b);
                        }
                    }
                    b.append("}");
                    break;
                case "Object[]":
                    Object[] a = (Object[]) o;
                    int length = a.length;
                    b.append("[");
                    switch (length) {
                        case 0:
                            break;
                        case 1:
                            stringify(a[0], b);
                            break;
                        default:
                            stringify(a[0], b);
                            for (int j = 1; j < length; j++) {
                                b.append(',');
                                stringify(a[j], b);
                            }
                    }
                    b.append("]");
                    break;
                case "String":
                    b.append('\"').append(o).append('\"');
                    break;
                case "Boolean":
                    b.append(o);
                    break;
                case "Integer":
                    b.append(o);
                    break;
                case "Double":
                    b.append(o);
                    break;
                case "BigDecimal":
                    BigDecimal bd = (BigDecimal) o;
                    BigDecimal ten = new BigDecimal("10");
                    int counter = 0;
                    if (bd.compareTo(new BigDecimal("0")) == 1) {
                        while (bd.compareTo(ten) >= 0) {
                            bd = bd.divide(ten);
                            counter += 1;
                        }
                        BigDecimal zero1 = new BigDecimal("0.1");
                        while (bd.compareTo(zero1) == -1) {
                            bd = bd.multiply(ten);
                            counter -= 1;
                        }
                    } else {
                        BigDecimal Nten = new BigDecimal("-10");
                        while (bd.compareTo(Nten) <= 0) {
                            bd = bd.divide(ten);
                            counter += 1;
                        }
                        BigDecimal Nzero1 = new BigDecimal("-0.1");
                        while (bd.compareTo(Nzero1) == 1) {
                            bd = bd.multiply(ten);
                            counter -= 1;
                        }
                    }
                    if (counter >= 0) {
                        b.append(bd.stripTrailingZeros()).append('E').append('+').append(counter);
                    } else {
                        b.append(bd.stripTrailingZeros()).append('E').append(counter);
                    }
                    break;
                default:
                    throwTypeException(o.getClass().getSimpleName() + "is not supported.");
            }
        }
    }
    
    private static void throwTypeException(String text){
        try {
            throw new Exception(text);
        } catch (Exception ex) {
            Logger.getLogger(JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
