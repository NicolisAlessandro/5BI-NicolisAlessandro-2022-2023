package nicolis_A_JDBC_01.bin;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Jdbc ex 02.
 */
public class JDBC_01{
        public static void viewTable(Connection connection) throws SQLException {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM docenti-Nicolis");

                while (resultSet.next()) {
                        System.out.println(resultSet.getInt(1)
                                + "  " + resultSet.getString(2)
                                + "  " + resultSet.getString(3)
                                + "  " + resultSet.getString(4)
                                + "  " + resultSet.getString(5)
                                + "  " + resultSet.getString(6)
                                + "  " + resultSet.getString(7)
                                + "  " + resultSet.getString(8)
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

        public static int createTable(Connection connection) throws SQLException {
                Statement statement = connection.createStatement();
                return statement.executeUpdate("CREATE TABLE `amici` ("
                        + "  `id` bigint(20) UNSIGNED NOT NULL primary key,"
                        + "  `classe` varchar(30) DEFAULT NULL,"
                        + "  `cognome` varchar(20) NOT NULL"
                        + "  `giorno` varchar(30) DEFAULT NULL,"
                        + "  `ora` varchar(30) DEFAULT NULL,"
                        + "  `6` varchar(30) DEFAULT NULL,"
                        + "  `7` varchar(30) DEFAULT NULL,"
                        + "  `8` varchar(30) DEFAULT NULL,"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1");
        }

        public static int insertTab01(Connection connection, String name, String surname) throws SQLException {
                Statement statement = connection.createStatement();

                return statement.executeUpdate(
                        "insert into amici (nome,cognome) values('"
                                + name + "','"
                                + surname + "')"
                );
        }

        public static int insertIntoTable(Connection connection, String name, String surname) throws SQLException {
                String insertTableSQL = "INSERT INTO amici (nome,cognome)"
                        + "values (?,?)";
                PreparedStatement statement = connection.prepareStatement(insertTableSQL);
                statement.setString(1, name);
                statement.setString(2, surname);

                return statement.executeUpdate();
        }

        public static int modifyTable(Connection connection, int id, String classe, String materie, String aula, int giorno, int ora) throws SQLException {
                String updateSQL = "update amici set nome=?,cognome=? where id=?";
                PreparedStatement statement = connection.prepareStatement(updateSQL);
                statement.setString(1, classe);
                statement.setString(2, materie);
                statement.setString(3, aula);
                statement.setInt(0, id);
                statement.setInt(4, giorno);
                statement.setInt(5, ora);


                return statement.executeUpdate();
        }

        public static String[] resultSetToVector(ResultSet resultSet) throws SQLException {
                String[] strings = new String[resultSet.getMetaData().getColumnCount()];
                for (int i = 0; i < strings.length; i++)
                        strings[i] = resultSet.getString(i);
                return strings;
        }

        public static ArrayList<String[]> csvInserisci(){
                try (CSVReader reader = new CSVReader(new FileReader("file.csv"))) {
                        ArrayList<String[]> r = reader.readAll();
                        r.forEach(x -> System.out.println(Arrays.toString(x)));
                }
                return r;
        }

        public static void main(String[] arg) {
                Scanner scanner = new Scanner(System.in);
                Connection connection;
                try {
                        connection = DriverManager.getConnection(
                                "jdbc:mysql://172.16.1.99/db99999?user=ut99999&password=pw99999");

                        boolean cont = true;
                        while (cont) {
                                if (existTable(connection, "docenti-Nicolis")) {
                                        System.out.println("Tabella esistente");
                                } else {
                                        System.out.println("Tabella inesistente");
                                }

                                System.out.print("0 Uscita\n1 Crea Tabella\n2 visualizza\n3 Aggiungi\n4 Mod Tabella\n5 Csv");
                                String line = scanner.nextLine();

                                int result, id, giorno, ora;
                                String classe, materia, aula;
                                ArrayList<String[]> list;

                                switch (line.charAt(0)) {
                                        case '0' -> cont = false;
                                        case '2' -> viewTable(connection);
                                        case '5' -> list = csvInserisci();
                                        String[] s = new String[list.size()];

                                        case '3' -> {
                                                System.out.print("Nome -->");
                                                classe = scanner.nextLine();
                                                System.out.print("Cognome -->");
                                                materia = scanner.nextLine();
                                                result = insertIntoTable(connection, classe, materia);
                                                System.out.println("risultato " + result);
                                        }
                                        case '4' -> {
                                                System.out.print("Id -->");
                                                id = Integer.parseInt(scanner.nextLine());
                                                System.out.print("Classe -->");
                                                classe = scanner.nextLine();
                                                System.out.print("Materia -->");
                                                materia = scanner.nextLine();
                                                System.out.print("l'aula -->");
                                                aula = scanner.nextLine();
                                                System.out.print("il giorno -->");
                                                giorno = Integer.parseInt(scanner.nextLine());
                                                System.out.print("l'ora -->");
                                                ora = Integer.parseInt(scanner.nextLine());
                                                result = modifyTable(connection, id, classe, materia, aula, giorno, ora );
                                                System.out.println("risultato " + result);
                                        }
                                        case '1' -> createTable(connection);
                                }
                        }
                } catch (SQLException ex) {
                        // handle any errors
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
                }
        }
}