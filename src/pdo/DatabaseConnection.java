package pdo;

import models.KanaProgress;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String CONN = "jdbc:sqlite:D:\\PJATK\\POJ\\lab11-copy\\src\\japaneseKana.db";
    private static final String TABLE_NAME = "kana";
    private static final String KANA_COLUMN = "kana";
    private static final String ROMANJI_COLUMN = "romanji";
    private static final String REPETITIONS_COUNT_COLUMN = "repetitionsCount";
    private static final String DONT_KNOW_COUNT_COLUMN = "dontKnowCount";
    private static final String PRACTICE_COUNT_COLUMN = "practiceCount";
    private static final String MASTERED_COUNT_COLUMN = "masteredCount";

    public static ArrayList<KanaProgress> getKanasFromDB() throws SQLException {
        ArrayList<KanaProgress> result = new ArrayList<>();
        String selectSQL = String.format("SELECT * FROM %s",TABLE_NAME);
        Connection connection = DriverManager.getConnection(CONN);

        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        try (connection; preparedStatement; resultSet) {
            while (resultSet.next()) {
                KanaProgress kanaProgress = new KanaProgress(
                        resultSet.getString(KANA_COLUMN),
                        resultSet.getString(ROMANJI_COLUMN),
                        resultSet.getInt(REPETITIONS_COUNT_COLUMN),
                        resultSet.getInt(DONT_KNOW_COUNT_COLUMN),
                        resultSet.getInt(PRACTICE_COUNT_COLUMN),
                        resultSet.getInt(MASTERED_COUNT_COLUMN)
                        );
                result.add(kanaProgress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

        public static ArrayList<KanaProgress> getKanasForChallengeFromDB() throws SQLException {
        ArrayList<KanaProgress> result = new ArrayList<>();
        String selectSQL = String.format("SELECT * FROM %s WHERE %s < %s and %s < %d",
                TABLE_NAME, MASTERED_COUNT_COLUMN, PRACTICE_COUNT_COLUMN, MASTERED_COUNT_COLUMN, KanaProgress.getMasteryCount());
            System.out.println(selectSQL);
        Connection connection = DriverManager.getConnection(CONN);

        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        try (connection; preparedStatement; resultSet) {
            while (resultSet.next()) {
                KanaProgress kanaProgress = new KanaProgress(
                        resultSet.getString(KANA_COLUMN),
                        resultSet.getString(ROMANJI_COLUMN),
                        resultSet.getInt(REPETITIONS_COUNT_COLUMN),
                        resultSet.getInt(DONT_KNOW_COUNT_COLUMN),
                        resultSet.getInt(PRACTICE_COUNT_COLUMN),
                        resultSet.getInt(MASTERED_COUNT_COLUMN)
                );
                result.add(kanaProgress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static ArrayList<KanaProgress> getKanasForTrainingFromDB() throws SQLException {
        ArrayList<KanaProgress> result = new ArrayList<>();
        String selectSQL = String.format("SELECT * FROM %s WHERE %s = (SELECT MIN(%s) FROM %s) ORDER BY %s DESC;",
                TABLE_NAME, MASTERED_COUNT_COLUMN,
                MASTERED_COUNT_COLUMN, TABLE_NAME,
                DONT_KNOW_COUNT_COLUMN);
        Connection connection = DriverManager.getConnection(CONN);

        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        try (connection; preparedStatement; resultSet) {
            while (resultSet.next()) {
                KanaProgress kanaProgress = new KanaProgress(
                        resultSet.getString(KANA_COLUMN),
                        resultSet.getString(ROMANJI_COLUMN),
                        resultSet.getInt(REPETITIONS_COUNT_COLUMN),
                        resultSet.getInt(DONT_KNOW_COUNT_COLUMN),
                        resultSet.getInt(PRACTICE_COUNT_COLUMN),
                        resultSet.getInt(MASTERED_COUNT_COLUMN)
                );
                result.add(kanaProgress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }



//    public static ArrayList<KanaProgress> getKanasToPracticeFromDB() throws SQLException {
//        ArrayList<KanaProgress> result = new ArrayList<>();
//        String selectSQL = String.format("SELECT * FROM %s WHERE %s > 0",TABLE_NAME, PRACTICE_COUNT_COLUMN);
//        Connection connection = DriverManager.getConnection(CONN);
//
//        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        try (connection; preparedStatement; resultSet) {
//            while (resultSet.next()) {
//                KanaProgress kanaProgress = new KanaProgress(
//                        resultSet.getString(KANA_COLUMN),
//                        resultSet.getString(ROMANJI_COLUMN),
//                        resultSet.getInt(REPETITIONS_COUNT_COLUMN),
//                        resultSet.getInt(DONT_KNOW_COUNT_COLUMN),
//                        resultSet.getInt(PRACTICE_COUNT_COLUMN),
//                        resultSet.getInt(MASTERED_COUNT_COLUMN)
//                );
//                result.add(kanaProgress);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//
//    }
//
//    public static ArrayList<KanaProgress> getKanasDontKnowFromDB() throws SQLException {
//        ArrayList<KanaProgress> result = new ArrayList<>();
//        String selectSQL = String.format("SELECT * FROM %s WHERE %s > 0",TABLE_NAME, DONT_KNOW_COUNT_COLUMN);
//        Connection connection = DriverManager.getConnection(CONN);
//
//        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        try (connection; preparedStatement; resultSet) {
//            while (resultSet.next()) {
//                KanaProgress kanaProgress = new KanaProgress(
//                        resultSet.getString(KANA_COLUMN),
//                        resultSet.getString(ROMANJI_COLUMN),
//                        resultSet.getInt(REPETITIONS_COUNT_COLUMN),
//                        resultSet.getInt(DONT_KNOW_COUNT_COLUMN),
//                        resultSet.getInt(PRACTICE_COUNT_COLUMN),
//                        resultSet.getInt(MASTERED_COUNT_COLUMN)
//                );
//                result.add(kanaProgress);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//
//    }
//
//    public static ArrayList<KanaProgress> getRepeatedKanasFromDB() throws SQLException {
//        ArrayList<KanaProgress> result = new ArrayList<>();
//        String selectSQL = String.format("SELECT * FROM %s WHERE %s > 0",TABLE_NAME, REPETITIONS_COUNT_COLUMN);
//        Connection connection = DriverManager.getConnection(CONN);
//
//        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        try (connection; preparedStatement; resultSet) {
//            while (resultSet.next()) {
//                KanaProgress kanaProgress = new KanaProgress(
//                        resultSet.getString(KANA_COLUMN),
//                        resultSet.getString(ROMANJI_COLUMN),
//                        resultSet.getInt(REPETITIONS_COUNT_COLUMN),
//                        resultSet.getInt(DONT_KNOW_COUNT_COLUMN),
//                        resultSet.getInt(PRACTICE_COUNT_COLUMN),
//                        resultSet.getInt(MASTERED_COUNT_COLUMN)
//                );
//                result.add(kanaProgress);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//
//    }

    public static void saveKanaToDB(KanaProgress kanaProgress) throws SQLException {
        String insertSQL = String.format("INSERT INTO %s VALUES (?, ?, ?, ?, ?, ?)", TABLE_NAME);
        Connection connection = DriverManager.getConnection(CONN);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        try (connection; preparedStatement) {
            preparedStatement.setString(1, kanaProgress.getMora());
            preparedStatement.setString(2, kanaProgress.getRomanji());
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 0);
            preparedStatement.setInt(6, 0);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateKanaToDB(KanaProgress kanaProgress) throws SQLException{
        String updateSQL = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?  WHERE %s = ? and  %s = ?",
                TABLE_NAME,
                REPETITIONS_COUNT_COLUMN, DONT_KNOW_COUNT_COLUMN, PRACTICE_COUNT_COLUMN, MASTERED_COUNT_COLUMN,
                KANA_COLUMN, ROMANJI_COLUMN);

        Connection connection = DriverManager.getConnection(CONN);
        try (connection) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, kanaProgress.getRepetitionsCount());
            preparedStatement.setInt(2, kanaProgress.getDontKnowCount());
            preparedStatement.setInt(3, kanaProgress.getPracticeCount());
            preparedStatement.setInt(4, kanaProgress.getMasteredCount());
            preparedStatement.setString(5, kanaProgress.getMora());
            preparedStatement.setString(6, kanaProgress.getRomanji());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
