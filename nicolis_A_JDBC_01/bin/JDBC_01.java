package nicolis_A_JDBC_01.bin;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JDBC_01 {
    public static void viewTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM docente-Nicolis");

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

    public static int insertTab01(Connection connection, int id, String surname) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(
                "insert into amici (id,cognome) values('"
                        + id + "','"
                        + surname + "')"
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

    public static String[] resultSetToVector(ResultSet resultSet) throws SQLException {
        String[] strings = new String[resultSet.getMetaData().getColumnCount()];
        for (int i = 0; i < strings.length; i++)
            strings[i] = resultSet.getString(i);
        return strings;
    }

    public static List<String[]> csvInserisci() throws IOException, CsvException {
        String file = "C:\\Users\\Utente\\Documents\\Docente_Nicolis.csv";
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
            return r;
        }
    }

    public static void main(String[] arg) throws SQLException, IOException, CsvException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://172.16.1.99/db99999?user=ut99999&password=pw99999");

        boolean cont = true;
        while (cont) {
            if (existTable(connection, "docenti-Nicolis")) {
                System.out.println("Tabella esistente");
            } else {
                System.out.println("Tabella inesistente");
            }

            System.out.print("0 Uscita\n1 Crea Tabella\n2 visualizza\n3 Aggiungi\n4 Mod Tabella\n5 Csv\n");
            String line = scanner.nextLine();

            int result, id, giorno, ora;
            String classe, materia, aula, cognome;
            List<String[]> l;

            switch (line.charAt(0)) {
                case '0' -> cont = false;
                case '2' -> viewTable(connection);
                case '5' -> {
                    l= csvInserisci();
                    System.out.println("l = " + l);
                }
                case '3' -> {
                    System.out.print("id -->");
                    id = scanner.nextInt();
                    System.out.print("Cognome -->");
                    cognome = scanner.nextLine();
                    result = insertTab01(connection, id, cognome);
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
                    result = modifyTable(connection, id, classe, materia, aula, giorno, ora);
                    System.out.println("risultato " + result);
                }
                case '1' -> createTable(connection);
            }
        }

    }
}