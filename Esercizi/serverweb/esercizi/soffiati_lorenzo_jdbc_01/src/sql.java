import java.sql.*;
import java.util.Scanner;

public class sql {

    public static void visualTab(Connection c) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = c.createStatement();
        rs = stmt.executeQuery("SELECT * FROM amici");

        while (rs.next()) {
            System.out.println(rs.getInt(1) +
                    "  " +
                    rs.getString(2) + "  " +
                    rs.getString("cognome"));
        }
        stmt.close();
    }

    public static int insertTab02(Connection c, String nom, String cog) throws SQLException {
        int r;
        String insertTableSQL =
                "INSERT INTO amici (nome,cognome) values (?,?)";
        PreparedStatement ps = c.prepareStatement(insertTableSQL);
        ps.setString(1, nom);
        ps.setString(2, cog);
        r = ps.executeUpdate();

        return r;
    }

    public static int update(Connection c, String id, String desc, String val) throws SQLException {
        int r;
        String updateS = "UPDATE amici SET ? = ? WHERE id=?";

        PreparedStatement ps = c.prepareStatement(updateS);
        ps.setString(1, desc);
        ps.setString(2, val);
        ps.setString(3, id);
        r = ps.executeUpdate();

        return r;
    }

    public static int delet(Connection c, String id) throws SQLException {
        int r;
        String deletsS = "DELET FROM amici WHERE id=?";

        PreparedStatement ps = c.prepareStatement(deletsS);
        ps.setString(1, id);
        r = ps.executeUpdate();

        return r;
    }



    public static void main (String... arg ) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:mysql://172.16.1.99/db99999?user=ut99999&password=pw99999");
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        //--------------------------------- Menu ------------------------
        System.out.println("[1] visualizza");
        System.out.println("[2] aggiungi");
        System.out.println("[3] modifica");
        System.out.println("[4] elimina");
        System.out.println("[5] esci");

        System.out.print("inserire il comando da eseguire: ");
        String opzione  = sc.nextLine();

        //------------------ operazione ------------------------------
        while(run) {
            switch (opzione) {
                case "1":
                    System.out.println("\n\n");
                    visualTab(con);
                    break;

                case "2":
                    System.out.print("inserire il nome: ");
                    String nome = sc.nextLine();
                    System.out.print("inserire il cognome: ");
                    String cognome = sc.nextLine();
                    insertTab02(con, nome, cognome);
                    break;

                case "3":
                    System.out.println("[1] Nome");
                    System.out.println("[2] cognome");
                    System.out.print("inserire il campo ceh si vuole modificare: ");
                    String ris = sc.nextLine().trim();
                    String desc = (ris.equals("1")) ? "nome" : (ris.equals("2")) ? "cognome" : "errore";
                    System.out.print("inserirre il campo modificato: ");
                    String val = sc.nextLine();
                    System.out.print("inserire l'ID: ");
                    String id = sc.nextLine();
                    update(con, id, desc, val);
                    break;

                case "4":
                    System.out.print("inserire id dell'utente da eliminare: ");
                    String idTemp = sc.nextLine();
                    delet(con, idTemp);
                    break;

                default:
                    run = false;
            }
        }
    }
}
