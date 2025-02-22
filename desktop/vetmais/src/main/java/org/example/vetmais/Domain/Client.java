package org.example.vetmais.Domain;

public class Client {
    private String name;
    private String cpf;
    private String email;
    private String telephone;

    public Client(String name, String cpf, String email, String telephone) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.telephone = telephone;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
