package com.company.v2;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.v2.Runner.mixedCase;
import static com.company.v2.Runner.singleCase;

class SubstringScenario {

    static final String EXTREMAL = " secret secret secret secret secret secret secret secret secret secret ";
    static final String POSITIVE = "We have sent a secret message to your secret email - [ please@hide.me ]";
    static final String NEGATIVE = "We have sent a secreт message to your secreт email - [ already stolen ]";

    static final String TARGET = "secret";
    static final String REPLACEMENT = "personal";

    static class StringReplace {
        public static void main(String[] args) {
            final Function<String, String> replacer = input -> input.replace(TARGET, REPLACEMENT);

            Logger.header("SAFE SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("SAFE SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("SAFE SUBSTRING REPLACEMENT MIXED SCENARIO", TARGET, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

    static class StringReplaceAll {
        public static void main(String[] args) {
            final Function<String, String> replacer = input -> input.replaceAll(TARGET, REPLACEMENT);

            Logger.header("ERROR-PRONE SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("ERROR-PRONE SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("ERROR-PRONE SUBSTRING REPLACEMENT MIXED SCENARIO", TARGET, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

    static class MatcherReplaceAll {
        public static void main(String[] args) {
            final Pattern pattern = Pattern.compile(TARGET, Pattern.LITERAL);
            final String replacement = Matcher.quoteReplacement(REPLACEMENT);
            final Function<String, String> replacer = input -> pattern.matcher(input).replaceAll(replacement);

            Logger.header("MATCHER SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("MATCHER SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("MATCHER SUBSTRING REPLACEMENT MIXED SCENARIO", TARGET, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

}
