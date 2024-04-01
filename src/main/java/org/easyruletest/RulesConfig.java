package org.easyruletest;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.support.composite.UnitRuleGroup;

public class RulesConfig {

    private static final int CO2_THRESHOLD = 1000; // example threshold
    private static final int TEMPERATURE_THRESHOLD = 35; // Celsius
    private static final int WIND_THRESHOLD = 20; // km/h
    private static final int HUMIDITY_THRESHOLD = 30; // percentage

    public static Rules defineRules() {
        Rules rules = new Rules();

        // CO2 Threshold Rule
        Rule co2ThresholdRule = new RuleBuilder()
                .name("CO2ThresholdRule")
                .description("Triggers if CO2 level exceeds threshold")
                .priority(1) // higher priority within the activation group
                .when(facts -> ((Integer) facts.get("co2Level")) > CO2_THRESHOLD)
                .then(facts -> System.out.println("CO2 threshold exceeded. Alarm activated."))
                .build();

        // Temperature Threshold Rule
        Rule temperatureThresholdRule = new RuleBuilder()
                .name("TemperatureThresholdRule")
                .description("Triggers if temperature exceeds threshold")
                .priority(2) // lower priority within the activation group
                .when(facts -> ((Integer) facts.get("temperature")) > TEMPERATURE_THRESHOLD)
                .then(facts -> System.out.println("Temperature threshold exceeded. Alarm activated."))
                .build();

        // Wind Threshold Rule
        Rule windThresholdRule = new RuleBuilder()
                .name("WindThresholdRule")
                .description("Triggers if wind speed exceeds threshold")
                .when(facts -> ((Integer) facts.get("windSpeed")) > WIND_THRESHOLD)
                .then(facts -> System.out.println("Wind threshold exceeded."))
                .build();

        // Humidity Threshold Rule
        Rule humidityThresholdRule = new RuleBuilder()
                .name("HumidityThresholdRule")
                .description("Triggers if humidity is below threshold")
                .when(facts -> ((Integer) facts.get("humidity")) < HUMIDITY_THRESHOLD)
                .then(facts -> System.out.println("Humidity threshold exceeded. Alarm activated."))
                .build();

        // Composite Rule for Fire Spread Alarm
        UnitRuleGroup fireSpreadRule = new UnitRuleGroup("FireSpreadRule");
        fireSpreadRule.addRule(co2ThresholdRule);
        fireSpreadRule.addRule(temperatureThresholdRule);
        fireSpreadRule.addRule(windThresholdRule);
        Rule conditionRule = new RuleBuilder().name("ConditionRule")
                .when(facts -> ((Integer) facts.get("co2Level")) > CO2_THRESHOLD || ((Integer) facts.get("temperature")) > TEMPERATURE_THRESHOLD)
                .then(facts -> {})
                .build();
        Rule actionRule = new RuleBuilder().name("ActionRule")
                .when(facts -> true)
                .then(facts -> System.out.println("Fire spread risk detected. Alarm activated."))
                .build();
        fireSpreadRule.addRule(conditionRule);
        fireSpreadRule.addRule(actionRule);

        // Registering all rules
        rules.register(co2ThresholdRule);
        rules.register(temperatureThresholdRule);
        rules.register(windThresholdRule);
        rules.register(humidityThresholdRule);
        rules.register(fireSpreadRule);

        return rules;
    }
}