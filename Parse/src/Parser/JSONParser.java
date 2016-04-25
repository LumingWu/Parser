package Parser;

import Component.JSONObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
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
                                        throwException("Parsing an JSON Object, but the parser found wrong character "
                                                + "while looking for the name of a value or end of the JSON Object.", iO);
                                        break;
                                }
                            }
                            break;
                        case 1:
                            if (d != ' ' && d != '\t' && d != '\n') {
                                if (d == ':') {
                                    stateO = 2;
                                } else {
                                    throwException("Parsing an JSON Object, but the parser found wrong character "
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
                                        throwException("Parsing an JSON Object, but the parser found wrong character "
                                                + "while looking for the charcter \',\' or the end of the JSON Object.", iO);
                                        break;
                                }
                            }
                            break;
                    }
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
                                        throwException("Parsing an Arrays, but the parser found wrong character "
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
                        throwException("Parsing a String, but the parser reached the end of the file.", start);
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
                    throwException("Expected value true.", start);
                }
            case 'f':
                if (file.charAt(start + 1) == 'a' && file.charAt(start + 2) == 'l' && file.charAt(start + 3) == 's' && file.charAt(start + 4) == 'e') {
                    return new Object[]{false, start + 5};
                } else {
                    throwException("Expected value false.", start);
                }
            case 'n':
                if (file.charAt(start + 1) == 'u' && file.charAt(start + 2) == 'l' && file.charAt(start + 3) == 'l') {
                    return new Object[]{null, start + 4};
                } else {
                    throwException("Expected value null.", start);
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
                                    number = new BigInteger(file.substring(start, iN));
                                } else {
                                    return new Object[]{Integer.parseInt(file.substring(start, iN)), iN};
                                }
                                break;
                            case 1:
                                if (d > 47 && d < 58) {

                                } else if (d == 'E' || d == 'e') {
                                    stateN = 3;
                                    number = new BigDecimal(file.substring(start, iN));
                                } else {
                                    return new Object[]{Double.parseDouble(file.substring(start, iN)), iN};
                                }
                                break;
                            case 2:
                                switch (d) {
                                    case '+':
                                        exponentStart = iN + 1;
                                        break;
                                    case '-':
                                        exponentStart = iN;
                                        break;
                                    default:
                                        throwException("Expected the sign of the exponent.", iN);
                                }
                                stateN = 4;
                                break;
                            case 3:
                                switch (d) {
                                    case '+':
                                        exponentStart = iN + 1;
                                        break;
                                    case '-':
                                        exponentStart = iN;
                                        break;
                                    default:
                                        throwException("Expected the sign of the exponent.", iN);
                                }
                                stateN = 5;
                                break;
                            case 4:
                                if (!(d > 47 && d < 58)) {
                                    return new Object[]{((BigInteger) number).multiply(new BigInteger("10").pow(Integer.parseInt(file.substring(exponentStart, iN)))), iN};
                                }
                                break;
                            case 5:
                                if (!(d > 47 && d < 58)) {
                                    return new Object[]{((BigDecimal) number).multiply(new BigDecimal("10").pow(Integer.parseInt(file.substring(exponentStart, iN)))), iN};
                                }
                                break;
                        }
                        iN += 1;
                    }
                } else {
                    throwException("Cannot determine the type of the data.", start);
                }
        }
        return null;
    }

    private static void throwException(String text, int location) {
        try {
            throw new ParseException(text, location);
        } catch (ParseException ex) {
            Logger.getLogger(JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
