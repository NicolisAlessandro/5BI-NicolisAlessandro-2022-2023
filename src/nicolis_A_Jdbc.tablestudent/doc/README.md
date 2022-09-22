# Program name: Jdbc.tablestudent.java

---

## Consegna

public static int insertProdotti(Connection c, String cod, String descr, String prezzo, String scadenza, String qta, String um) throws SQLException {
        int r;
        String insertTableSQL = "INSERT INTO prodotti values (?,?,?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(insertTableSQL);
        ps.setString(1, cod);
        ps.setString(2, descr);
        ps.setString(3, prezzo);
        ps.setString(4, scadenza);
        ps.setString(5, qta);
        ps.setString(6, um);
        r = ps.executeUpdate();

        return r;
    }
