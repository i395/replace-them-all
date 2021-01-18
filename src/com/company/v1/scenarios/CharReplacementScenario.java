package com.company.v1.scenarios;

import com.company.v1.Replacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.v1.Logger.header;
import static com.company.v1.Runner.run;

public class CharReplacementScenario {

    private static final String POSITIVE = "We have sent a $ecret message to your $ecret email - [ please@hide.me ]";
    private static final String NEGATIVE = "We have sent a public message to your public email - [ already stolen ]";
    private static final String TARGET = "$";
    private static final String REPLACEMENT = "s";

    static class CharReplacer implements Replacer {
        private final char target = TARGET.charAt(0);
        private final char replacement = REPLACEMENT.charAt(0);

        public String replace(String inputString) {
            return inputString.replace(target, replacement);
        }
    }

    static class SafeStringReplacer implements Replacer {
        public String replace(String inputString) {
            return inputString.replace(TARGET, REPLACEMENT);
        }
    }

    static class ErrorProneStringReplacer implements Replacer {
        public String replace(String inputString) {
            return inputString.replaceAll(TARGET, REPLACEMENT);
        }
    }

    static class MatcherReplacer implements Replacer {
        private final Pattern pattern = Pattern.compile(TARGET, Pattern.LITERAL);

        public String replace(String inputString) {
            return pattern.matcher(inputString).replaceAll(Matcher.quoteReplacement(REPLACEMENT));
        }
    }

    static class CharScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new CharReplacer();

            header("CHAR REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, POSITIVE);

            header("CHAR REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, NEGATIVE);
        }
    }

    static class SafeStringScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new SafeStringReplacer();

            header("SAFE CHAR-AS-SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, POSITIVE);

            header("SAFE CHAR-AS-SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, NEGATIVE);
        }
    }

    static class ErrorProneStringScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new ErrorProneStringReplacer();

            header("ERROR-PRONE CHAR-AS-SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, POSITIVE);

            header("ERROR-PRONE CHAR-AS-SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, NEGATIVE);
        }
    }

    static class MatcherScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new MatcherReplacer();

            header("MATCHER CHAR-AS-SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, POSITIVE);

            header("MATCHER CHAR-AS-SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, NEGATIVE);
        }
    }

}