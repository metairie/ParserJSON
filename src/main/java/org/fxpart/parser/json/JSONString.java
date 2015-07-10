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
 * <b>JSONString</b> class
 * <p>
 * <p>JSONString class<br />
 * This class is a String. Method Heritated from JSON class, are used to parse
 * the string to return
 * <p>
 * </p>
 * <p>
 * author : stephane.metairie@gmail.com
 */
public class JSONString extends JSON {

    // hold json string object
    protected String value = new String();

    /**
     * JSONString Constructor
     */
    public JSONString() {
    }

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
     * Read a chunk of the input string, matches JSON String rules.
     * <p>
     * The input string, held by the String object "input" variable is shared
     * by all JSON subclasses (static). Note that the index of parsing is also
     * shared by all JSON child classes.
     * If the string is empty, the method returns null.<br />
     * </p>
     *
     * @return a JSON String.
     * @throws JSONParsingException if the string is not correctly wrote.
     * @see #parse(java.lang.String src)
     */
    @Override
    public JSONString read(String src, int idx) throws JSONParsingException {
        value = src.substring(idx, src.length());
        return this;
    }

    /**
     * Print JSON String.
     * <p>
     * This method is a simple substring extracted
     * from the global input String held by static JSON object.
     * </p>
     *
     * @return the chunk string matched with this object.
     */
    @Override
    public String toString() {
        return DBLQUOTE + value + DBLQUOTE;
    }

    @Override
    public void toBuffer(StringBuffer s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}