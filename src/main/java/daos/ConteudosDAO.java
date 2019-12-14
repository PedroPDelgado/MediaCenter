/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import media.Conteudo;
import utilizadores.Registado;

/**
 *
 * @author pedro
 */
public class ConteudosDAO implements Set<Conteudo>{
    
    private static final String CONN = "jdbc:mysql://localhost/mydb"; 
    public static final String USR = "root";
    public static final String PWD = "password";
    

    
    private static ConteudosDAO inst = null;
    
    private ConteudosDAO () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {throw new NullPointerException(e.getMessage());}
    }
    
    public static ConteudosDAO getInstance() {
        if (inst == null) {
            inst = new ConteudosDAO();
        }
        return inst;
    }
    

    @Override
    public int size() {
         try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idConteudo FROM Conteudos");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public boolean isEmpty() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idConteudo FROM Conteudos");
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public boolean contains(Object value) {
        if (value instanceof Conteudo) {
            int id = ((Conteudo) value).getId();
            Conteudo c = null;
            try ( Connection conn = DriverManager.getConnection(CONN, USR, PWD)) {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Conteudos WHERE idConteudo = ?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    c = new Conteudo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
                    System.out.println(c);
                }
                
                return ((Conteudo) value).equals(c);
                
            } catch (Exception e) {
                throw new NullPointerException(e.getMessage());
            }
        } else {
            return false;
        }
    }

    @Override
    public Iterator<Conteudo> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Collection<Conteudo> col = new HashSet<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Conteudos");
            for (;rs.next();) {
                col.add(new Conteudo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6)));
            }
            return col.toArray();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    public List<Conteudo> listaConteudosColecao(int id) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            List<Conteudo> cs = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Conteudos WHERE idColecao ='" + id + "'");
            for (;rs.next();) {
                cs.add(new Conteudo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6)));
            }
            return cs;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public List<Conteudo> listaConteudosAlbum(int id) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            List<Conteudo> cs = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Conteudos WHERE idAlbum ='" + id + "'");
            for (;rs.next();) {
                cs.add(new Conteudo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6)));
            }
            return cs;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    public <T> T[] toArray(T[] arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(Conteudo value) {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            boolean res = false;
            if(!this.contains(value)){
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Conteudos VALUES (?,?,?,?,?,?)");
                ps.setInt(1, value.getId());
                ps.setString(2, value.getNome());
                ps.setString(3, value.getCategoria());
                ps.setString(4, value.getFilepath());
                ps.setInt(5, value.getIdColecao());
                ps.setInt(6, value.getIdAlbum());
                ps.executeUpdate();
                res = true; 
           }
            return res;
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());}
    }

    @Override
    public boolean remove(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends Conteudo> arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(CONN,USR,PWD)) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Colecoes");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void alteraCategoriaConteudo(Conteudo c, String categoria) {
        try ( Connection conn = DriverManager.getConnection(CONN, USR, PWD)) {

            PreparedStatement ps = conn.prepareStatement("UPDATE Conteudos SET categoria=? WHERE idConteudo=?");
            ps.setString(1, categoria);
            ps.setInt(2, c.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    /*public void alteraCategoriaConteudoRegistado(Registado r, Conteudo c, String categoria) {
        try ( Connection conn = DriverManager.getConnection(CONN, USR, PWD)) {

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Conteudos WHERE nomeRegistado='" + r.getNome() + "'" 
                    + " AND idColecao!=1 AND filepath='" + c.getFilepath() + "'");
            Conteudo con;
            for (;rs.next();) {
                con = new Conteudo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6));
                alteraCategoriaConteudo(con, categoria);
            }

        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }*/
    
    public void alteraConteudosRegistado(Registado r, String filepath, String novaCategoria){
        try ( Connection conn = DriverManager.getConnection(CONN, USR, PWD)) {

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Conteudos WHERE idColecao "
                    + "IN (SELECT idColecao FROM Colecoes WHERE nomeRegistado='" + r.getNome() +
                    "') AND filepath = '" + filepath + "'");
            Conteudo con;
            for (;rs.next();) {
                con = new Conteudo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6));
                alteraCategoriaConteudo(con, novaCategoria);
            }

        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    
}
