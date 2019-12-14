/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media;

/**
 *
 * @author pedro
 */
public class Conteudo {
    
    private int id;
    private String nome;
    private String categoria;
    private String filepath;
    private int idColecao;
    private int idAlbum;
    
    public Conteudo(int id, String nome, String categoria, String filepath, int idColecao, int idAlbum){
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.filepath = filepath;
        this.idColecao = idColecao;
        this.idAlbum = idAlbum;
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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getIdColecao() {
        return idColecao;
    }

    public void setIdColecao(int idColecao) {
        this.idColecao = idColecao;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        final Conteudo other = (Conteudo) obj;
        
        if (this.id != other.id || this.idColecao != other.idColecao 
                || this.idAlbum != other.idAlbum || !(this.nome.equals(other.nome))
                || !(this.categoria.equals(other.categoria)) || !(this.filepath.equals(other.filepath))) {
            return false;
        }
        return true;
    }
    
    public boolean comparaFilePath(String filepath){
        return this.filepath.equals(filepath);
    }
            
    @Override
    public String toString() {
        return this.nome + "   " + this.categoria;
    }
    
    
    
    
}

