/*
*
* author : stephane.metairie@gmail.com
*
* - Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* - Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
*
* - Redistributions in binary form must reproduce the above copyright notice
*/


package org.fxpart.parser.json;

/**
 * <b>JSONAttribute</b> class
 * <p>
 * <p>JSONAttribute class<br />
 * This class allows to manage JSON attribute-name and attribute-value rules.
 * <p>
 * </p>
 * <p>
 * author : stephane.metairie@gmail.com
 */
public class JSONAttribute extends JSON {

    protected String name = null;
    protected Object value = null;
    private String tmp = "";
    public int k = 0;
    private JSONArray ja;
    private JSONObject jo;

    /**
     * Read the input string
     *
     * @param src
     * @return
     * @throws JSONParsingException
     * @see #read(java.lang.String src, int)
     */
    @Override
    public JSON read(String src) throws JSONParsingException {
        return read(src, 0);
    }

    /**
     * Read a chunk of the input string, matches JSON Attribute rules.
     * <p>
     * If the JS String is rounded with a valid pair of quotes,
     * they are changed with DBLQUOTE. If the string is empty, the method
     * returns null.<br />
     * </p>
     *
     * @param src the input string to parse
     * @param idx the "cursor's" position, put 0 to begin a complete parsing
     * @return a JSON Attribute representing a part of the input string.
     * @throws JSONParsingException if the string is not correctly wrote.
     * @see #parse(java.lang.String src)
     */
    @Override
    public JSONAttribute read(String src, int idx) throws JSONParsingException {
        int currentPos = idx;
        int ascii;
        char c;
        int length = src.length();

        try {
            // attribute name
            k = src.indexOf(DBLQUOTE, currentPos);
            name = src.substring(currentPos, k);
            // separator
            k++;
            k = src.indexOf(SEPARATOR_ATTRIBUTE, k);
            // attribute value
            k++;
            while (k < length) {
                c = src.charAt(k);
                ascii = (int) c;

                if (c == DBLQUOTE || c == QUOTE) {
                    // string expected
                    k++;
                    currentPos = k;
                    while (k < length) {
                        c = src.charAt(k);
                        // expected end string
                        if (c == DBLQUOTE || c == QUOTE) {
                            k++;
                            break;
                        } else if (c == ANTISLASH) {
                            k++;
                            c = src.charAt(idx);
                            if (c == UNICODE)
                                k += 3;
                        }
                        k++;
                    }
                    value = src.substring(currentPos, k - 1);
                    break;

                } else if (c == OPEN_BRACE) {
                    // jsonobject expected
                    k++;
                    jo = new JSONObject();
                    value = jo.read(src, k);
                    k = jo.k;
                    break;

                } else if (c == OPEN_BRACKET) {
                    // jsonarray expected
                    k++;
                    ja = new JSONArray();
                    value = ja.read(src, k);
                    k = ja.k;
                    break;

                } else if (ascii > 32) {
                    // number boolean expected
                    currentPos = k;
                    k++;
                    while (k < length) {
                        c = src.charAt(k);
                        if (c == SEPARATOR_LIST || c == CLOSE_BRACE || c == CLOSE_BRACKET) {
                            tmp = src.substring(currentPos, k).toLowerCase();
                            if (tmp.indexOf("true") > -1)
                                value = true;
                            else if (tmp.indexOf("false") > -1)
                                value = false;
                            else if (tmp.indexOf("null") > -1)
                                value = null;
                            else if (tmp.indexOf(EXPMIN) == -1 && tmp.indexOf(SEPARATOR_FLOATING) == -1)
                                value = Long.parseLong(tmp);
                            else
                                value = Double.parseDouble(tmp);
                            break;
                        }
                        k++;
                    }
                    break;

                }
                k++;
            }
        } catch (Exception ex) {
            throw new JSONParsingException("Invalid input JSON string. " + ex.getCause() + " " + ex.getMessage(), k);
        }

        return this;
    }

    @Override
    public void toBuffer(StringBuffer s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}