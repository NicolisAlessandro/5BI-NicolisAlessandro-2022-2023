package nicolis_A_Jdbc.tablestudent.bin;


import java.sql.*;
import java.util.Scanner;

public class JdbcEx02 {

	public static void visualTab(Connection c) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM docenti-Nicolis");

		while (rs.next()) {
			System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString("cognome"));
		}
		stmt.close();
	}


	public static boolean esiste(Connection c, String tab) throws SQLException {
		DatabaseMetaData md = c.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		while (rs.next()) {
			if (tab.equalsIgnoreCase(rs.getString(6))) {
				return true;
			}
		}

		return false;
	}

	public static int creaTab(Connection c) throws SQLException {
		Statement stmt = null;
		stmt = c.createStatement();
		int r = stmt.executeUpdate("CREATE TABLE `Docenti-Nicolis` ("
				+ "  `id` bigint(20) UNSIGNED NOT NULL auto_increment primary key,"
				+ "  `nome` varchar(30) DEFAULT NULL,"
				+ "  `cognome` varchar(20) NOT NULL"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1");
		return r;
	}

	public static int insertTab01(Connection c, String nom, String cog) throws SQLException {
		int r;
		Statement stmt = null;
		stmt = c.createStatement();

		r = stmt.executeUpdate(
				"insert into amici (nome,cognome) values('"
						+ nom + "','"
						+ cog + "')");

        /*
                String insertTableSQL = "INSERT INTO amici (nome,cognome)"
                        + "values (?,?)";
        PreparedStatement ps = conn.prepareStatement(insertTableSQL);
                ps.setString(1, nom);
                ps.setString(2, cog);
                res = ps.executeUpdate();
         */
		return r;
	}
	public static int insertTab02(Connection c, String nom, String cog) throws SQLException {
		int r;
		String insertTableSQL = "INSERT INTO amici (nome,cognome)"
				+ "values (?,?)";
		PreparedStatement ps = c.prepareStatement(insertTableSQL);
		ps.setString(1, nom);
		ps.setString(2, cog);
		r = ps.executeUpdate();

		return r;
	}



	public static int modTab(Connection c, int cod,String nom, String cog) throws SQLException {
		int r;
		String updateSQL = "update amici set nome=?,cognome=? where id=?";
		PreparedStatement ps = c.prepareStatement(updateSQL);
		ps.setString(1, nom);
		ps.setString(2, cog);
		ps.setInt(3,cod);
		r = ps.executeUpdate();

		return r;
	}
	// restituiscecome vettore di stringhe l'elenco di campi del recordset

	public static String [] rsToVect(ResultSet r) throws SQLException{
		String sr[]=new String[r.getMetaData().getColumnCount()];
		for (int i=0;i< sr.length;i++)
			sr[i]=r.getString(i);
		return sr;
	}


	public static void main(String[] arg) {
		Scanner T = new Scanner(System.in);

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://172.16.1.99/db99999?user=ut99999&password=pw99999");

			boolean cont = true;
			while (cont) {
				if (esiste(conn, "amici")) {
					System.out.println("Tabella esistente");
				} else {
					System.out.println("Tabella inesistente");
				}

				System.out.print("0 Uscita\n1 Crea Tabella\n2 visualizza\n3 Aggiungi\n4 Mod Tabella\n");
				String sc = T.nextLine();

				int res;
				String nome,
						cognome;
				int cod;

				switch (sc.charAt(0)) {
					case '0':
						cont = false;
						break;
					case '2':
						visualTab(conn);
						break;
					case '3':
						System.out.print("Nome -->");
						nome = T.nextLine();
						System.out.print("Cognome -->");
						cognome = T.nextLine();

						res = insertTab02(conn, nome, cognome);
						System.out.println("risultato " + res);
						break;

					case '4':
						System.out.print("Codice -->");
						cod = Integer.parseInt(T.nextLine());

						System.out.print("Nome -->");
						nome = T.nextLine();
						System.out.print("Cognome -->");
						cognome = T.nextLine();

						res = modTab(conn, cod,nome, cognome);
						System.out.println("risultato " + res);
						break;
					case '1':

						creaTab(conn);
						break;

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