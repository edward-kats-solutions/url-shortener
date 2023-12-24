package com.company.utils;

import com.company.properties.GlobalConfigurationProperties;
import org.apache.commons.lang3.ThreadUtils;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HealthCheckGuardUtil {

    public static boolean isHealthy() {
        final String HEALTHCHECK_URL = GlobalConfigurationProperties.SERVER_URL + "/health";
        final long RETRY_INTERVAL_DURATION_SECONDS = 5L;

        final long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(GlobalConfigurationProperties.HEALTHCHECK_WAIT_TIMEOUT_SECONDS);
        boolean isHealthy = false;

        while (System.currentTimeMillis() < endTime) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(HEALTHCHECK_URL))
                        .timeout(Duration.ofSeconds(5))
                        .GET()
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    System.out.println("Healthcheck Guard: success. URL: " + HEALTHCHECK_URL);
                    isHealthy = true;
                    break;
                }
            } catch (ConnectException e) {
                System.err.println("Healthcheck Guard error: host not reachable. URL: " + HEALTHCHECK_URL);
            } catch (Exception e) {
                System.err.println("Healthcheck Guard error: " + e.getMessage());
                e.printStackTrace();
            }

            ThreadUtils.sleepQuietly(Duration.ofSeconds(RETRY_INTERVAL_DURATION_SECONDS));
        }

        return isHealthy;
    }
}
