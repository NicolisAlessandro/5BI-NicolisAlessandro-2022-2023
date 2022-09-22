import java.sql.*;
import java.until.*;


public JDBC{

        public JDBC{
        CREATE DATABASE IF NOT EXISTS dbfrasi;

        use dbfrasi;

        drop user if exists 'utfrasi'@'%';
        drop user if exists 'utfrasi'@'localhost';
        DROP TABLE IF EXISTS frase;
        DROP TABLE IF EXISTS autore ;

        GRANT ALL PRIVILEGES ON dbfrasi.* TO 'utfrasi'@'%';
        GRANT ALL PRIVILEGES ON dbfrasi.* TO 'utfrasi'@'localhost';
        }

public static int insertProdotti(Connection c,String nome,String matricola,String cognome,String classe,String annoSc)throws SQLException{
        int r;
        String insertTableSQL="INSERT INTO prodotti values (?,?,?)";
        PreparedStatement ps=c.prepareStatement(insertTableSQL);
        ps.setString(1,nome);
        ps.setString(2,cognome);
        ps.setString(3,matricola);
        r=ps.executeUpdate();
        return r;
        }


public static int creaTabAutore(Connection c)throws SQLException{
        Statement stmt=c.createStatement();
        String create="CREATE TABLE studenti ("
        +"  nome varchar(10) primary key,"
        +"  cognome varchar(30) DEFAULT NULL,"
        +"  matricola int not null auto_increment,"
        +") ";
        System.out.println(create);
        int r=stmt.executeUpdate(create);
        return r;
        }

public static int creaTabFrase(Connection c)throws SQLException{
        Statement stmt=c.createStatement();
        String create="CREATE TABLE studenti ("
        +"  nome varchar(10) primary key,"
        +"  cognome varchar(30) DEFAULT NULL,"
        +"  matricola int not null auto_increment,"
        +") ";
        System.out.println(create);
        int r=stmt.executeUpdate(create);
        return r;
        }


public static void visAutori(Connection c)throws SQLException{
        Statement stmt=c.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT * FROM autori");

        while(rs.next()){
        System.out.println(rs.getString("nome")
        +"  "+rs.getString("cognome")+"  "
        rs.getString("matricola")
        }
        stmt.close();
        }
}


public class JDBCTest {
        public static void main(String[] args) throws SQLEexception{
                conn=DriverManager.getConnection("jdbc:sqlite:magaz.db", );
        }
}