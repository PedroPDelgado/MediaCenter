/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import static daos.ColecoesDAO.PWD;
import static daos.ColecoesDAO.USR;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import media.Album;
import media.Colecao;

/**
 *
 * @author pedro
 */



public class AlbunsDAO implements Map<Integer,Album>{

    private static final String CONN = "jdbc:mysql://localhost/mydb"; 
    public static final String USR = "root";
    public static final String PWD = "password";
    

    
    private static AlbunsDAO inst = null;
    
    private AlbunsDAO () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {throw new NullPointerException(e.getMessage());}
    }
    
    public static AlbunsDAO getInstance() {
        if (inst == null) {
            inst = new AlbunsDAO();
        }
        return inst;
    }
    
    @Override
   public void clear() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Albuns");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
   }
   
   public boolean containsKey(Object key) throws NullPointerException {
       try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            PreparedStatement ps = conn.prepareStatement("SELECT idAlbum FROM Albuns WHERE idAlbum=?");
            ps.setInt(1, (Integer) key);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public boolean containsValue(Object value) {
        if (value instanceof Album) {
            int id = ((Album) value).getId();
            try ( Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
                Statement stm = conn.createStatement();
                String sql = "SELECT idAlbum FROM Albuns WHERE idAlbum='" + id + "'";
                ResultSet rs = stm.executeQuery(sql);
                Album a = new Album(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                return ((Album) value).equals(a);
            } catch (Exception e) {
                throw new NullPointerException(e.getMessage());
            }
        } else {
            return false;
        }
    }
    
    public Set<Map.Entry<Integer,Album>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }
    
    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }
    
    public Album get(Object key) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Album a = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Albuns WHERE idAlbum=?");
            ps.setInt(1, (Integer) key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
                a = new Album(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            return a;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public int hashCode() {
        return this.inst.hashCode();
    }
    
    public boolean isEmpty() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idAlbum FROM Albuns");
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public Set<Integer> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    
     public Album put(Integer key, Album value) {
        try ( Connection conn = DriverManager.getConnection(CONN, USR, PWD)) {
            Album a = null;
            conn.setAutoCommit(false);
            try {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM Albuns WHERE idAlbum=?");
                ps.setInt(1, key);
                ps.executeUpdate();
                ps = conn.prepareStatement("INSERT INTO Albuns VALUES (?,?,?,?)");
                ps.setInt(1, value.getId());
                ps.setString(2, value.getNome());
                ps.setString(3, value.getCategoria());
                ps.setString(4, value.getNomeRegistado());
                ps.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
            }
            conn.commit();
            return new Album(value.getId(), value.getNome(), value.getCategoria(), value.getNomeRegistado());
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
     
    
    public void putAll(Map<? extends Integer,? extends Album> t) {
        throw new NullPointerException("Not implemented!");
    }
    
    public Album remove(Object key) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Album a = this.get(key);
            PreparedStatement ps = conn.prepareStatement("DELETE ? FROM Albuns");
            ps.setInt(1, (Integer) key);
            int i  = ps.executeUpdate();
            return a;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public int size() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idAlbum FROM Albuns");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public Collection<Album> values() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Collection<Album> col = new HashSet<Album>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Albuns");
            for (;rs.next();) {
                col.add(new Album(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
     public List<Album> getAlbunsRegistado(String nomeRegistado){
         try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            List<Album> als = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Albuns WHERE nomeRegistado='" + nomeRegistado + "'");
            for (;rs.next();) {
                als.add(new Album(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            return als;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
}
