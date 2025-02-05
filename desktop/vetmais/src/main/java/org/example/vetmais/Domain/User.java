package org.example.vetmais.Domain;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.example.vetmais.Model.Validacao.ValidarEmail;

public class User {
    private String email;
    private String password;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setEncryptedPassword(String password) {
        this.password = hashPassword(password);
    }

    private String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();  // Cria uma instância do Argon2
        return argon2.hash(4, 1024 * 1024, 8, password.toCharArray());  // Hash com 4 iterações, 1MB de memória, 8 threads
    }
}
