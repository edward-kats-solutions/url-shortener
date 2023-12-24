package com.company;

import com.company.utils.UrlWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpDsl;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


public class ApiRequests {

    private static final Integer URL_AVERAGE_USAGE_PER_YEAR = 10 * 365;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final ConcurrentHashMap<UrlWrapper, Integer> urls = new ConcurrentHashMap<>();


    public ChainBuilder shortenUrlRequest() {
        return CoreDsl.exec(
                HttpDsl
                        .http("Shorten URL")
                        .post("/api/v1/shorten")
                        .body(CoreDsl.ElFileBody("shorten-url.json"))
                        .asJson()
                        .check(HttpDsl.status().is(200))
                        .check(CoreDsl.jsonPath("$.shortUrl").saveAs("shortUrl"))
                        .transformResponse(((response, session) -> {
                            try {
                                var url = mapper.readTree(response.body().string()).get("shortUrl").asText();
                                var wrapper = new UrlWrapper(url);
                                urls.put(wrapper, 0);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                                session.markAsFailed();
                            }
                            return response;
                        }))
        );
    }


    public ChainBuilder getLongUrl() {
        return CoreDsl.exec(
                HttpDsl
                        .http("Get Full Url")
                        .get(getRandomUrl())
                        .disableFollowRedirect()
                        .check(HttpDsl.status().is(302))
        );
    }

    private Function<Session, String> getRandomUrl() {
        return session -> {
            int random = RANDOM.nextInt(urls.size());
            Map.Entry<UrlWrapper, Integer> entry = urls.entrySet().stream()
                    .skip(random)
                    .findFirst()
                    .get();

            String url = entry.getKey().getUrl();
            urls.computeIfPresent(entry.getKey(), (key, value) -> value + 1);

            if (entry.getValue() > URL_AVERAGE_USAGE_PER_YEAR) {
                urls.remove(entry.getKey());
            }

            return url;
        };
    }


}
