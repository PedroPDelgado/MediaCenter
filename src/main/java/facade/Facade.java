/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import administracao.Administrador;
import utilizadores.EmailExistenteException;
import utilizadores.RegistadoExistenteException;
import java.io.File;
import media.Album;
import media.Colecao;
import media.Conteudo;
import utilizadores.PasswordIncorretaException;
import utilizadores.Registado;
import utilizadores.RegistadoInexistenteException;
import utilizadores.Registados;

/**
 *
 * @author pedro
 */
public class Facade {
    
    private static final Registados registados = new Registados();
    
    public Facade(){
    }
    
    public void apagaBD(){
        this.registados.getRD().apaga();
    }
    
    public void inicializaSistema(String password){
        this.registados.inicializarSistema(password);
    }
    
    public boolean logInAdministrador(String password){
        return this.registados.logInAdministrador(password);
    }
    
    public Registado logIn(String nome, String password) throws RegistadoInexistenteException, PasswordIncorretaException{
        return this.registados.logIn(nome, password);
    }
    
    public void registaRegistado(String nome, String email, String password) throws RegistadoExistenteException, EmailExistenteException{
        this.registados.registaRegistado(nome, email, password);
    }
    
    public void uploadAlbum(String nomeAlbum,String categoria, Registado r, String[] filepaths) throws AlbumInvalidoException{
        if(testaFicheirosAlbum(filepaths)){
            int idAlbum = r.getAd().size(); 
            Conteudo c;
            Album a = new Album(idAlbum, nomeAlbum, categoria, r.getNome());
            
            r.getAd().put(idAlbum, a);
             
            for (int i = 0; i < filepaths.length; i++) {
                c = new Conteudo(r.getCd().get(1).getCd().size()+1, getFileName(filepaths[i]), a.getCategoria(), filepaths[i], -1, idAlbum);
                r.getCd().get(1).getCd().add(c);
                if(!r.getCd().get(1).existe(c)){
                    c = new Conteudo(r.getCd().get(1).getCd().size()+1, getFileName(filepaths[i]), a.getCategoria(), filepaths[i], 1, -1);
                    r.getCd().get(1).getCd().add(c);
                }
                if(!r.getCd().getColDefaultRegistado(r.getNome()).existe(c)){
                    c = new Conteudo(r.getCd().get(1).getCd().size()+1, getFileName(filepaths[i]), a.getCategoria(), filepaths[i],
                            r.getCd().getColDefaultRegistado(r.getNome()).getId(), -1);
                    r.getCd().get(1).getCd().add(c);
                }
            }
        }else{
            throw new AlbumInvalidoException();
        }
    }
    
    private boolean testaFicheirosAlbum(String[] filepaths){
        for (int i = 0; i < filepaths.length; i++) {
           if(!getFileExtension(filepaths[i]).equals(".mp3") && !getFileExtension(filepaths[i]).equals(".wav")) return false; 
        }
        return true;
    }
    
    private String getFileExtension(String filepath) {
        String name = filepath;
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }
    
    public String getFileName(String filepath) {
        File f = new File(filepath);
        String[] nome = f.getName().split("\\.");
        return nome[0];
    }
    
    public void uploadConteudos(Registado r, Colecao c, String[] filepaths) {
        if (testaFicheirosColecao(filepaths)) {
            for (int i = 0; i < filepaths.length; i++) {
                if (r.getCd().get(1).existeFilePath(filepaths[i])) {
                    String categoria = r.getCd().get(1).procuraPorFilePath(filepaths[i]).getCategoria();
                    if (r.getCd().getColDefaultRegistado(r.getNome()).existeFilePath(filepaths[i])) {
                        if(!c.existeFilePath(filepaths[i]))
                            c.getCd().add(new Conteudo(c.getCd().size() + 1, getFileName(filepaths[i]), categoria, filepaths[i], c.getId(), -1));
                    } else {
                        c.getCd().add(new Conteudo(c.getCd().size() + 1, getFileName(filepaths[i]),
                                categoria, filepaths[i], r.getCd().getColDefaultRegistado(r.getNome()).getId(), -1));
                        c.getCd().add(new Conteudo(c.getCd().size() + 1, getFileName(filepaths[i]), categoria, filepaths[i], c.getId(), -1));
                    }
                } else {
                    c.getCd().add(new Conteudo(c.getCd().size() + 1, getFileName(filepaths[i]),
                            "", filepaths[i], r.getCd().getColDefaultRegistado(r.getNome()).getId(), -1));
                    c.getCd().add(new Conteudo(c.getCd().size() + 1, getFileName(filepaths[i]), "", filepaths[i], 1, -1));
                    c.getCd().add(new Conteudo(c.getCd().size() + 1, getFileName(filepaths[i]), "", filepaths[i], c.getId(), -1));
                }

            }
        }
    }
    
    private boolean testaFicheirosColecao(String[] filepaths){
        for (int i = 0; i < filepaths.length; i++) {
           if(!getFileExtension(filepaths[i]).equals(".mp3") && !getFileExtension(filepaths[i]).equals(".wav") 
                   && !getFileExtension(filepaths[i]).equals(".mp4")) return false; 
        }
        return true;
    }
    
    public void alteraCategoriaConteudoGlobal(Conteudo c, String categoria){
        this.registados.getCD().get(1).getCd().alteraCategoriaConteudo(c, categoria);
    }
    
    public void alteraCategoriaConteudoRegistado(Registado r, Conteudo c, String categoria){
        this.registados.alteraCategoriaConteudoRegistado(r, c, categoria);
    }
    
    public boolean existeAdministrador(){
        Administrador a = this.registados.getRD().getAdministrador();
        if(a == null) return false;
        else return true;
    }
    
}
