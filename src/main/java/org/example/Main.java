package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Recipe recipe = new Recipe();

        recipe.addMainPart("seat", 1);
        recipe.addMainPart("frame", 1);
        recipe.addMainPart("brake set", 2);
        recipe.addMainPart("handlebar", 1);
        recipe.addMainPart("wheel", 2);
        recipe.addMainPart("tire", 2);
        recipe.addMainPart("chain", 1);
        recipe.addMainPart("crank set", 1);
        recipe.addMainPart("pedal", 2);


        List<Part> brakeSetParts = Arrays.asList(
                new Part("brake paddle", 1),
                new Part("brake cable", 1),
                new Part("lever set", 1),
                new Part("brake shoe", 2)
        );
        recipe.addSubComponents("brake set", brakeSetParts);


        List<Inventory> inventory = Arrays.asList(
                new Inventory("seat", 50),
                new Inventory("frame", 30),
                new Inventory("brake paddle", 40),
                new Inventory("brake cable", 36),
                new Inventory("lever set", 40),
                new Inventory("brake shoe", 100),
                new Inventory("handlebar", 25),
                new Inventory("wheel", 60),
                new Inventory("tire", 70),
                new Inventory("chain", 30),
                new Inventory("crank set", 35),
                new Inventory("pedal", 70)
        );

        MRPPlanner planner = new MRPPlanner(recipe, inventory);

        int totalBicycles = 20;
        // Initial build attempt
        int canBuild = planner.calculateMaxBicycles();
        int remainingToProduce = totalBicycles - canBuild;

        System.out.println("Total bicycles requested: " + totalBicycles);
        System.out.println("Can build from inventory: " + canBuild);
        System.out.println("Remaining to build (needs procurement): " + remainingToProduce);

        /* Show total requirement and what's missing*/
        Map<String, Integer> totalReq = planner.calculateTotalRequirements(totalBicycles);
        Map<String, Integer> toBuy = planner.calculateToBeProcured(totalReq);

        /*procuring missing stock*/
        planner.updateInventory(toBuy);

        /* Try building remaining bicycles after procurement */
        int remainingBuilt = planner.calculateMaxBicycles();

        System.out.println("After procurement, remaining bicycles built: " + remainingBuilt);
        System.out.println("All bicycles manufactured successfully!");
    }
}
