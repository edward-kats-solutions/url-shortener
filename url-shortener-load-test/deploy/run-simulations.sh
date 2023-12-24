#!/bin/bash
mvn gatling:test -Dgatling.simulationClass=com.company.scenario.scenarios.WarmUpScenarioSimulation
mvn gatling:test -Dgatling.simulationClass=com.company.scenario.scenarios.LoadTestScenarioSimulation