package ie.setu.bakeryca.services;

import ie.setu.bakeryca.models.BakeryStore;

import java.io.*;

public class StoreFileManager {

    private StoreFileManager() {}

    public static void saveStore(BakeryStore store, String fileName) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(store);
        out.close();
    }

    public static BakeryStore loadStore(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        BakeryStore store = (BakeryStore) in.readObject();
        in.close();
        return store;
    }
}