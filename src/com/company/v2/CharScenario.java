package com.company.v2;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.v2.Runner.mixedCase;
import static com.company.v2.Runner.singleCase;

class CharScenario {

    static final String EXTREMAL = "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$";
    static final String POSITIVE = "We have sent a $ecret message to your $ecret email - [ please@hide.me ]";
    static final String NEGATIVE = "We have sent a secret message to your secret email - [ already stolen ]";

    static final String TARGET = "$";
    static final String REPLACEMENT = "s";

    static class StringReplaceChar {
        public static void main(String[] args) {
            char oldChar = TARGET.charAt(0);
            char newChar = REPLACEMENT.charAt(0);
            final Function<String, String> replacer = input -> input.replace(oldChar, newChar);

            Logger.header("CHAR REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("CHAR REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("CHAR REPLACEMENT MIXED SCENARIO", TARGET, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

    static class StringReplace {
        public static void main(String[] args) {
            final Function<String, String> replacer = input -> input.replace(TARGET, REPLACEMENT);

            Logger.header("SAFE CHAR-AS-SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("SAFE CHAR-AS-SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("SAFE CHAR-AS-SUBSTRING REPLACEMENT MIXED SCENARIO", TARGET, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

    static class StringReplaceAll {
        public static void main(String[] args) {
            final Function<String, String> replacer = input -> input.replaceAll(TARGET, REPLACEMENT);

            Logger.header("ERROR-PRONE CHAR-AS-SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("ERROR-PRONE CHAR-AS-SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("ERROR-PRONE CHAR-AS-SUBSTRING REPLACEMENT MIXED SCENARIO", TARGET, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

    static class MatcherReplaceAll {
        public static void main(String[] args) {
            final Pattern pattern = Pattern.compile(TARGET, Pattern.LITERAL);
            final String replacement = Matcher.quoteReplacement(REPLACEMENT);
            final Function<String, String> replacer = input -> pattern.matcher(input).replaceAll(replacement);

            Logger.header("MATCHER CHAR-AS-SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, POSITIVE);

            Logger.header("MATCHER CHAR-AS-SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            singleCase(replacer, NEGATIVE);

            Logger.header("MATCHER CHAR-AS-SUBSTRING REPLACEMENT MIXED SCENARIO", TARGET, REPLACEMENT);
            mixedCase(replacer, EXTREMAL, POSITIVE, NEGATIVE);
        }
    }

}
