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
import java.awt.Component;

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

    public void venderProduto(Component parent, Integer id){
        String query = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    
    // Criando conexão com o banco de dados
    try (Connection conn = new conectaDAO().getConnection();
         PreparedStatement prep = conn.prepareStatement(query)) {
        
        // Definir o parâmetro da consulta
        prep.setInt(1, id);
        
        // Executar a atualização
        int rowsAffected = prep.executeUpdate();
        
        // Exibir mensagem de sucesso ou erro caso nenhum produto seja atualizado
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(parent, "Produto vendido!");
        } else {
            JOptionPane.showMessageDialog(parent, "Nenhum produto foi encontrado com esse ID.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(parent, "Erro ao vender produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
     public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        String query = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try (Connection conn = new conectaDAO().getConnection(); 
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet resultset = prep.executeQuery()) {

            while (resultset.next()) {
                ProdutosDTO prod = new ProdutosDTO();
                prod.setId(resultset.getInt("id")); // Use o nome da coluna para evitar erros
                prod.setNome(resultset.getString("nome"));
                prod.setValor(resultset.getInt("valor"));
                prod.setStatus(resultset.getString("status"));

                listagem.add(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listagem;
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

