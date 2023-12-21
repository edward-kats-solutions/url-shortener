package com.company.utils;

import java.security.SecureRandom;
import java.util.Objects;

public class UrlWrapper {
    private static final SecureRandom RANDOM = new SecureRandom();

    private final String url;
    private final int hashCode;

    public UrlWrapper(String url) {
        if (url == null) {
            throw new NullPointerException("URL cannot be null");
        }
        this.url = url;
        this.hashCode = RANDOM.nextInt(Integer.MAX_VALUE);
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlWrapper that = (UrlWrapper) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return this.url;
    }
}