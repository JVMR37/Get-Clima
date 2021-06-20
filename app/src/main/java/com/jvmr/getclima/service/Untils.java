package com.jvmr.getclima.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Untils {
    public static List<String> convertObjectToListString(Object obj) {
        List<String> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((String[]) obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<String>((Collection<String>) obj);
        }
        return list;
    }
}
