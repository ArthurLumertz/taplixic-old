package com.arthurlumertz.taplixic.inventory;

import com.arthurlumertz.taplixic.items.*;

import java.util.*;

public class Inventory {

    public static final int MIN_INVENTORY_SIZE = 0;
    public static final int MAX_INVENTORY_SIZE = 24;

    private static final List<Item> inventory = new ArrayList<Item>();

    public static List<Item> getInventory() {
        return inventory;
    }

    public static void clear() {
        inventory.clear();
    }

    public static void add(Item item) {
        if (size() < MAX_INVENTORY_SIZE) {
            if (item != null) {
                inventory.add(item);
            } else {
                System.err.println("Item is null!");
            }
        }
    }

    public static void remove(Item item) {
        for (int i = 0; i < size(); ++i) {
            Item currentItem = inventory.get(i);
            if (currentItem.getName().equals(item.getName())) {
                inventory.remove(i);
                break;
            }
        }
    }

    public static Item getItem(int index) {
        if (index >= MIN_INVENTORY_SIZE && index < size()) {
            return inventory.get(index);
        }
        return null;
    }

    public static int size() {
        return inventory.size();
    }

    public static int countItemOccurrences(Item item) {
        int count = 0;
        for (Item currentItem : getInventory()) {
            if (currentItem.getName().equals(item.getName())) {
                count++;
            }
        }
        return count;
    }

    public static Item getItemByName(String name) {
        Item item = null;

        switch (name) {
            case "Plank":
                item = new Plank();
                break;
            case "Rock":
                item = new Rock();
                break;
            case "Cactus":
                item = new Cactus();
                break;
            case "Stick":
                item = new Stick();
                break;
            case "Sword":
                item = new Sword();
                break;
            case "Pickaxe":
                item = new Pickaxe();
                break;
            case "Axe":
                item = new Axe();
                break;
            case "Shield":
                item = new Shield();
                break;
            case "Coal":
                item = new Coal();
            case "Torch":
                item = new Torch();
                // TODO: EXTREMELY IMPORTANT DO NOT FORGET
        }
		
        return item;
    }

}
