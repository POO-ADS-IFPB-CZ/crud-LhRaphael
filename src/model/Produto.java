package src.model;

import java.io.Serializable;
import java.util.Objects;

public class Produto implements Serializable{
    private String codigo;
    private String descricao;
    private String preco;

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPreco() {
        return preco;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Produto)){
            return false;
        }
        Produto outro = (Produto) obj;
        return Objects.equals(this.codigo, outro.codigo); // comparação correta
    }



    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

     @Override
    public String toString() {
        return "Produto{" +
                "codigo='" + codigo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                '}';
    }
}
