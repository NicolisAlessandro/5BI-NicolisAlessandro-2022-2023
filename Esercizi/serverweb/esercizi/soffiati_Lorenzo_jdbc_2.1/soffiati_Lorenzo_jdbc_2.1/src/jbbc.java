import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;

public class jbbc{

    public static void visualTab(Connection c, String tabella) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = c.createStatement();
        rs = stmt.executeQuery("SELECT * FROM " + tabella);

        while (rs.next()) {
            System.out.println(rs.getInt(1) +
                    "  " +
                    rs.getString(2)+ "  " +
                    rs.getString(3)+ "  " +
                    rs.getString(4)+ "  " +
                    rs.getString(5)+ "  " +
                    rs.getString(6));
        }
        stmt.close();
    }

    public static int insert(Connection c, String tabella, String autore, String titolo, int durata) throws SQLException {

        int r;

        String insertTableSQL = "INSERT INTO " + tabella + "(autore,titolo,datains,numascolti,durata) values (?,?,?,0,?)";

        PreparedStatement ps = c.prepareStatement(insertTableSQL);
        ps.setString(1, autore);
        ps.setString(2, titolo);
        ps.setDate(3,  new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        ps.setInt(4, durata);


        r = ps.executeUpdate();
        return r;
    }

    public static int update(Connection c, String tabella, String id) throws SQLException {
        int r;

        //  Recupera il numero di ascolti
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT numascolti FROM " + tabella + " WHERE id=" + id);
        rs.next();
        int val = rs.getInt(1);
        val++;

        // Update
        String updateS = "UPDATE " + tabella + " SET numascolti = ? WHERE id=?";

        PreparedStatement ps = c.prepareStatement(updateS);

        ps.setInt(1, val);
        ps.setString(2, id);
        r = ps.executeUpdate();

        return r;
    }

    public static int delet(Connection c, String tabella, String id) throws SQLException {
        int r;
        String deletsS = "DELETE FROM " + tabella + " WHERE id=?";

        PreparedStatement ps = c.prepareStatement(deletsS);
        ps.setString(1, id);
        r = ps.executeUpdate();

        return r;
    }

    public static void createTabel(Connection c, String tabella) throws SQLException {
        String comando = "CREATE TABLE IF NOT EXISTS " + tabella + " (" +
                "id integer auto_increment primary key," +
                "autore varchar(30)," +
                "titolo varchar(30)," +
                "datains date," +
                "numascolti integer," +
                "durata integer )";

        Statement stmt = c.createStatement();
        stmt.executeUpdate(comando);
    }

    public static void visualizzaGenerico(Connection c, String tabella, String colonna, String condizione) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = c.createStatement();
        rs = stmt.executeQuery("SELECT " + colonna + " FROM " + tabella + " where " + condizione);

        while (rs.next()) {
            System.out.println(rs.getInt(1) +
                    "  " +
                    rs.getString(2)+ "  " +
                    rs.getString(3)+ "  " +
                    rs.getString(4)+ "  " +
                    rs.getString(5)+ "  " +
                    rs.getString(6));
        }
        stmt.close();
    }

    public static void main(String... arg) throws SQLException {

        String tabella = null;
        String opzione;
        boolean run = true;

        Connection con = DriverManager.getConnection("jdbc:mysql://172.16.1.99/db99999?user=ut99999&password=pw99999");
        Scanner sc = new Scanner(System.in);

        String id;
        String operazione;

        System.out.print("inserire il nome della tabella: ");
        tabella = sc.nextLine();
        createTabel(con, tabella);


        while(run){
            // Menu
            System.out.println("[1] visualizza");
            System.out.println("[2] aggiungi");
            System.out.println("[3] incrementa");
            System.out.println("[4] elimina");
            System.out.println("[5] visulizza per ascolti");
            System.out.println("[6] visualizza per cantante");
            System.out.println("[7] visulizza canzoni mai ascoltate");
            System.out.println("[0] esci");

            System.out.print("inserire il comando da eseguire: ");
            opzione  = sc.nextLine();

            // Operazioni
            switch (opzione) {
                case "1":
                    System.out.println("\n\n");
                    visualTab(con, tabella);
                    break;

                case "2":
                    System.out.println();
                    System.out.print("inserire l'autore: ");
                    String autore = sc.nextLine();
                    System.out.print("inserire il titolo: ");
                    String titolo = sc.nextLine();
                    System.out.print("inserire la durata: ");
                    int durata = sc.nextInt();
                    insert(con, tabella, autore, titolo, durata);
                    break;

                case "3":
                    System.out.println();
                    System.out.print("inserire l'id: ");
                    id = sc.nextLine();
                    update(con, tabella, id);
                    break;

                case "4":
                    System.out.println();
                    System.out.print("inserire l'id: ");
                    id = sc.nextLine();
                    delet(con, tabella, id);
                    break;

                case "5":
                    // Visualizza per ascolti
                    System.out.println();
                    System.out.print("quanti ascolti minimi deve avere la canzone: ");
                    operazione = "numascolti >= " + sc.nextLine().trim().strip();
                    System.out.println("\n");
                    visualizzaGenerico(con, tabella, "*" , operazione);
                    break;

                case "6":
                    // Visualizza per cantante
                    System.out.print("nome cantante: ");
                    operazione = "autore = '" + sc.nextLine().trim().strip() + "'";
                    System.out.println("\n");
                    visualizzaGenerico(con, tabella, "*" , operazione);
                    break;

                case "7":
                    // Visualizza per ascolti=0
                    System.out.println();
                    operazione = "numascolti = 0 ";
                    System.out.println("\n");
                    visualizzaGenerico(con, tabella, "*" , operazione);
                    break;

                case "0":
                    run = false;
                    break;

                default:
                    System.out.println("comando non valido");
            }

            System.out.println("\n");
        }
    }
}
