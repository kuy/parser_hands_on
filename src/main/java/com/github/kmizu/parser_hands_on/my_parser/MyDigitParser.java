package com.github.kmizu.parser_hands_on.my_parser;

import com.github.kmizu.parser_hands_on.ParseFailure;
import com.github.kmizu.parser_hands_on.digit.AbstractDigitParser;

public class MyDigitParser extends AbstractDigitParser {
    @Override
    public Integer parse(String input) {
        if (1 < input.length()) throw new ParseFailure("length should be 1, but " + input.length());
        char ch = input.charAt(position);
        if (!('0' <= ch && ch <= '9')) throw new ParseFailure("character '" + ch + "' is not input");
        return ch - '0';
    }
}
