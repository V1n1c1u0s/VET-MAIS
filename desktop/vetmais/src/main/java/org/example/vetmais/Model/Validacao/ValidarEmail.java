package org.example.vetmais.Model.Validacao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarEmail {
    public String ValidarEmail(String email) {
        // Expressão regular para validar o formato básico de um e-mail
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" // parte local (antes do @)
                + "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*" // parte do domínio
                + "(?:\\.[a-zA-Z]{2,})$"; // domínio de topo (ex: .com, .org, etc.)

        // Compilando a expressão regular
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        // Verifica se o e-mail corresponde ao padrão
        if (matcher.matches()) {
            return "Válido";
        } else {
            return "Inválido";
        }
    }
}
