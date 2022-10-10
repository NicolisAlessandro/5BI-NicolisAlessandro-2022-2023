package nicolis_A_ETL_01.bin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import static java.lang.Integer.parseInt;

public class ETL_DB {
    public static void viewTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM logging-Nicolis");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1)
                    + "  " + resultSet.getString(2)
                    + "  " + resultSet.getString(3)
                    + "  " + resultSet.getString(4)
                    + "  " + resultSet.getString(5)
                    + "  " + resultSet.getString(6)
                    + "  " + resultSet.getString(7)
                    + "  " + resultSet.getString(8)
                    + "  " + resultSet.getString(9)
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
                "CREATE TABLE lezione_Nicolis ("
                + "  `data` bigint(20) UNSIGNED NOT NULL primary key,"
                + "  `ora` varchar(30) DEFAULT NULL,"
                + "  `nome` varchar(20) NOT NULL,"
                + "  `utente` varchar(20) NOT NULL,"
                + "  `contestoEvento` varchar(30) DEFAULT NULL,"
                + "  `componente` varchar(30) DEFAULT NULL"
                + "  `Evento` varchar(30) DEFAULT NULL"
                + "  `descrizione` varchar(30) DEFAULT NULL"
                + "  `origine` varchar(30) DEFAULT NULL"
                + "  `indirizzoIP` varchar(30) DEFAULT NULL"
                + ")");
    }

    public static int insertTab01(Connection connection, int id_prof, String cognome) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(
                "CREATE TABLE docente_Nicolis (" +
                        " `id_prof` bigint(20) UNSIGNED NOT NULL primary key," +
                        " `cognome` varchar(30) NOT NULL" +
                        ")"
        );
    }

    public static int modifyTable(Connection connection, int id, String classe, String materie, String aula, int giorno, int ora) throws SQLException {
        String updateSQL = "update lezione_Nicolis set classe=?,materia=?, aula=?, giorno=?, ora=? where id=?";
        PreparedStatement statement = connection.prepareStatement(updateSQL);
        statement.setInt(1, id);
        statement.setString(2, classe);
        statement.setString(3, materie);
        statement.setString(4, aula);
        statement.setInt(5, giorno);
        statement.setInt(6, ora);
        return statement.executeUpdate();
    }

    public static void csvInserisci(Connection connection) throws IOException, SQLException {
        String file = "file/logs_5BI 202223_20221004-1028.csv";
        BufferedReader lineReader=new BufferedReader(new FileReader(file));

        String lineText;

        lineReader.readLine();
        while ((lineText=lineReader.readLine())!=null) {
            String[] data = lineText.split(",");

            String id = data[0];
            String name = data[1];
            String address = data[2];
            String salary = data[3];

            String sql = "insert into employee(id,name,address,salary) values(?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, parseInt(id));
            statement.setString(2, name);
            statement.setString(3, address);
            statement.setInt(4, parseInt(salary));
            statement.addBatch();

        }
    }
}
