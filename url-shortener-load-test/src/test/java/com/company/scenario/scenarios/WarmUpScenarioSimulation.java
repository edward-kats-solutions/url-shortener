package com.company.scenario.scenarios;

import com.company.properties.GlobalConfigurationProperties;
import com.company.scenario.BaseScenarioSimulation;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;

public class WarmUpScenarioSimulation extends BaseScenarioSimulation {

    @Override
    public void before() {
        System.out.printf("""
                |-----------------------------------------
                | parameters: WarmUpScenarioSimulation
                |               \s
                | Warm Up run for duration (seconds): %s
                | (constant users per seconds) Number of users: %s
                |               \s
                |-----------------------------------------
                """,
                GlobalConfigurationProperties.WarmUpProperties.DURATION_SECONDS,
                GlobalConfigurationProperties.WarmUpProperties.USERS_PER_SECOND);
    }

    @Override
    public PopulationBuilder getPopulationBuilder() {
        return setUpScenario()
                .injectOpen(
                        CoreDsl
                                .constantUsersPerSec(GlobalConfigurationProperties.WarmUpProperties.USERS_PER_SECOND)
                                .during(GlobalConfigurationProperties.WarmUpProperties.DURATION_SECONDS)
                )
                .protocols(HTTP_PROTOCOL);
    }

    @Override
    public ScenarioBuilder setUpScenario() {
        return CoreDsl.scenario("WarmUp Scenario Simulation")
                .feed(RANDOM_URL_GENERATOR_FEEDER)
                .exec(getApiRequests().shortenUrlRequest())
                .exitHereIfFailed()
                .exec(getApiRequests().getLongUrl());
    }
}
