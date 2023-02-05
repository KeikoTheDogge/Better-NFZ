package Reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Klasa MenuReader służy do wczytywania pliku tekstowego z poleceniami z menu
 */
public class MenuReader {
    /**
     * Metoda readFile czyta plik txt (jeden z trzech plików utworzonych dla trzech różnych menu - głównego,
     * doktora i pacejtna)
     *
     * @param fileName: nazwa wczytywanego pliku
     */
    public static void readFile(String fileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.printf("Nie można wczytać pliku z menu: %s", fileName);
            return;
        }
        try {
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Nie można wczytać pliku z opisem menu");
        }
    }
}
