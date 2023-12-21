package com.company.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UrlGeneratorUtil {
    private static final String[] DOMAINS = {"com", "net", "org", "io"};
    private static final String[] PROTOCOLS = { "http://", "https://" };
    private static final java.util.Random RANDOM = new java.util.Random();

    public static String generateRandomUrl() {
        try {
            String domain = "www." + generateRandomString(5) + "." + DOMAINS[RANDOM.nextInt(DOMAINS.length)];
            String path = "/" + generateRandomString(10);
            String protocol = getRandomProtocol();
            return protocol + domain + path;
        } catch (Exception e) {
            System.out.println("Fuck!!!");
            return "";
        }
    }

    private static String getRandomProtocol() {
        int random = RANDOM.nextInt(100);
        if (random > 20) {
            return PROTOCOLS[1];
        }

        return PROTOCOLS[0];
    }

    private static String generateRandomString(int length) {
        return RANDOM.ints(48, 122)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
