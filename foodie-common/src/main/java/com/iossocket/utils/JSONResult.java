package com.iossocket.utils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class JSONResult {

    private Object data;
    private Object error;

    public static JSONResult error(Object error) {
        return new JSONResult(null, error);
    }

    public static JSONResult error(String errorMsg) {
        Map<String, String> error = new HashMap<>();
        error.put("message", errorMsg);
        return new JSONResult(null, error);
    }

    public static JSONResult success(Object data) {
        return new JSONResult(data, null);
    }
}
