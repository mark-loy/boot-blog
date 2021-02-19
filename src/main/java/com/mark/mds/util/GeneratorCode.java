package com.mark.mds.util;

import java.util.Random;

/**
 * 生成4位验证码
 * @author 木可
 * @version 1.0
 * @date 2021/2/1 12:36
 */
public class GeneratorCode {

    public static String getCode() {

        String template = "ABCDEFJHIGKLMNOPQRSTUVWSYZ0123456789";

        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char charAt = template.charAt(new Random().nextInt(template.length()));
            sb.append(charAt);
        }

        return sb.toString();
    }
}
