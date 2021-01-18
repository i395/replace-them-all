package com.company.v1.scenarios;

import com.company.v1.Replacer;

import java.util.regex.Pattern;

import static com.company.v1.Logger.header;
import static com.company.v1.Runner.run;

public class RegexpReplacementScenario {

    private static final String POSITIVE = "We have sent a secret message to your secret email - [ please@hide.me ]";
    private static final String NEGATIVE = "We have sent a secret message to your secret email - [ already stolen ]";
    private static final String REGEX = "[^@\\s]+@[^@\\s]+\\.[^@\\s]+";
    private static final String REPLACEMENT = "hidden";

    static class StringReplacer implements Replacer {
        public String replace(String inputString) {
            return inputString.replaceAll(REGEX, REPLACEMENT);
        }
    }

    static class MatcherReplacer implements Replacer {
        private final Pattern pattern = Pattern.compile(REGEX);

        public String replace(String inputString) {
            return pattern.matcher(inputString).replaceAll(REPLACEMENT);
        }
    }

    static class StringScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new StringReplacer();

            header("STRING REGEX REPLACEMENT POSITIVE SCENARIO", REGEX, REPLACEMENT);
            run(replacer, POSITIVE);

            header("STRING REGEX REPLACEMENT NEGATIVE SCENARIO", REGEX, REPLACEMENT);
            run(replacer, NEGATIVE);
        }
    }

    static class MatcherScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new MatcherReplacer();

            header("MATCHER REGEX REPLACEMENT POSITIVE SCENARIO", REGEX, REPLACEMENT);
            run(replacer, POSITIVE);

            header("MATCHER REGEX REPLACEMENT NEGATIVE SCENARIO", REGEX, REPLACEMENT);
            run(replacer, NEGATIVE);
        }
    }

}