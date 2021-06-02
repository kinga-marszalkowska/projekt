package pdo;

import models.KanaProgress;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public interface ReadCsv {

    default void readCsv(String pathToCsv){

    }

    default Map<String, String> readCsvConvertToMap(String pathToCsv) {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            File file = new File(pathToCsv);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                try{
                    map.putIfAbsent(line[0], line[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("no record, skipping");
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("No file at the given location " + pathToCsv);
        }
        return map;
    }

    default ArrayList<KanaProgress> getUserProgress(String pathToCsv){
        ArrayList<KanaProgress> kanaProgressArrayList = new ArrayList<>();
        try {
            File file = new File(pathToCsv);
            Scanner scanner = new Scanner(file);
            String headerLine = scanner.nextLine();
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                try{
                    //todo skip first line
                    kanaProgressArrayList.add(new KanaProgress(line));
                    System.out.println(kanaProgressArrayList);
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("no record, skipping");
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("No file at the given location " + pathToCsv);
        }
        return kanaProgressArrayList;
    }

    default boolean writeUserProgressToCsv(KanaProgress kanaProgress){
        return false;
    }

    default boolean writeManyUserProgressToCsv(ArrayList<KanaProgress> kanaProgress){
        return false;
    }

    default void resetAllProgress(){
        // todo set all numeric columns to 0
    }


}
