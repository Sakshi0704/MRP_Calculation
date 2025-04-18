// --- MRPPlanner.java ---
package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MRPPlanner {

    private Recipe recipe;
    private Map<String, Integer> inventory;

    MRPPlanner(Recipe recipe, List<Inventory> onHandInventory) {
        this.recipe = recipe;
        this.inventory = new HashMap<>();
        for (Inventory item : onHandInventory) {
            inventory.put(item.getPartName(), item.getQuantityAvailable());
        }
    }

    Map<String, Integer> calculateTotalRequirements(int bicycles) {
        Map<String, Integer> total = new HashMap<>();

        for (Part part : recipe.getMainParts()) {
            int qtyNeeded = part.getQuantityRequiredPerBicycle() * bicycles;
            if (recipe.getSubComponents().containsKey(part.getName())) {
                for (Part sub : recipe.getSubComponents().get(part.getName())) {
                    int totalSubQty = sub.getQuantityRequiredPerBicycle() * qtyNeeded;
                    total.put(sub.getName(), total.getOrDefault(sub.getName(), 0) + totalSubQty);
                }
            } else {
                total.put(part.getName(), total.getOrDefault(part.getName(), 0) + qtyNeeded);
            }
        }
        return total;
    }

    Map<String, Integer> calculateToBeProcured(Map<String, Integer> totalReq) {
        Map<String, Integer> toBuy = new HashMap<>();
        for (String part : totalReq.keySet()) {
            int required = totalReq.get(part);
            int available = inventory.getOrDefault(part, 0);
            toBuy.put(part, Math.max(0, required - available));
        }
        return toBuy;
    }

    int calculateMaxBicycles() {
        int maxBicycles = 0;
        Map<String, Integer> workingInventory = new HashMap<>(inventory);

        while (true) {
            Map<String, Integer> neededParts = calculateTotalRequirements(1); // requirements for 1 bicycle
            boolean canBuild = true;

            // Check if we can build one bicycle
            for (Map.Entry<String, Integer> entry : neededParts.entrySet()) {
                String part = entry.getKey();
                int requiredQty = entry.getValue();
                int availableQty = workingInventory.getOrDefault(part, 0);

                if (availableQty < requiredQty) {
                    canBuild = false;
                    break;
                }
            }

            // If we can build, reduce inventory and count one more bicycle
            if (canBuild) {
                for (Map.Entry<String, Integer> entry : neededParts.entrySet()) {
                    String part = entry.getKey();
                    int requiredQty = entry.getValue();
                    workingInventory.put(part, workingInventory.get(part) - requiredQty);
                }
                maxBicycles++;
            } else {
                break; // Can't build more
            }
        }

        // Update real inventory after calculating
        inventory = workingInventory;
        return maxBicycles;
    }

    Map<String, Integer> getCurrentInventory() {
        return inventory;
    }

    void updateInventory(Map<String, Integer> additionalStock) {
        for (String part : additionalStock.keySet()) {
            int addQty = additionalStock.get(part);
            inventory.put(part, inventory.getOrDefault(part, 0) + addQty);
        }
    }
}
