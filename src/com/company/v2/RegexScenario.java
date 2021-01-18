package com.company.v2;

import java.util.function.Function;
import java.util.regex.Pattern;

import static com.company.v2.Runner.mixedCase;
import static com.company.v2.Runner.singleCase;

class RegexScenario {

    static final String EXTREMAL = "really_extremely_long_address@just_monstrous_domain_name.impossable_one";
    static final String POSITIVE = "We have sent a secret message to your secret email - [ please@hide.me ]";
    static final String NEGATIVE = "We have sent a secret message to your secret email - [ already stolen ]";

    static final String REGEX = "[^@\\s]+@[^@\\s]+\\.[^@\\s]+";
    static final String REPLACEMENT = "hidden";

    static class StringReplaceAll {
        public static void main(String[] args) {
            final Function<String, String> replacer = input -> input.replaceAll(REGEX, REPLACEMENT);

            Logger.header("STRING REGEX REPLACEMENT POSITIVE SCENARIO", REGEX, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("STRING REGEX REPLACEMENT NEGATIVE SCENARIO", REGEX, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("STRING REGEX REPLACEMENT MIXED SCENARIO", REGEX, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

    static class MatcherReplaceAll {
        public static void main(String[] args) {
            final Pattern pattern = Pattern.compile(REGEX);
            final Function<String, String> replacer = input -> pattern.matcher(input).replaceAll(REPLACEMENT);

            Logger.header("MATCHER REGEX REPLACEMENT POSITIVE SCENARIO", REGEX, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("MATCHER REGEX REPLACEMENT NEGATIVE SCENARIO", REGEX, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("MATCHER REGEX REPLACEMENT MIXED SCENARIO", REGEX, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

}
