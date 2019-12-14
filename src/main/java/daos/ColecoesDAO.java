/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import media.Colecao;

/**
 *
 * @author pedro
 */



public class ColecoesDAO implements Map<Integer,Colecao>{

    private static final String CONN = "jdbc:mysql://localhost/mydb"; 
    public static final String USR = "root";
    public static final String PWD = "password";
    

    
    private static ColecoesDAO inst = null;
    
    private ColecoesDAO () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {throw new NullPointerException(e.getMessage());}
    }
    
    public static ColecoesDAO getInstance() {
        if (inst == null) {
            inst = new ColecoesDAO();
        }
        return inst;
    }
    
    @Override
   public void clear() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Colecoes");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
   }
   
   public boolean containsKey(Object key) throws NullPointerException {
       try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            PreparedStatement ps = conn.prepareStatement("SELECT idColecao FROM Colecoes WHERE idColecao=?");
            ps.setInt(1, (Integer) key);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public boolean containsValue(Object value) {
        if (value instanceof Colecao) {
            int id = ((Colecao) value).getId();
            try ( Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
                Statement stm = conn.createStatement();
                String sql = "SELECT idColecao FROM Colecoes WHERE idColecao='" + id + "'";
                ResultSet rs = stm.executeQuery(sql);
                Colecao c = new Colecao(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                return ((Colecao) value).equals(c);
            } catch (Exception e) {
                throw new NullPointerException(e.getMessage());
            }
        } else {
            return false;
        }
    }
    
    public Set<Map.Entry<Integer,Colecao>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }
    
    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }
    
    public Colecao get(Object key) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Colecao c = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Colecoes WHERE idColecao=?");
            ps.setInt(1, (Integer) key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
                c = new Colecao(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            return c;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public int hashCode() {
        return this.inst.hashCode();
    }
    
    public boolean isEmpty() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idColecao FROM Colecoes");
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public Set<Integer> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    
     public Colecao put(Integer key, Colecao value) {
        try ( Connection conn = DriverManager.getConnection(CONN, USR, PWD)) {
            Colecao c = null;
            conn.setAutoCommit(false);
            try {
                System.out.println(key);
                PreparedStatement ps = conn.prepareStatement("DELETE FROM Colecoes WHERE idColecao=?");
                ps.setInt(1, key);
                ps.executeUpdate();
                ps = conn.prepareStatement("INSERT INTO Colecoes VALUES (?,?,?,?)");
                ps.setInt(1, value.getId());
                ps.setString(2, value.getNome());
                ps.setString(3, value.getCategoria());
                ps.setString(4, value.getNomeRegistado());
                ps.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
            }
            conn.commit();
            return new Colecao(value.getId(), value.getNome(), value.getCategoria(), value.getNomeRegistado());
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
     
    
    public void putAll(Map<? extends Integer,? extends Colecao> t) {
        throw new NullPointerException("Not implemented!");
    }
    
    public Colecao remove(Object key) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Colecao c = this.get(key);
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Colecoes WHERE idColecao =?");
            ps.setInt(1, (Integer) key);
            int i  = ps.executeUpdate();
            return c;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public int size() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idColecao FROM Colecoes");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public Collection<Colecao> values() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Collection<Colecao> col = new HashSet<Colecao>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Colecoes");
            for (;rs.next();) {
                col.add(new Colecao(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public Colecao getColDefaultRegistado(String nomeRegistado){
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Colecoes WHERE nome = ? AND nomeRegistado = ?");
            ps.setString(1, "default");
            ps.setString(2,nomeRegistado);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            Colecao c = new Colecao(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            return c;
            }
            else return null;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public List<Colecao> getColecoesRegistado(String nomeRegistado){
         try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            List<Colecao> col = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Colecoes WHERE nomeRegistado='" + nomeRegistado + "'");
            for (;rs.next();) {
                col.add(new Colecao(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void alteraCategoriaColecao(Colecao c,String novaCategoria) {
        try ( Connection conn = DriverManager.getConnection(CONN, USR, PWD)) {

            PreparedStatement ps = conn.prepareStatement("UPDATE Colecoes SET categoria=? WHERE idColecao=?");
            ps.setString(1, novaCategoria);
            ps.setInt(2, c.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
    
}
