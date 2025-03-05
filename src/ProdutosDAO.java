/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import com.mysql.cj.xdevapi.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;


public class ProdutosDAO {
    
     Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
        //conn = new conectaDAO().connectDB();

        try {
            conectaDAO conexao = new conectaDAO();
            conexao.connectDB();
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

         
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql, new String[]{"ID"});

            consulta.setString(1, produto.getNome());
            consulta.setInt(2, produto.getValor());
            consulta.setString(3, produto.getStatus());

            int status = consulta.executeUpdate();

           
            if (status == 1) {
                ResultSet generatedKeys = consulta.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGerado = generatedKeys.getInt(1);
                    JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso");
                    System.out.println("Produto cadastrado com sucesso. ID gerado: " + idGerado);
                } else {
                    System.out.println("Erro ao recuperar o ID gerado.");
                }
            } else {
                System.out.println("Erro ao cadastrar o produto.");
            }

            conexao.desconectar();

        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar dados: " + ex.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> produtos = new ArrayList<ProdutosDTO>();

        try {
            conectaDAO conexao = new conectaDAO();
            conexao.connectDB();

            String sql = "select * from produtos ";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);
            ResultSet resposta = consulta.executeQuery();

            while (resposta.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resposta.getInt("id"));
                p.setNome(resposta.getString("nome"));
                p.setValor(resposta.getInt("valor"));
                p.setStatus(resposta.getString("status"));
                produtos.add(p);
            }

            conexao.desconectar();

        } catch (SQLException ex) {
            System.out.println("ERRO ao tentar listar todos: " + ex.getMessage());

        }
        return produtos;

        //return listagem;
    
    
    }
}

