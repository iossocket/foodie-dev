package com.iossocket.utils;

public class DesensitizationUtil {
    private static final int SIZE = 6;
    private static final String SYMBOL = "*";

    public static String commonDisplay(String value) {
        if (null == value || "".equals(value)) {
            return value;
        }

        int len = value.length();
        int paramOne = len / 2;
        int paramTwo = paramOne - 1;
        int paramThree = len % 2;

        StringBuilder stringBuilder = new StringBuilder();
        if (len <= 2) {
            if (paramThree == 1) {
                return SYMBOL;
            }
            stringBuilder.append(SYMBOL);
            stringBuilder.append(value.charAt(len - 1));
        } else {
            if (paramTwo <= 0) {
                stringBuilder.append(value, 0, 1);
                stringBuilder.append(SYMBOL);
                stringBuilder.append(value, len - 1, len);

            } else if (paramTwo >= SIZE / 2 && SIZE + 1 != len) {
                int paramFive = (len - SIZE) / 2;
                stringBuilder.append(value, 0, paramFive);
                for (int i = 0; i < SIZE; i++) {
                    stringBuilder.append(SYMBOL);
                }
                stringBuilder.append(value, len - (paramFive + 1), len);
            } else {
                int paramFour = len - 2;
                stringBuilder.append(value, 0, 1);
                for (int i = 0; i < paramFour; i++) {
                    stringBuilder.append(SYMBOL);
                }
                stringBuilder.append(value, len - 1, len);
            }
        }
        return stringBuilder.toString();
    }
}
