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

import java.util.ArrayList;

/**
 * <b>JSONArray</b> class
 * <p>
 * <p>JSON Array class<br />
 * This class hold the JSON Array implementation.
 * <p>
 * </p>
 * <p>
 * author : stephane.metairie@gmail.com
 */
public class JSONArray extends JSON {

    // hold json array
    protected ArrayList<Object> items = new ArrayList();

    public int k = 0;
    private JSONArray ja;
    private JSONObject jo;

    /**
     * JSONArray Constructor
     */
    public JSONArray() {
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
     * Read a chunk of the input string, match JSON Array rules.
     * <p>
     * If the string is empty, the method returns null.<br />
     * </p>
     *
     * @param src the input string to parse
     * @param idx the "cursor's" position, put 0 to begin a complete parsing
     * @return a JSON Array representing a part of the input string.
     * @throws JSONParsingException if the string is not correctly wrote.
     * @see #parse(java.lang.String src)
     */
    @Override
    public JSONArray read(String src, int idx) throws JSONParsingException {
        boolean start = true;
        char c;
        int length = src.length();
        k = idx;
        while (k < length) {
            c = src.charAt(k);
            // expected jsonobject
            if (c == OPEN_BRACE) {
                k++;
                jo = new JSONObject();
                items.add(jo.read(src, k));
                k = jo.k;
            } else if (c == SEPARATOR_LIST || c == CLOSE_BRACKET || c == CLOSE_BRACE) {
                k++;
            } else if (c == OPEN_BRACKET) {
                k++;
                if (start) {
                    start = false;
                } else {
                    // expected jsonarray
                    ja = new JSONArray();
                    items.add(ja.read(src, k));
                    k = ja.k;
                }
            } else if ((int) c > 32) {
                throw new JSONParsingException("Invalid input JSON string. Expected character: " + DBLQUOTE + " found: " + c, k);
            } else k++;

        }

        return this;
    }

    /**
     * Put to the JSON Array one attribute name-value pair
     *
     * @param o The object to add
     */
    public final void put(Object o) {
        items.add(o);
    }


    /**
     * Get an Object with the index
     *
     * @param i The index
     * @return the Object linked to the key name. Null value possibly returned.
     */
    public final Object get(int i) {
        return items.get(i);
    }

    /**
     * Get an JSONObject with the index
     *
     * @param i The index
     * @return the Object linked to the key name. Null value possibly returned.
     */
    public final JSONObject getJSONObject(int i) {
        return (JSONObject) items.get(i);
    }

    /**
     * Get an JSONObject with the index
     *
     * @param i The index
     * @return the Object linked to the key name. Null value possibly returned.
     */
    public final JSONArray getJSONArray(int i) {
        return (JSONArray) items.get(i);
    }

    /**
     * Get a boolean with the index
     *
     * @param i The index
     * @return the Object linked to the key name. Never returns null.
     */
    public final boolean getBoolean(int i) {
        Boolean b = (Boolean) items.get(i);
        return (b == null ? false : b);
    }

    /**
     * Get a int with the index
     *
     * @param i The index
     * @return the Object linked to the key name. Never returns null.
     */
    public final int getInt(int i) {
        Number o = (Number) items.get(i);
        return (o == null ? 0 : o.intValue());
    }

    /**
     * Get a double with the index
     *
     * @param i The index
     * @return the Object linked to the key name. Never returns null.
     */
    public final double getDouble(int i) {
        Number o = (Number) items.get(i);
        return (o == null ? 0 : o.doubleValue());
    }

    /**
     * Get a long with the index
     *
     * @param i The index
     * @return the Object linked to the key name. Never returns null.
     */
    public final Long getLong(int i) {
        Number o = (Number) items.get(i);
        return (o == null ? 0 : o.longValue());
    }

    /**
     * Get a String with the index
     *
     * @param i The index
     * @return the Object linked to the key name. Never returns null.
     */
    public final String getString(int i) {
        Object o = items.get(i);
        return (o == null ? "" : (String) o);
    }


    @Override
    public void toBuffer(StringBuffer s) {
        s.append(OPEN_BRACKET);
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                s.append(SEPARATOR_LIST);
                s.append(SPACE);
            }
            s.append(items.get(i).toString());
        }
        s.append(CLOSE_BRACKET);
    }

    /**
     * Print JSON Array.
     * <p>
     * This method calls all toString() method in a cascade manner.
     * </p>
     *
     * @return the chunk string matched with this object.
     */
    @Override
    public final String toString() {
        StringBuffer s = new StringBuffer(items.size() * 20);
        toBuffer(s);
        return s.toString();
    }

}