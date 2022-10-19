package nicolis_A_ETL_Alunni.bin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ETL_Alunni_DB {
    public static void viewTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Alunno_Nicolis");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1)
                    + "  " + resultSet.getString(2)
                    + "  " + resultSet.getString(3)
                    + "  " + resultSet.getString(4)
                    + "  " + resultSet.getString(5)
                    + "  " + resultSet.getString(6)
                    + "  " + resultSet.getDouble(7)
                    + "  " + resultSet.getInt(8)
                    + "  " + resultSet.getString(9)
                    + "  " + resultSet.getInt(10)
                    + "  " + resultSet.getInt(11)
                    + "  " + resultSet.getInt(12)
                    + "  " + resultSet.getInt(13)
            );
        }
        statement.close();
    }

    public static boolean existTable(Connection connection, String table) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, "%", null);
        while (resultSet.next()) {
            if (table.equalsIgnoreCase(resultSet.getString(3))) {
                return true;
            }
        }
        return false;
    }

    public static void createTable(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "CREATE TABLE Alunno_Nicolis ("
                        + "  `matricola` bigint(20) UNSIGNED NOT NULL primary key,"
                        + "  `cognome` varchar(30) DEFAULT NULL,"
                        + "  `nome` varchar(20) NOT NULL,"
                        + "  `residenza` varchar(20) NOT NULL,"
                        + "  `classe` varchar(30) DEFAULT NULL,"
                        + "  `cittadinanza` varchar(30) DEFAULT NULL,"
                        + "  `votoMedio` double(30) DEFAULT NULL,"
                        + "  `votoCondotta` bigint(30) DEFAULT NULL,"
                        + "  `dataNasc` varchar(30) DEFAULT NULL,"
                        + "  `cap` bigint(20) DEFAULT NULL,"
                        + "  `votoIT` bigint(20) DEFAULT NULL,"
                        + "  `votoMat` bigint(20) DEFAULT NULL,"
                        + "  `votoEng` bigint(20) DEFAULT NULL"
                        + ")"
        );
    }

    public static int insertTab01(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(
                "CREATE TABLE Alunno_Nicolis ("
                        + "  `matricola` bigint(20) UNSIGNED NOT NULL primary key,"
                        + "  `cognome` varchar(30) DEFAULT NULL,"
                        + "  `nome` varchar(20) NOT NULL,"
                        + "  `residenza` varchar(20) NOT NULL,"
                        + "  `classe` varchar(30) DEFAULT NULL,"
                        + "  `cittadinanza` varchar(30) DEFAULT NULL,"
                        + "  `votoMedio` double(30) DEFAULT NULL,"
                        + "  `votoCondotta` bigint(30) DEFAULT NULL,"
                        + "  `dataNasc` varchar(30) DEFAULT NULL,"
                        + "  `cap` bigint(20) DEFAULT NULL,"
                        + "  `votoIT` bigint(20) DEFAULT NULL,"
                        + "  `votoMat` bigint(20) DEFAULT NULL,"
                        + "  `votoEng` bigint(20) DEFAULT NULL"
                        + ")"
        );
    }

    public static void csvInserisci(Connection connection) throws IOException, SQLException {
        String file = "file/export_alunni.csv";
        BufferedReader lineReader = new BufferedReader(new FileReader(file));

        String lineText;

        lineReader.readLine();
        while ((lineText = lineReader.readLine()) != null) {
            String[] data = lineText.split(";");

            String matr = data[0];
            String cogn = data[1];
            String nome = data[2];
            String resid = data[3];
            String classe = data[4];
            String cittad = data[5];
            String votM = data[6];
            String votC = data[7];
            String dataNas = data[8];
            String cap = data[9];
            String votIt = data[10];
            String votMat = data[11];
            String votEng = data[12];

            String sql = "insert into employee( matr, cogn,  nome, res, clas, citt,  votM,  votC,  dataNas,  cap, votIt, votMat, votEng)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(matr));
            statement.setString(2, cogn);
            statement.setString(3, nome);
            statement.setString(4, resid);
            statement.setString(5, classe);
            statement.setString(6, cittad);
            statement.setDouble(7, Double.parseDouble(votM));
            statement.setInt(8, Integer.parseInt(votC));
            statement.setString(9, dataNas);
            statement.setInt(10, Integer.parseInt(cap));
            statement.setInt(11, Integer.parseInt(votIt));
            statement.setInt(12, Integer.parseInt(votMat));
            statement.setInt(13, Integer.parseInt(votEng));
            statement.addBatch();
        }
    }
}
