package me.involuting.mobdrops.model.weight;

import me.involuting.mobdrops.model.Drop;

import java.util.List;
import java.util.Random;

public class WeightedLoot {

    private static final Random random = new Random();

    public static Drop pick(List<Drop> drops) {

        int totalWeight = 0;

        for (Drop drop : drops) {
            totalWeight += drop.getRarity().getWeight();
        }

        int roll = random.nextInt(totalWeight);

        int current = 0;

        for (Drop drop : drops) {
            current += drop.getRarity().getWeight();

            if (roll < current) {
                return drop;
            }
        }

        return null;
    }
}

