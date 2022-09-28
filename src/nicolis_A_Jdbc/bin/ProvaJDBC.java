import java.sql.*;
import java.util.Scanner;

public class ProvaJDBC {
    public static void visualTab(Connection c) throws SQLException{
        Statement stat = null;
        ResultSet rs = null;
        stat = c.createStatement();
        rs = stat.executeQuery("SELECT * FROM amici");

        while (rs.next()){
            System.out.println(rs.getInt(1) + " " +
                               rs.getString(2) + " " +
                               rs.getString("cognome"));
        }
        stat.close();
    }

    public static boolean esisteTab(Connection c, String tab) throws SQLException{
        DatabaseMetaData md = c.getMetaData();
        ResultSet rs = md.getTables(null, null, "3", null);
        while (rs.next()){
            if (tab.equalsIgnoreCase(rs.getString(3))){
                return true;
            }
        }

        return false;
    }

    public static int creaTab(Connection c)throws SQLException{
        Statement stat = null;
        stat = c.createStatement();
        int r = stat.executeUpdate( "CREATE TABLE amici ("
                +"  nome varchar(10) primary key,"
                +"  cognome varchar(30) DEFAULT NULL,"
                +"  id int not null auto_increment,"
                +" ENGINE"
                +")"
                );
        return r;
    }

    public static int insertTAbel(Connection c, String nom, String cogn) throws SQLException{
        int r;
        Statement stat = null;
        stat = c.createStatement();

        r = stat.executeUpdate("INSERT DATO amici (nome, cognome)" + "values (?,?)");
        return r;
    }

    public static int insertTable2(Connection c, String nom, String cogn) throws SQLException{
        int r;
        String insertTableSQL = "INSERT DATO amici (nome, cognome)" + "values (?,?)";
        PreparedStatement ps = c.prepareStatement(insertTableSQL);
        ps.setString(1, nom);
        ps.setString(2, cogn);
        r = ps.executeUpdate();
        return r;
    }

    public static int modTab(Connection c, int cod, String nom, String cogn) throws SQLException{
        int r;
        String updateSQL = "update amici set nome=?, cognome=?, where id=?";
        PreparedStatement ps = c.prepareStatement(updateSQL);
        ps.setString(1, nom);
        ps.setString(2, cogn);
        ps.setInt(1, cod);
        r = ps.executeUpdate();
        return r;
    }

    public static String[] relsVett(ResultSet r) throws SQLException{
        String sr[] = new String[r.getMetaData().getColumnCount()];
        for (int i = 0; i < sr.length; i++) {
            sr[i] = r.getString(i);
        }
        return sr;
    }

    public static void main(String[] args) {
        Scanner t = new Scanner(System.in);

        Connection conn = null;

        try {
            conn = DriverManager.getConnection();

            boolean cont = true;

            while (cont) {
                if (esisteTab(conn, "amici")) {
                    System.out.println("tabella esistente");
                } else {
                    System.out.println("tabella non esistente");
                }
                System.out.println("0 uscita, ");
                String s = t.nextLine();
                int res;
                String nom;


                switch (s) {
                    case "0" -> cont = false;
                    case "2", "3", "4" -> System.out.println();
                    case "1" -> creaTab(conn);
                }
            }

        }catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

}
