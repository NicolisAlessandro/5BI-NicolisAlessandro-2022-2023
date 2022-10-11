package nicolis_A_ETL_01.bin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ETL_DB {
    public static void viewTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM logs-Nicolis");

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
                "CREATE TABLE logs_Nicolis ("
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

    public static int insertTab01(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(
                "CREATE TABLE logs_Nicolis ("
                        + "  `data` varchar(30) UNSIGNED NOT NULL primary key,"
                        + "  `ora` varchar(30) NOT NULL,"
                        + "  `nome` varchar(20) NOT NULL,"
                        + "  `utente` varchar(20) NOT NULL,"
                        + "  `contestoEvento` varchar(30) DEFAULT NULL,"
                        + "  `componente` varchar(30) DEFAULT NULL"
                        + "  `Evento` varchar(30) DEFAULT NULL"
                        + "  `descrizione` varchar(30) DEFAULT NULL"
                        + "  `origine` varchar(30) DEFAULT NULL"
                        + "  `indirizzoIP` varchar(30) DEFAULT NULL"
                        + ")"
                );
    }

    public static int modifyTable(Connection connection, String data, String ora, String nome, String utente,
                                  String contEv, String comp, String ev, String descr, String orig, String indIP )
                                throws SQLException {
        String updateSQL = "update logs_Nicolis set classe=?,materia=?, aula=?, giorno=?, ora=? where id=?";
        PreparedStatement statement = connection.prepareStatement(updateSQL);
            statement.setString(1, data);
            statement.setString(2, ora);
            statement.setString(3, nome);
            statement.setString(4, utente);
            statement.setString(5, contEv);
            statement.setString(6, comp);
            statement.setString(7, ev);
            statement.setString(8, descr);
            statement.setString(9, orig);
            statement.setString(10, indIP);

        return statement.executeUpdate();
    }

    public static void csvInserisci(Connection connection) throws IOException, SQLException {
        String file = "file/logs_5BI 202223_20221004-1028.csv";
        BufferedReader lineReader=new BufferedReader(new FileReader(file));

        String lineText;

        lineReader.readLine();
        while ((lineText=lineReader.readLine())!=null) {
            String[] data = lineText.split(",");

            String datA = data[0];
            String ora = data[1];
            String nome = data[2];
            String utente = data[3];
            String contEv = data[4];
            String comp = data[5];
            String ev = data[6];
            String descr = data[7];
            String orig = data[8];
            String indIP = data[9];

            String sql = "insert into employee( data, ora,  nome, utente, contEv,  comp,  ev,  descr,  orig,  indIP)" +
                    " values(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, datA);
            statement.setString(2, ora);
            statement.setString(3, nome);
            statement.setString(4, utente);
            statement.setString(5, contEv);
            statement.setString(6, comp);
            statement.setString(7, ev);
            statement.setString(8, descr);
            statement.setString(9, orig);
            statement.setString(10, indIP);
            statement.addBatch();
        }
    }
    public static void dropTable(Connection connection, String table) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE " + table + ";");
    }

    public static void dropColumn(Connection connection, String col) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP COLUMN " + col + ";");
    }
}
