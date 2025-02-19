package org.example.vetmais.Domain;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.example.vetmais.Model.Validacao.ValidarCPF;
import org.example.vetmais.Model.Validacao.ValidarEmail;

public class User {
    private String email;
    private String password;
    private String cpf;
    private String name;
    private String privilege;

    public User() {}

    public User(String email, String password, String cpf, String name, String privilege) {
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.name = name;
        this.privilege = privilege;
    }

    public User(String email, String password, String cpf, String name) {
        this(email, password, cpf, name, "normal");
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCPF(String cpf)  {
        this.cpf = cpf;
    }

    public String getCPF() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidEmail() {
        //return email != null && email.contains("@");
        ValidarEmail validarEmail = new ValidarEmail();
        String email = validarEmail.ValidarEmail(getEmail());
        return email.contains("Válido");
    }

    public boolean isValidCPF() {
        ValidarCPF validarCPF = new ValidarCPF();
        String cpf = validarCPF.ValidarCPF(getCPF());
        return cpf.contains("Válido");
    }

    public void setEncryptedPassword(String password) {
        this.password = hashPassword(password);
    }

    private String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();  // Cria uma instância do Argon2
        return argon2.hash(4, 1024 * 1024, 8, password.toCharArray());  // Hash com 4 iterações, 1MB de memória, 8 threads
    }
}
