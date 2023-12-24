package com.company.scenario;

import com.company.ApiRequests;
import com.company.properties.GlobalConfigurationProperties;
import com.company.utils.HealthCheckGuardUtil;
import com.company.utils.UrlGeneratorUtil;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class BaseScenarioSimulation extends Simulation {

    private final ApiRequests apiRequests = new ApiRequests();

    protected final Iterator<Map<String, Object>> RANDOM_URL_GENERATOR_FEEDER =
            Stream.generate((Supplier<Map<String, Object>>) ()
                    -> Collections.singletonMap("longUrl", UrlGeneratorUtil.generateRandomUrl())
            ).iterator();
    protected final HttpProtocolBuilder HTTP_PROTOCOL = HttpDsl
            .http
            .baseUrl(GlobalConfigurationProperties.SERVER_URL)
            .acceptHeader("application/json")
            .disableWarmUp()
            .disableCaching()
            .disableFollowRedirect()
            ;


    public BaseScenarioSimulation() {
        if (!HealthCheckGuardUtil.isHealthy()) {
            String message = String.format(
                    "Healthcheck to %s/health did not respond successfully during %s seconds. Simulation did not start and has been aborted...",
                    GlobalConfigurationProperties.SERVER_URL,
                    GlobalConfigurationProperties.HEALTHCHECK_WAIT_TIMEOUT_SECONDS
            );
            throw new RuntimeException(message);
        }

        this.setUp(
                getPopulationBuilder()
        )
                .assertions(
                        getAssertions()
                );
    }

    protected ApiRequests getApiRequests() {
        return apiRequests;
    }


    /**
     * 1. Scenario
     * 2. Inject Open -> load params, such as users per second
     * 3. Protocol -> HTTP
     * 4. Assertions -> List.of() by default, overridable per simulation
     */
    public abstract PopulationBuilder getPopulationBuilder();


    public abstract ScenarioBuilder setUpScenario();

    public List<Assertion> getAssertions() {
        return List.of();
    }

}
