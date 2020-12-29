package br.com.bobsundyn.model;

public class Livro {

    public String titulo;
    public String autor;
    public String editora;
    public String imagem;
    private String key;

    public Livro(){

    }

    public Livro(String titulo, String autor, String editora, String imagem) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.imagem = imagem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
