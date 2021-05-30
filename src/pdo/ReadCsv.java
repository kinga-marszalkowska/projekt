package pdo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadCsv {

    public static Map<String, String> readCsv(String pathToCsv) {
        Map<String, String> kanaToRomanji = new LinkedHashMap<>();
        try {
            File file = new File(pathToCsv);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                System.out.println(line);
                kanaToRomanji.putIfAbsent(line[0], line[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file at the given location " + pathToCsv);
        }
        return kanaToRomanji;
    }
}
