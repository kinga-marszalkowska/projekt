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



    public static List<KanaProgress> getKanasFromDB() throws SQLException {
        List<KanaProgress> result = new ArrayList<>();
        String selectSQL = String.format("SELECT * FROM %s",TABLE_NAME);
        Connection connection = DriverManager.getConnection(CONN);

        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        try (connection; preparedStatement; resultSet) {
            while (resultSet.next()) {
                String kana = resultSet.getString(KANA_COLUMN);
                String romanji = resultSet.getString(KANA_COLUMN);
                String rep = resultSet.getString(KANA_COLUMN);
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
}
