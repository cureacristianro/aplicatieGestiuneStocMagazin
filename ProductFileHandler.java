package aplicatieGestiuneStocMagazin;

import java.io.*;
import java.util.ArrayList;

public class ProductFileHandler {
    private static final String FILE_NAME = "produse.txt";

    public static void saveProducts(ArrayList<Produs> produse) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(produse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Produs> loadProducts() {
        ArrayList<Produs> produse = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                produse = (ArrayList<Produs>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return produse;
    }
}
