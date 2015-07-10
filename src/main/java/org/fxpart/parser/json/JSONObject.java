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

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * <b>JSONObject</b> class
 * <p>
 * <p>JSONObject class<br />
 * This class holds the JSON Object implementation.
 * <p>
 * </p>
 * <p>
 * author : stephane.metairie@gmail.com
 */
public class JSONObject extends JSON {

    // hold json object
    protected LinkedHashMap<String, Object> items = new LinkedHashMap<String, Object>();

    public int k = 0;
    private JSONAttribute jat;

    /**
     * JSONObject Constructor
     */
    public JSONObject() {
    }

    /**
     * Create JSONObject and put one name-value pair
     *
     * @param name  The name string
     * @param value The value linked with the key. Null accepted.
     */
    public JSONObject(String name, Object value) {
        this();
        put(name, value);
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
     * Read a chunk of the input string, match JSON Object rules.
     * <p>
     * If the string is empty, the method returns null.<br />
     * </p>
     *
     * @param src the input string to parse
     * @param idx the "cursor's" position, put 0 to begin a complete parsing
     * @return a JSON Object representing a part of the input string.
     * @throws JSONParsingException if the string is not correctly wrote.
     * @see #parse(java.lang.String src)
     */
    @Override
    public JSONObject read(String src, int idx) throws JSONParsingException {
        char c;
        int length = src.length();
        k = idx;
        while (k < length) {
            c = src.charAt(k);
            // expected attribute only
            if (c == DBLQUOTE || c == QUOTE) {
                k++;
                jat = new JSONAttribute();
                put(jat.read(src, k));
                k = jat.k;
            } else if (c == CLOSE_BRACE) {
                k++;
                break;
            } else if (c == OPEN_BRACE || c == SEPARATOR_LIST) {
                k++;
            } else if ((int) c > 32) {
                throw new JSONParsingException("Invalid input JSON string. Expected character: " + DBLQUOTE + CLOSE_BRACE + SEPARATOR_LIST + " found: " + c, k);
            } else k++;
        }
        return this;
    }

    /**
     * Put to the JSON Object one name-value pair
     *
     * @param attr The JSON Attribute containing name-value pair
     */
    public final void put(JSONAttribute attr) {
        items.put(attr.name, attr.value);
    }

    /**
     * Put to the JSON Object one name-value pair
     *
     * @param name  The key string
     * @param value The value linked with the key. Null accepted.
     */
    public final void put(String name, Object value) {
        items.put(name, value);
    }

    /**
     * Get an Object with the key name
     *
     * @param name The key string
     * @return the Object linked to the key name. Null value possibly returned.
     */
    public final Object get(String name) {
        return items.get(name);
    }

    /**
     * Get an JSONObject with the index
     *
     * @param name The key string
     * @return the Object linked to the key name. Null value possibly returned.
     */
    public final JSONObject getJSONObject(String name) {
        return (JSONObject) items.get(name);
    }

    /**
     * Get an JSONObject with the index
     *
     * @param name The key string
     * @return the Object linked to the key name. Null value possibly returned.
     */
    public final JSONArray getJSONArray(String name) {
        return (JSONArray) items.get(name);
    }

    /**
     * Get a Boolean with the key name
     *
     * @param name The key string
     * @return the Object linked to the key name. Never returns null.
     */
    public final boolean getBoolean(String name) {
        Boolean b = (Boolean) items.get(name);
        return (b == null ? false : b);
    }

    /**
     * Get an int with the key name
     *
     * @param name The key string
     * @return the Object linked to the key name. Never returns null.
     */
    public final int getInt(String name) {
        Number o = (Number) items.get(name);
        return (o == null ? 0 : o.intValue());
    }

    /**
     * Get a double with the key name
     *
     * @param name The key string
     * @return the Object linked to the key name. Never returns null.
     */
    public final double getDouble(String name) {
        Number o = (Number) items.get(name);
        return (o == null ? 0 : o.doubleValue());
    }

    /**
     * Get an long with the key name
     *
     * @param name The key string
     * @return the Object linked to the key name. Never returns null.
     */
    public final Long getLong(String name) {
        Number o = (Number) items.get(name);
        return (o == null ? 0 : o.longValue());
    }

    /**
     * Get a String with the key name
     *
     * @param name The key string
     * @return the Object linked to the key name. Never returns null.
     */
    public final String getString(String name) {
        String o = (String) items.get(name);
        return (o == null ? "" : (String) items.get(name));
    }

    @Override
    protected void toBuffer(StringBuffer s) {
        s.append(OPEN_BRACE);
        boolean start = true;
        for (Iterator<String> iter = items.keySet().iterator(); iter.hasNext(); ) {
            if (!start) {
                s.append(SEPARATOR_LIST_SPACE);
            } else start = false;
            // append attribute-name
            String si = iter.next();
            s.append(DBLQUOTE + si + DBLQUOTE);
            // separator
            s.append(SEPARATOR_ATTRIBUTE_SPACE);
            // append attribute-value
            // could be null, string, number, bool, JSON
            Object o = items.get(si);
            if (o instanceof String) {
                s.append(DBLQUOTE + o.toString() + DBLQUOTE);
            } else if (o == null) {
                s.append("null");
            } else {
                s.append(o.toString());
            }
        }
        s.append(CLOSE_BRACE);
    }

    @Override
    public final String toString() {
        StringBuffer s = new StringBuffer(items.size() * 20);
        toBuffer(s);
        return s.toString();
    }

}