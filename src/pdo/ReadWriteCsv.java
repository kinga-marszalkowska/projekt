package pdo;

import models.KanaProgress;

import java.io.*;
import java.util.*;

public interface ReadWriteCsv {
    String PATH_TO_USER_PROGRESS_CSV = "src\\pdo\\userProgress.csv";
    String PATH_TO_KANA_ROMANJI_CSV = "src\\pdo\\kana_to_romanji.csv";

    default void readCsv(String pathToCsv){

    }

    default Map<String, String> readCsvConvertToMap() {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            File file = new File(PATH_TO_KANA_ROMANJI_CSV);
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
            System.out.println("No file at the given location " + PATH_TO_KANA_ROMANJI_CSV);
        }
        return map;
    }

     static ArrayList<KanaProgress> getUserProgress(){
        ArrayList<KanaProgress> kanaProgressArrayList = new ArrayList<>();

        try {
            File file = new File(PATH_TO_USER_PROGRESS_CSV);
            Scanner scanner = new Scanner(file);
            String headerLine = scanner.nextLine();
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().trim().split(",");
//                System.out.println("BEGIN TRANSACTION;");
//                System.out.println(String.format("INSERT INTO kana VALUES(\"%s\",\"%s\",0,0,0,0);", line[0], line[1]));
//                System.out.println("COMMIT;");
                try{
                    //todo skip first line
                    kanaProgressArrayList.add(new KanaProgress(line));
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("no record, skipping");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file at the given location " + PATH_TO_USER_PROGRESS_CSV);
        }

        return kanaProgressArrayList;
    }

    default boolean writeUserProgressToCsv(KanaProgress kanaProgress){
        try {
            File file = new File(PATH_TO_USER_PROGRESS_CSV);
            File temp = new File("src\\pdo\\userProgressTemp.csv");

            FileWriter csvWriter = new FileWriter(temp);
            Scanner scanner = new Scanner(file);
            String newLine = kanaProgress.toCsvLine();
            String[] newLineArr = newLine.split(",");

            System.out.println(newLine);
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                String[] lineArr = line.split(",");
                // if kanas are the same - this is the line to modify
                if((newLineArr[0]).equals(lineArr[0])){
                    System.out.println(line);
                    csvWriter.write(newLine);
                    csvWriter.write("\n");
                }else{
                    csvWriter.write(line);
                    csvWriter.write("\n");
                }

            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.out.println("user progress not saved");
        }

        return false;
    }

    default boolean writeManyUserProgressToCsv(ArrayList<KanaProgress> kanaProgress){
        return false;
    }

    default void resetAllProgress(){
        // todo set all numeric columns to 0
    }


}
