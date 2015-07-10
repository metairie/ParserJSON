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
 * <b>JSON</b> class
 * <p>
 * <p>JSON class <br />
 * This is our free implementation of JSON format.</p>
 * <p>Here below this is the BNF : <br />
 * <p>
 * <p>
 * jsonarray<br />
 * = '[' jsonobject ',' jsonarray-list ']'<br />
 * | '[' jsonarray-list ',' jsonobject ']'<br />
 * | string<br />
 * ;<br />
 * <p>
 * jsonarray-list<br />
 * = jsonarray ',' jsonarray-list<br />
 * | jsonarray<br />
 * ;<br />
 * <p>
 * tag-name<br />
 * = string<br />
 * ;<br />
 * <p>
 * jsonobject<br />
 * = '{' jsonobject-list '}'<br />
 * | '{' '}'<br />
 * ;<br />
 * <p>
 * jsonobject-list<br />
 * = attribute ',' jsonobject-list<br />
 * | attribute<br />
 * ;<br />
 * <p>
 * attribute<br />
 * = attribute-name ':' attribute-value<br />
 * ;<br />
 * <p>
 * attribute-name<br />
 * = string<br />
 * ;<br />
 * <p>
 * attribute-value<br />
 * = string<br />
 * | number<br />
 * | 'true'<br />
 * | 'false'<br />
 * | 'null'<br />
 * ;<br /><br />
 * <p>
 * author : stephane.metairie@gmail.com
 */
public abstract class JSON {

    /**
     * useful constants for parsing
     */
    public final static char SPACE = ' ';
    public final static char ANTISLASH = '\\';
    public final static char UNICODE = 'u';

    public final static char OPEN_BRACKET = '[';
    public final static char OPEN_BRACE = '{';
    public final static char DBLQUOTE = '"';
    public final static char QUOTE = '\'';

    public final static char CLOSE_BRACKET = ']';
    public final static char CLOSE_BRACE = '}';

    public final static char SEPARATOR_FLOATING = '.';
    public final static char SEPARATOR_ATTRIBUTE = ':';
    public final static char SEPARATOR_LIST = ',';
    public final static String SEPARATOR_ATTRIBUTE_SPACE = ": ";
    public final static String SEPARATOR_LIST_SPACE = ", ";

    public final static char MINUS = '-';
    public final static char EXPMIN = 'e';

    /**
     * Method for reading input string
     *
     * @param src the input string to parse
     * @param idx the "cursor's" position, put 0 to begin a complete parsing
     * @return JSON object
     * @throws JSONParsingException
     */
    public abstract JSON read(String src, int idx) throws JSONParsingException;

    /**
     * Method for reading input string
     *
     * @param src the input string to parse
     * @return JSON object
     * @throws JSONParsingException
     */
    public abstract JSON read(String src) throws JSONParsingException;

    /**
     * Append string output to a stringBuffer.
     *
     * @param s the stringBuffer.
     */
    protected abstract void toBuffer(StringBuffer s);

    /**
     * Parse an input stream and build a JSON object.
     * <p>
     * If the string is empty, the method returns null.<br />
     * To recursively output objects, use toString().
     * <ul>Input strings are :<br />
     * <li>- JSONArray [...]</li>
     * <li>- or JSONObject {...}</li>
     * <li>- or a simple string "..."</li>
     * </ul>
     * </p>
     *
     * @param src The input string top parse
     * @return a JSON object representing the entire string.
     * @throws JSONParsingException If the string is not correctly wrote.
     * @see #read(String)
     * @see #toString()
     */
    public static JSON parse(String src) throws JSONParsingException {
        char c;
        int k = 0;
        while (true) {
            c = src.charAt(k);
            // initial switch
            //  JSON object
            if (c == OPEN_BRACKET) {
                return new JSONArray().read(src, k);
                // JSON array
            } else if (c == OPEN_BRACE) {
                return new JSONObject().read(src, k);
                // simple JSON string
            } else if (c == DBLQUOTE || c == QUOTE) {
                k++;
                return new JSONString().read(src, k);
                // unexpected character found
            } else {
                if ((int) c > 32) {
                    throw new JSONParsingException("Invalid input JSON string. Expected character: " + OPEN_BRACKET + OPEN_BRACE + " found: " + c, k);
                }
                k++;
            }
        }
    }
}