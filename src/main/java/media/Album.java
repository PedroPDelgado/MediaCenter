/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media;

import daos.ConteudosDAO;

/**
 *
 * @author pedro
 */
public class Album {
    
    private int id;
    private String nome;
    private String categoria;
    private String nomeRegistado;
    private ConteudosDAO cd;

    public Album(int id, String nome, String categoria, String nomeRegistado) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.nomeRegistado = nomeRegistado;
        this.cd = ConteudosDAO.getInstance();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getNomeRegistado() {
        return nomeRegistado;
    }

    public void setNomeRegistado(String n) {
        this.nomeRegistado = n;
    }

    public ConteudosDAO getCd() {
        return cd;
    }
    
    @Override
    public String toString() {
        return this.nome + "   " + this.categoria;
    }
    
}
