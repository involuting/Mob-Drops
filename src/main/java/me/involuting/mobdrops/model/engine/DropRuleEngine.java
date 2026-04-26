package me.involuting.mobdrops.model.engine;

import me.involuting.mobdrops.model.context.DropContext;
import me.involuting.mobdrops.model.rule.DropRule;

import java.util.ArrayList;
import java.util.List;

public class DropRuleEngine {

    private static final List<DropRule> rules = new ArrayList<>();

    public static void register(DropRule rule) {
        rules.add(rule);
    }

    public static double getMultiplier(DropContext ctx) {

        double multiplier = 1.0;

        for (DropRule rule : rules) {
            if (rule.matches(ctx)) {
                multiplier *= rule.getMultiplier();
            }
        }

        return multiplier;
    }
}
