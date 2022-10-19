package nicolis_A_ETL_Alunni.bin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static nicolis_A_ETL_Alunni.bin.ETL_Alunni_DB.*;

class ETL_Alunni {
        public static void main(String[] args) throws SQLException, IOException{
                Scanner scanner = new Scanner(System.in);
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://172.16.1.99/db99999?user=ut99999&password=pw99999");

                System.out.println("connection = " + connection);
                boolean cont = true;
                while (cont) {
                        if (existTable(connection, "Alunno_Nicolis")) {
                                System.out.println("Tabella esistente");
                        } else {
                                System.out.println("Tabella inesistente");
                        }

                        System.out.print("0 Uscita\n");
                        String line = scanner.nextLine();
                        switch (line.charAt(0)) {
                                case '0' -> cont = false;
                                case '1' -> createTable(connection);
                                case '2' -> viewTable(connection);
                                case '3' -> {
                                        int result = insertTab01(connection);
                                        System.out.println("risultato " + result);
                                }
                                case '4' -> csvInserisci(connection);
                        }
                }
        }
}