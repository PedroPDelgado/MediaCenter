/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracao;

/**
 *
 * @author pedro
 */
public class Administrador {
    
    private String password;
    
    public Administrador(String password){
        this.password = password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public boolean comparaPassword(String password){
        return this.password.equals(password);
    }
}
