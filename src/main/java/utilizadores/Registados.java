/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilizadores;

import administracao.Administrador;
import daos.ColecoesDAO;
import daos.RegistadosDAO;
import java.util.List;
import media.Album;
import media.Colecao;
import media.Conteudo;

/**
 *
 * @author pedro
 */
public class Registados {
    
    private RegistadosDAO registados;
    private ColecoesDAO colecoes;
    
    public Registados(){
        this.registados = RegistadosDAO.getInstance();
        this.colecoes = ColecoesDAO.getInstance();
    }

    public RegistadosDAO getRD() {
        return registados;
    }

    public ColecoesDAO getCD() {
        return colecoes;
    }
    
    public Registado logIn(String nome, String password) throws RegistadoInexistenteException, PasswordIncorretaException {

        Registado r = this.registados.get(nome);

        if (r == null) {
            throw new RegistadoInexistenteException();
        } else if (r.comparaPassword(password)) {
            return r;
        } else {
            throw new PasswordIncorretaException();
        }
    }
    
    public boolean logInAdministrador(String password){
        Administrador a = this.registados.getAdministrador();
        return a.comparaPassword(password);
    }
    
    public void registaRegistado(String nome, String email, String password) throws RegistadoExistenteException, EmailExistenteException{
        
        //TODO: validar credenciais + exception
        
        if(this.registados.containsKey(nome)) throw new RegistadoExistenteException();
        else{
            if(existeEmail(email)){
                throw new EmailExistenteException();
            }
            else{ 
                Registado r = new Registado(nome, email, password);
                this.registados.put(r.getNome(), r);
                r.criaColecaoDeafult();
            }
        }
    }
    
    private boolean existeEmail(String email){
        for(Registado r : registados.values()){
            if(r.comparaEmail(email))
                return true;
        }
        return false;
    }
    
    public void inicializarSistema(String password) {
        Registado r = new Registado("admin","",password);
        this.registados.put("admin",r);
        this.registados.put("inexistente",new Registado("inexistente", "", ""));
        
        Colecao c = new Colecao(1, "global", "global", "admin");
        this.colecoes.put(1, c);
        this.colecoes.put(-1, new Colecao(-1,"","","inexistente"));
        
        r.getAd().put(-1, new Album(-1,"","","inexistente"));
    }
    
    public void alteraCategoriaConteudoRegistado(Registado r, Conteudo c, String categoria){
        List<Colecao> cols = this.colecoes.getColecoesRegistado(r.getNome());
        for (Colecao col : cols) {
            col.alteraCategoriaConteudo(c.getFilepath(), categoria);
        }
    }    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void addRegistado(Registado r){
        this.registados.put(r.getNome(), r);
    }
    
    public void removeRegistado(String nome){
        this.registados.remove(nome);
    }
    
    public Registado getRegistado(String nome){
        return this.registados.get(nome);
    }
    
    public boolean existeRegistado(String nome){
        return this.registados.containsKey(nome);
    }
    
    public boolean validaFormatoCredenciais(String n, String e, String p){
        return (validaFormatoNome(n) && validaFormatoEmail(e) && validaFormatoPassword(p));
    }
                
    private boolean validaFormatoNome(String n){
        return true;
    }
    
    private boolean validaFormatoEmail(String n){
        return true;
    }
    
    private boolean validaFormatoPassword(String n){
        return true;
    }

    
    
}
