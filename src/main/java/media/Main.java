/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media;

import daos.*;
import facade.Facade;
import utilizadores.Registado;

/**
 *
 * @author pedro
 */
public class Main {
    
    /*private static final String CONN = "jdbc:mysql://localhost/Clinica";
    public static final String USR = "root";
    public static final String PWD = "password";
    */
    public static void main(String[] args) throws Exception{
      /* Connection conn = null;
       conn = DriverManager.getConnection(CONN,USR,PWD);
       Statement stm = conn.createStatement();
       String sql = "SELECT * FROM MEDICO WHERE nome='Jose Maria'";
       ResultSet rs = stm.executeQuery(sql);
       rs.next();
       System.out.println(rs.getString(2));
    */
      
      ColecoesDAO cd = ColecoesDAO.getInstance();
      
      Facade facade = new Facade();
      facade.apagaBD();
      facade.inicializaSistema("password");
      Registado r = new Registado("divadoten", "email", "pass");
      facade.registaRegistado("divadoten", "email@gmail.com", "password");
      Colecao c = new Colecao(cd.size(), "colecao1", "Pop", r.getNome());
      cd.put(cd.size(), c);
      
      String[] filepaths1 = {"home/music/hello.mp3"};
      String[] filepaths2 = {"home/music/hello.mp3","home/music/as.mp4","home/videos/christmas.mp4"};
      
      facade.uploadConteudos(r, c, filepaths1);
      facade.uploadConteudos(r, c, filepaths2);
      Conteudo con = new Conteudo(3,"hello","","home/music/hello.mp3",3,-1);
      facade.alteraCategoriaConteudoRegistado(r, con, "nova categoria");
      
    }
}
