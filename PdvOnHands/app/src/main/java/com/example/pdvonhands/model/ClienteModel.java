package com.example.pdvonhands.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ClienteModel implements Parcelable {

    private int id;
    private String nome;
    private String cpfcnpj;
    private String email;

    public ClienteModel() {
    }

    public ClienteModel(int id, String nome, String cpfcnpj, String email) {
        this.id = id;
        this.nome = nome;
        this.cpfcnpj = cpfcnpj;
        this.email = email;
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

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ClienteModel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpfcnpj='" + cpfcnpj + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    protected ClienteModel(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        cpfcnpj = in.readString();
        email = in.readString();
    }

    public static final Creator<ClienteModel> CREATOR = new Creator<ClienteModel>() {
        @Override
        public ClienteModel createFromParcel(Parcel in) {
            return new ClienteModel(in);
        }

        @Override
        public ClienteModel[] newArray(int size) {
            return new ClienteModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(cpfcnpj);
        dest.writeString(email);
    }

}
