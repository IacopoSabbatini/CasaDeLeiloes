
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
     Connection conn;
    PreparedStatement stm;
    ResultSet rs;

    String db = "jdbc:mysql://localhost:3306/uc11";
    String user = "root";
    String senha = "Pudim23052003!";
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(db, user, senha);
    }

    public void connectDB() {
        //Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(db, user, senha);
            System.out.println("Conexao realizada com sucesso.");

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Erro ao tentar conectar: " + ex.getMessage());

        }
    }

    public void desconectar() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexao encerrada com sucesso.");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao tentar desconectar: " + ex.getMessage());
        }
    }

    public boolean isConected() {
        return (conn != null);
    }

    public Connection getConexao() {
        return conn;
    }

}

