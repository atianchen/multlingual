package com.yonyou.multlingual.client.core;

public class ConvertConfig {

    public static final String DEFAULT_DEFAULT_PREFIX = "";
    public static final String DEFAULT_DEFAULT_SUFFIX = "js";

    private String prefix = DEFAULT_DEFAULT_PREFIX;

    private String suffix = DEFAULT_DEFAULT_SUFFIX;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
