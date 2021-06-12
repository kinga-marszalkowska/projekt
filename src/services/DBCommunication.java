package services;

import models.KanaProgress;
import pdo.DatabaseConnection;

import java.sql.SQLException;
import java.util.*;

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
            System.out.println(e);
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

    default ArrayList<KanaProgress> getKanasForChallenge(int roundsCount){
        Random rand = new Random();
        ArrayList<KanaProgress> challengeKanas = new ArrayList<>();
        try {
            ArrayList<KanaProgress> kana = DatabaseConnection.getKanasForChallengeFromDB();
            if(kana.size() > roundsCount){
                int count = 0;
                while (count < roundsCount){
                    KanaProgress kanaProgress = kana.get(rand.nextInt(kana.size()));
                    if(! challengeKanas.contains(kanaProgress)){
                        challengeKanas.add(kanaProgress);
                        count++;
                    }
                }
            } else {
                challengeKanas.addAll(kana);
                int remaining = roundsCount - challengeKanas.size();
                ArrayList<KanaProgress> all = readKanas();
                while (remaining > 0){
                    KanaProgress kanaProgress = all.get(rand.nextInt(all.size()));
                    if(! challengeKanas.contains(kanaProgress)){
                        challengeKanas.add(kanaProgress);
                        remaining--;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return challengeKanas;
    }

    default ArrayList<KanaProgress> getKanasForTraining(int roundsCount){
        Random rand = new Random();
        ArrayList<KanaProgress> trainingKanas = new ArrayList<>();
        try {
            ArrayList<KanaProgress> kana = DatabaseConnection.getKanasForTrainingFromDB();
            if(kana.size() > roundsCount){
                int count = 0;
                while (count < roundsCount){
                    KanaProgress kanaProgress = kana.get(rand.nextInt(kana.size()));
                    if(! trainingKanas.contains(kanaProgress)){
                        trainingKanas.add(kanaProgress);
                        count++;
                    }
                }
            } else {
                trainingKanas.addAll(kana);
                int remaining = roundsCount - trainingKanas.size();
                ArrayList<KanaProgress> all = readKanas();
                while (remaining > 0){
                    KanaProgress kanaProgress = all.get(rand.nextInt(all.size()));
                    if(! trainingKanas.contains(kanaProgress)){
                        trainingKanas.add(kanaProgress);
                        remaining--;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainingKanas;
    }
//        ArrayList<KanaProgress> challengeKanas = new ArrayList<>();
//        Random rand = new Random();
//        try {
//            ArrayList<KanaProgress> toPractice = DatabaseConnection.getKanasToPracticeFromDB();
//            if(toPractice.size() == roundsCount) return toPractice;
//            else if(toPractice.size() > roundsCount){
//                // select kanas to practice randomly
//                for (int i = 0; i < roundsCount; i++) {
//                    KanaProgress kanaProgress = toPractice.get(rand.nextInt(toPractice.size()));
//                    challengeKanas.add(kanaProgress);
//                }
//                return challengeKanas;
//            }
//            else {
//                if(toPractice.size() > 0) challengeKanas.addAll(toPractice);
//                ArrayList<KanaProgress> repeated = DatabaseConnection.getRepeatedKanasFromDB();
//
//                int remaining = challengeKanas.size() - toPractice.size();
//
//                if(repeated.size() > 0){
//                    for (int i = 0; i < remaining; i++) {
//                        KanaProgress kanaProgress = repeated.get(rand.nextInt(repeated.size()));
//                        challengeKanas.add(kanaProgress);
//                    }
//                }
//                else {
//                    ArrayList<KanaProgress> all = readKanas();
//                    for (int i = 0; i < remaining; i++) {
//                        KanaProgress kanaProgress = all.get(rand.nextInt(all.size()));
//                        challengeKanas.add(kanaProgress);
//                    }
//                }
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(challengeKanas);
//        return challengeKanas;
//    }
}
