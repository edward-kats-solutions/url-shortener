package com.company.scenario.scenarios;

import com.company.properties.GlobalConfigurationProperties;
import com.company.scenario.BaseScenarioSimulation;
import io.gatling.javaapi.core.Assertion;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.util.List;


public class LoadTestScenarioSimulation extends BaseScenarioSimulation {

    @Override
    public void before() {
        System.out.printf("""
                        |-----------------------------------------
                        |parameters: LoadTestScenarioSimulation
                        |               \s
                        | Load Test run for duration (seconds): %s
                        | (constant users per seconds) Number of users: %s
                        |               \s
                        |-----------------------------------------
                        """,
                GlobalConfigurationProperties.LoadTestProperties.DURATION_SECONDS,
                GlobalConfigurationProperties.LoadTestProperties.USERS_PER_SECOND);
    }


    @Override
    public PopulationBuilder getPopulationBuilder() {
        return setUpScenario()
                .injectOpen(
                        CoreDsl
                                .constantUsersPerSec(GlobalConfigurationProperties.LoadTestProperties.USERS_PER_SECOND)
                                .during(GlobalConfigurationProperties.LoadTestProperties.DURATION_SECONDS)
                )
                .protocols(HTTP_PROTOCOL);
    }

    @Override
    public ScenarioBuilder setUpScenario() {
        return CoreDsl.scenario("Load Test Scenario Simulation")
                .feed(RANDOM_URL_GENERATOR_FEEDER)
                .exec(getApiRequests().shortenUrlRequest())
                .exitHereIfFailed()
                .repeat(365 * 10)
                .on(getApiRequests().getLongUrl())
                ;
    }

    @Override
    public List<Assertion> getAssertions() {
        return List.of(
                CoreDsl.global().responseTime().max().lt(GlobalConfigurationProperties.EXPECTED_MAX_RESPONSE_TIME_MILLIS),
                CoreDsl.global().successfulRequests().percent().gt(GlobalConfigurationProperties.EXPECTED_MIN_SUCCESSFUL_REQUESTS_PERCENT)
        );
    }
}
