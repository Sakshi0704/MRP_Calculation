package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {

    private List<Part> mainParts;
    private Map<String, List<Part>> subComponents;

    public Recipe() {
        mainParts = new ArrayList<>();
        subComponents = new HashMap<>();
    }

    public void addMainPart(String name, int qty) {
        mainParts.add(new Part(name, qty));
    }

    public void addSubComponents(String parent, List<Part> components) {
        subComponents.put(parent, components);
    }

    public List<Part> getMainParts() {
        return mainParts;
    }

    public Map<String, List<Part>> getSubComponents() {
        return subComponents;
    }
}
