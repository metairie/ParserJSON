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
 * @author stephane
 */
public class JSONParsingException extends Exception {

    protected int pos;

    /**
     * Constructs a new JSON parsing exception
     *
     * @param message a human readable error message.
     * @param pos     the position where the exception occured
     */
    public JSONParsingException(String message, int pos) {
        super(message);
        this.pos = pos;
    }

    @Override
    public String getMessage() {
        String s = super.getMessage();
        if (pos > -1)
            return s + " near position: " + pos;
        return s;
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
