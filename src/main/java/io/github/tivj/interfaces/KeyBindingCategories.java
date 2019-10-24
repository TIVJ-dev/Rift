package io.github.tivj.interfaces;

public interface KeyBindingCategories {
    void addCategory(String name, int order);
    void addNextAvailableCategory(String name, int order);
    void moveCategory(String name, int newPosition);
}
