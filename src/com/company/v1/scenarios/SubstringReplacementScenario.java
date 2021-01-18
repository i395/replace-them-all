package com.company.v1.scenarios;

import com.company.v1.Replacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.v1.Logger.header;
import static com.company.v1.Runner.run;

public class SubstringReplacementScenario {

    private static final String POSITIVE = "We have sent a secret message to your secret email - [ please@hide.me ]";
    private static final String NEGATIVE = "We have sent a secreт message to your secreт email - [ already stolen ]";
    private static final String TARGET = "secret";
    private static final String REPLACEMENT = "personal";

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

    static class SafeStringScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new SafeStringReplacer();

            header("SAFE SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, POSITIVE);

            header("SAFE SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, NEGATIVE);
        }
    }

    static class ErrorProneStringScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new ErrorProneStringReplacer();

            header("ERROR-PRONE SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, POSITIVE);

            header("ERROR-PRONE SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            //run(replacer, NEGATIVE);
        }
    }

    static class MatcherScenario {
        public static void main(String[] args) throws Exception {
            var replacer = new MatcherReplacer();

            header("MATCHER SUBSTRING REPLACEMENT POSITIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, POSITIVE);

            header("MATCHER SUBSTRING REPLACEMENT NEGATIVE SCENARIO", TARGET, REPLACEMENT);
            run(replacer, NEGATIVE);
        }
    }

}