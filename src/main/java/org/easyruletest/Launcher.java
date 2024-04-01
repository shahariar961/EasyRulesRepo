package org.easyruletest;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class Launcher
{

    public static void main(String[] args) {
        // Instantiate Rules
        Rules rules = RulesConfig.defineRules();

        // Create a RulesEngine
        RulesEngine rulesEngine = new DefaultRulesEngine();

        // Facts (these would be populated from actual sensor readings)
        Facts facts = new Facts();
        facts.put("co2Level", 1100); // Example CO2 level
        facts.put("temperature", 36); // Example temperature
        facts.put("windSpeed", 25); // Example wind speed
        facts.put("humidity", 40); // Example humidity level

        // Fire the rules
        rulesEngine.fire(rules, facts);
    }
}