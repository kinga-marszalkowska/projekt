package services;

import models.KanaProgress;
import pdo.DatabaseConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public interface DBCommunication {

    default boolean updateKana(KanaProgress kanaProgress){
        try {
            DatabaseConnection.updateKanaToDB(kanaProgress);
            return true;
        } catch (SQLException e) {
            System.out.println("Coulnd't update kana");
            return false;
        }
    }

    default ArrayList<KanaProgress> readKanas(){
        try {
            return DatabaseConnection.getKanasFromDB();
        } catch (SQLException e) {
            System.out.println("Coulnd't read kanas");
            return null;
        }
    }

    default Map<String, Integer> kanasArrayToMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        ArrayList<KanaProgress> all = readKanas();

        for (int i = 0; i < all.size(); i++) {
            map.putIfAbsent(all.get(i).getMora(), i);
        }

        return map;
    }
}
