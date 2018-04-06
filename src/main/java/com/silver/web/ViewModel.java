package com.silver.web;

import java.util.HashMap;
import java.util.Map;

public class ViewModel {
    private final String PREFIX = "/WEB-INF/servlet/";
    private final String SUFFIX = ".jsp";
    private String view;
    private Map<String, Object> argumentsMap = new HashMap<>();

    public ViewModel(Map<String, Object> argumentsMap, String view) {
        this.view = view;
        this.argumentsMap = argumentsMap;
    }

    public ViewModel(String view) {
        this.view = view;
    }

    public String getView() {
        return String.format("%s%s%s", PREFIX, view, SUFFIX);
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getArgumentsMap() {
        return argumentsMap;
    }

    public void setArgument(String argName, Object arg) {
        this.argumentsMap.put(argName, arg);
    }
}
