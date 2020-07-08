package it.smartcommunitylab.challenges.app;

import java.util.HashMap;
import java.util.Map;

class CliOptions {
    private Map<String, String> options = new HashMap<>();

    private static final String OPTION_PREFIX = "--";
    public enum Options {
        CONFIG("config"), URL("url"), USERNAME("username"), PASSWORD("password");

        private String value;

        private Options(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public CliOptions(String[] cliArgs) {
        this.options = getOptions(cliArgs);
        validate(this.options);
    }

    private void validate(Map<String, String> options) {
        for (Options opt : Options.values()) {
            if (options.get(opt.getValue()) == null) {
                throw new IllegalArgumentException(
                        String.format("%s option is required", opt.getValue()));
            }
        }
    }

    public String get(Options option) {
        return options.get(option.getValue());
    }

    private final Map<String, String> getOptions(String[] args) {
        Map<String, String> options = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String option = args[i];
            if (option.startsWith(OPTION_PREFIX)) {
                options.put(option.substring(2), args[i + 1]);
            }
        }
        return options;
    }
}
