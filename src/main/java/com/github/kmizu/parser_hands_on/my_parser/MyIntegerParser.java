package com.github.kmizu.parser_hands_on.my_parser;

import com.github.kmizu.parser_hands_on.ParseFailure;
import com.github.kmizu.parser_hands_on.integer.AbstractIntegerParser;

public class MyIntegerParser extends AbstractIntegerParser {
    void consume() {
        position += 1;
    }

    Integer zero(String input) {
        if (input.length() - 1 < position) throw new ParseFailure("EOF");
        if (input != "0") throw new ParseFailure("zero should be '0'");
        consume();
        return 0;
    }

    Integer digitFirst(String input) {
        if (input.length() - 1 < position) throw new ParseFailure("EOF");
        char ch = input.charAt(position);
        if (!('1' <= ch && ch <= '9')) throw new ParseFailure("digit-first should be '1' - '9', but got " + ch);
        consume();
        return ch - '0';
    }

    Integer digitRest(String input) {
        if (input.length() - 1 < position) throw new ParseFailure("EOF");
        char ch = input.charAt(position);
        if (!('0' <= ch && ch <= '9')) throw new ParseFailure("digit-rest should be '0' - '9', but got " + ch);
        consume();
        return ch - '0';
    }

    Integer accumlate(Integer r1, Integer r2) {
        return 10 * r1 + r2;
    }

    @Override
    public Integer parse(String input) {
        try {
            save(); return zero(input);
        } catch (ParseFailure e1) {
            restore();
            Integer result = digitFirst(input);
            while (true) {
                try {
                    save(); result = accumlate(result, digitRest(input));
                } catch (ParseFailure e2) {
                    restore();
                    if (input.length() == position) {
                        return result;
                    } else {
                        throw new ParseFailure("Invalid character at " + position + ": " + input);
                    }
                }
            }
        }
    }
}
