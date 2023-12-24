package com.company.properties;

import java.util.Optional;

public class GlobalConfigurationProperties {
    public static final String SERVER_URL = Optional.ofNullable(System.getenv("SERVER_URL")).orElse("http://localhost:8080");
    public static final Integer EXPECTED_MAX_RESPONSE_TIME_MILLIS = Optional.ofNullable(System.getenv("EXPECTED_MAX_RESPONSE_TIME_MILLIS")).map(Integer::parseInt).orElse(200);
    public static final Double EXPECTED_MIN_SUCCESSFUL_REQUESTS_PERCENT = Optional.ofNullable(System.getenv("EXPECTED_MIN_SUCCESSFUL_REQUESTS_PERCENT")).map(Double::parseDouble).orElse(99.0);
    public static final Long HEALTHCHECK_WAIT_TIMEOUT_SECONDS = Optional.ofNullable(System.getenv("HEALTHCHECK_WAIT_TIMEOUT_SECONDS")).map(Long::parseLong).orElse(60L);

    public static class WarmUpProperties {
        public static final Integer USERS_PER_SECOND = Optional.ofNullable(System.getenv("WARM_UP_USERS_PER_SECOND")).map(Integer::parseInt).orElse(10);
        public static final Long DURATION_SECONDS = Optional.ofNullable(System.getenv("WARM_UP_DURATION_SECONDS")).map(Long::parseLong).orElse(30L);
    }

    public static class LoadTestProperties {
        public static final Double USERS_PER_SECOND = Optional.ofNullable(System.getenv("LOAD_TEST_USERS_PER_SECOND")).map(Double::parseDouble).orElse(.52);
        public static final Long DURATION_SECONDS = Optional.ofNullable(System.getenv("LOAD_TEST_DURATION_SECONDS")).map(Long::parseLong).orElse(300L);
    }
}
