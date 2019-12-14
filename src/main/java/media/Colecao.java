/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media;

import daos.ConteudosDAO;
import daos.RegistadosDAO;
import java.util.List;
import java.util.Objects;
import utilizadores.Registado;

/**
 *
 * @author pedro
 */
public class Colecao {
    
    private int id;
    private String nome;
    private String categoria;
    private String nomeRegistado;
    private ConteudosDAO cd;

    public Colecao(int id, String nome, String categoria, String nomeRegistado) {
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
    
    public boolean existe(Conteudo c){
        List<Conteudo> cs = this.cd.listaConteudosColecao(this.id);
        for (Conteudo cont : cs) {
            if (cont.comparaFilePath(c.getFilepath())) {
                return true;
            }
        }
        return false;
    }

    public boolean existeFilePath(String filepath){
        List<Conteudo> cs = this.cd.listaConteudosColecao(this.id);
        for (Conteudo cont : cs) {
            if (cont.comparaFilePath(filepath)) {
                return true;
            }
        }
        return false;
    }


    
    public Conteudo procuraPorFilePath(String filepath){
        List<Conteudo> cs = this.cd.listaConteudosColecao(this.id);
        for (Conteudo cont : cs) {
            if (cont.comparaFilePath(filepath)) {
                return cont;
            }
        }
        return null;
    }
    
    public void alteraCategoriaConteudo(String filepath, String categoria){
        List<Conteudo> lc = this.cd.listaConteudosColecao(this.id);
        for (Conteudo c : lc) {
            if(c.getFilepath().equals(filepath))
               this.cd.alteraCategoriaConteudo(c, categoria);
        }
    }
    
    public Registado getRegistado(){
        return RegistadosDAO.getInstance().get(nomeRegistado);
    }
    
    
    @Override
    public String toString() {
        return this.nome + "  " + this.categoria;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        final Colecao other = (Colecao) obj;
        
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
