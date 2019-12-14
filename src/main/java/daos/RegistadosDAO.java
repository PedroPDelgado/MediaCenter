/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import administracao.Administrador;
import utilizadores.Registado;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.sql.*;

/**
 *
 * @author pedro
 */



public class RegistadosDAO implements Map<String,Registado>{

    private static final String CONN = "jdbc:mysql://localhost/mydb"; //ESTOU A USAR UMA BASE DE DADOS DIFERENTE!
    public static final String USR = "root";
    public static final String PWD = "password";
    

    
    private static RegistadosDAO inst = null;
    
    private RegistadosDAO () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {throw new NullPointerException(e.getMessage());}
    }
    
    public static RegistadosDAO getInstance() {
        if (inst == null) {
            inst = new RegistadosDAO();
        }
        return inst;
    }
    
    @Override
   public void clear() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Registados");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
   }
   
   public boolean containsKey(Object key) throws NullPointerException {
       try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            PreparedStatement ps = conn.prepareStatement("SELECT nome FROM Registados WHERE nome=?");
            ps.setString(1, (String) key);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public boolean containsValue(Object value) {
        if (value instanceof Registado) {
            String nome = ((Registado) value).getNome();
            try ( Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
                Statement stm = conn.createStatement();
                String sql = "SELECT nome FROM Registados WHERE nome='" + nome + "'";
                ResultSet rs = stm.executeQuery(sql);
                Registado r = new Registado(rs.getString(1),rs.getString(2),rs.getString(3));
                return ((Registado) value).equals(r);
            } catch (Exception e) {
                throw new NullPointerException(e.getMessage());
            }
        } else {
            return false;
        }
    }
    
    public Set<Map.Entry<String,Registado>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }
    
    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }
    
    public Registado get(Object key) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Registado r = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Registados WHERE nome=?");
            ps.setString(1, (String) key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
                r = new Registado(rs.getString(1),rs.getString(2),rs.getString(3));
            return r;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public int hashCode() {
        return this.inst.hashCode();
    }
    
    public boolean isEmpty() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nome FROM Registados");
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    
     public Registado put(String key, Registado value) {
        try ( Connection conn = DriverManager.getConnection(CONN, USR, PWD)) {
            Registado r = null;
            conn.setAutoCommit(false);
            try {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM Registados WHERE nome=?");
                ps.setString(1, key);
                ps.executeUpdate();
                ps = conn.prepareStatement("INSERT INTO Registados VALUES (?,?,?)");
                ps.setString(1, value.getNome());
                ps.setString(2, value.getEMail());
                ps.setString(3, value.getPassword());
                ps.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
            }
            conn.commit();
            return new Registado(value.getNome(), value.getEMail(), value.getPassword());
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
     
    
    public void putAll(Map<? extends String,? extends Registado> t) {
        throw new NullPointerException("Not implemented!");
    }
    /*
    public Registado remove(Object key) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Registado r = this.get(key);
            Statement stm = conn.createStatement();
            String sql = "DELETE '"+key+"' FROM Registados";
            int i  = stm.executeUpdate(sql);
            return r;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    */
    
    public Registado remove(Object key) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Registado r = this.get(key);
            PreparedStatement ps = conn.prepareStatement("DELETE ? FROM Registados");
            ps.setString(1, (String) key);
            int i  = ps.executeUpdate();
            return r;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public int size() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nome FROM Registados");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public Collection<Registado> values() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Collection<Registado> col = new HashSet<Registado>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Registados");
            for (;rs.next();) {
                col.add(new Registado(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public Administrador getAdministrador(){
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Administrador a = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Registados WHERE nome=?");
            ps.setString(1, "admin");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
                a = new Administrador(rs.getString(3));
            return a;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void apaga() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            ps.executeUpdate();
            ps = conn.prepareStatement("TRUNCATE TABLE Registados");
            ps.executeUpdate();
            ps = conn.prepareStatement("TRUNCATE TABLE Colecoes");
            ps.executeUpdate();
            ps = conn.prepareStatement("TRUNCATE TABLE Albuns");
            ps.executeUpdate();
            ps = conn.prepareStatement("TRUNCATE TABLE Conteudos");
            ps.executeUpdate();
            ps = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1");
            ps.executeUpdate();
            conn.commit();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    
}
