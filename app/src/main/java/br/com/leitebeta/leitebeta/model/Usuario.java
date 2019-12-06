package br.com.leitebeta.leitebeta.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

/**
 * Created by Jonathas on 18/10/2017.
 */

public class Usuario implements Parcelable {

    private String email;
    @Exclude
    private String senha;
    private String nome;
    private String telefone;
    private String nomeFazenda;
    private String codigoProdutor;
    private int cep;

    public Usuario() {
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNomeFazenda() {
        return nomeFazenda;
    }

    public void setNomeFazenda(String nomeFazenda) {
        this.nomeFazenda = nomeFazenda;
    }

    public String getCodigoProdutor() {
        return codigoProdutor;
    }

    public void setCodigoProdutor(String codigoProdutor) {
        this.codigoProdutor = codigoProdutor;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.senha);
        dest.writeString(this.nome);
        dest.writeString(this.telefone);
        dest.writeString(this.nomeFazenda);
        dest.writeString(this.codigoProdutor);
        dest.writeInt(this.cep);
    }

    protected Usuario(Parcel in) {
        this.email = in.readString();
        this.senha = in.readString();
        this.nome = in.readString();
        this.telefone = in.readString();
        this.nomeFazenda = in.readString();
        this.codigoProdutor = in.readString();
        this.cep = in.readInt();
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}
