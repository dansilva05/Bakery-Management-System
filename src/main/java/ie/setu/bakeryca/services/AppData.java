package ie.setu.bakeryca.services;

import ie.setu.bakeryca.models.BakeryStore;

public class AppData {

    private static final String SAVE_FILE = "bakery-store.dat";
    private static BakeryStore store = new BakeryStore();

    private AppData() {}

    public static BakeryStore getStore() {
        return store;
    }

    // replaces the store after loading from file
    public static void setStore(BakeryStore newStore) {
        store = newStore;
    }

    // creates a fresh/empty store for the reset
    public static void resetStore() {
        store = new BakeryStore();
    }

    public static String getSaveFile() {
        return SAVE_FILE;
    }
}