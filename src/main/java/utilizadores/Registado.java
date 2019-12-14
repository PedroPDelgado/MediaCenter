/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilizadores;

import daos.AlbunsDAO;
import daos.ColecoesDAO;
import media.Colecao;

/**
 *
 * @author pedro
 */
public class Registado {
    private String nome;
    private String email;
    private String password;
    private ColecoesDAO cd;
    private AlbunsDAO ad;
    
    public Registado(String n, String e, String p){
        this.nome = n;
        this.email = e;
        this.password = p;
        this.cd = ColecoesDAO.getInstance();
        this.ad = AlbunsDAO.getInstance();
    }

    public void setNome(String n){
        this.nome = n;
    }
    
    public void setMail(String e){
        this.email = e;
    }
    
    public void setPassword(String p){
        this.password = p;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public String getEMail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public boolean comparaPassword(String p){
        return this.password.equals(p);      
    }

    public AlbunsDAO getAd() {
        return ad;
    }

    public ColecoesDAO getCd() {
        return cd;
    }
    
    @Override
    public String toString() {
        return "Registado{" + "nome=" + nome + ", email=" + email + ", password=" + password + '}';
    }

    boolean comparaEmail(String email) {
        return this.email.equals(email);
    }
    
    public void criaColecaoDeafult(){
        Colecao c = new Colecao(this.cd.size(), "default", "default", this.nome);
        this.cd.put(c.getId(), c);
    }

    public void alteraCategoriaColecao(Colecao c, String categoria){
        this.cd.alteraCategoriaColecao(c, categoria);
    }
    
}
