package nicolis_A_JDBC_01.bin;

import java.sql.*;

public class JDBC_DB {
    public static void viewTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM lezione-Nicolis");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1)
                    + "  " + resultSet.getString(2)
                    + "  " + resultSet.getString(3)
                    + "  " + resultSet.getString(4)
                    + "  " + resultSet.getString(5)
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
        statement.executeUpdate("CREATE TABLE `lezione_Nicolis` ("
                + "  `id` bigint(20) UNSIGNED NOT NULL primary key,"
                + "  `classe` varchar(30) DEFAULT NULL,"
                + "  `cognome` varchar(20) NOT NULL"
                + "  `giorno` varchar(30) DEFAULT NULL,"
                + "  `ora` varchar(30) DEFAULT NULL,"
                + ") ENGINE=InnoDB DEFAULT CHARSET=latin1");
    }

    public static int insertTab01(Connection connection, int id_prof, String cognome) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(
                "insert into docente_Nicolis (id_prof,cognome) values('"
                        + id_prof + "','"
                        + cognome + "')"
        );
    }

    public static int modifyTable(Connection connection, int id, String classe, String materie, String aula, int giorno, int ora) throws SQLException {
        String updateSQL = "update Orari_Nicolis set nome=?,cognome=? where id=?";
        PreparedStatement statement = connection.prepareStatement(updateSQL);
        statement.setInt(0, id);
        statement.setString(1, classe);
        statement.setString(2, materie);
        statement.setString(3, aula);
        statement.setInt(4, giorno);
        statement.setInt(5, ora);
        return statement.executeUpdate();
    }
}
