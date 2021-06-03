package pdo;

import models.KanaProgress;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main implements ReadWriteCsv{

    public static void main(String[] args) {
        ArrayList<KanaProgress> all = ReadWriteCsv.getUserProgress();
        KanaProgress kanaProgress = all.get(0);
        kanaProgress.setMasteredCount(1);

        try {

                DatabaseConnection.updateKanaToDB(kanaProgress);

////            System.out.println(DatabaseConnection.getKanasFromDB().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
