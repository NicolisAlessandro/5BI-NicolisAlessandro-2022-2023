package nicolis_A_ETL_01.bin;

import static nicolis_A_ETL_01.bin.ETL_DB.*;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

class ETL_01 {

        public static void main(String[] arg) throws SQLException, IOException, CsvException {
                Scanner scanner = new Scanner(System.in);
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://172.16.1.99/db99999?user=ut99999&password=pw99999");

                System.out.println("connection = " + connection);

                boolean cont = true;
                while (cont) {
                        if (existTable(connection, "lezione-Nicolis")) {
                                System.out.println("Tabella esistente");
                        } else {
                                System.out.println("Tabella inesistente");
                        }

                        System.out.print("0 Uscita\n1 Crea Tabella\n2 visualizza\n3 Aggiungi\n4 Mod Tabella\n5 Csv\n");
                        String line = scanner.nextLine();

                        int result, id, giorno, ora;
                        String classe, materia, aula, cognome;

                        switch (line.charAt(0)) {
                                case '0' -> cont = false;
                                case '2' -> viewTable(connection);
                                case '5' -> csvInserisci(connection);
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