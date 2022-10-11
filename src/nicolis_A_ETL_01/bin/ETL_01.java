package nicolis_A_ETL_01.bin;

import static nicolis_A_ETL_01.bin.ETL_DB.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

class ETL_01 {

        public static void main(String[] arg) throws SQLException, IOException {
                Scanner scanner = new Scanner(System.in);
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://172.16.1.99/db99999?user=ut99999&password=pw99999");

                System.out.println("connection = " + connection);

                boolean cont = true;
                while (cont) {
                        if (existTable(connection, "logs-Nicolis")) {
                                System.out.println("Tabella esistente");
                        } else {
                                System.out.println("Tabella inesistente");
                        }

                        System.out.print("0 Uscita\n1 Crea Tabella\n2 visualizza\n3 Aggiungi\n4 Mod Tabella\n5 Csv\n");
                        String line = scanner.nextLine();

                        int result;
                        String data, ora, nome, utente, contEv, comp, ev, descr, orig, indIP;

                        switch (line.charAt(0)) {
                                case '0' -> cont = false;
                                case '2' -> viewTable(connection);
                                case '5' -> csvInserisci(connection);
                                case '3' -> {
                                        result = insertTab01(connection);
                                        System.out.println("risultato " + result);
                                }
                                case '4' -> {
                                        System.out.print("dati da inserire -->");
                                        data = scanner.nextLine();
                                        ora = scanner.nextLine();
                                        nome = scanner.nextLine();
                                        utente = scanner.nextLine();
                                        contEv = scanner.nextLine();
                                        comp = scanner.nextLine();
                                        ev = scanner.nextLine();
                                        descr = scanner.nextLine();
                                        orig = scanner.nextLine();
                                        indIP = scanner.nextLine();

                                        result = modifyTable(connection, data, ora, nome, utente,
                                                contEv,  comp,  ev,  descr,  orig,  indIP);
                                        System.out.println("risultato " + result);
                                }
                                case '1' -> createTable(connection);
                                case '6' -> {
                                        String table = scanner.nextLine();
                                        dropTable(connection, table);
                                }
                                case '7' -> {
                                        String col = scanner.nextLine();
                                        dropColumn(connection, col);
                                }
                        }
                }
        }
}