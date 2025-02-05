package org.example.vetmais.Model.Validacao;

public class ValidarCPF {
    public String ValidarCPF(String cpf) {

        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return "Inválido";
        }

        // Verifica se todos os números do CPF são iguais
        if (cpf.chars().distinct().count() == 1) {
            return "Inválido";
        }

        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Integer.parseInt(String.valueOf(cpf.charAt(i)));
        }

        // Validação do primeiro dígito verificador
        int S1 = 0;
        for (int k = 0; k < 9; k++) {
            S1 += (10 - k) * digits[k];
        }
        int R1 = S1 % 11;
        if (R1 < 2) {
            if (digits[9] != 0) {
                return "Inválido";
            }
        } else {
            if (digits[9] != 11 - R1) {
                return "Inválido";
            }
        }

        // Validação do segundo dígito verificador
        int S2 = 0;
        for (int j = 0; j < 10; j++) {
            S2 += (11 - j) * digits[j];
        }
        int R2 = S2 % 11;
        if (R2 < 2) {
            if (digits[10] != 0) {
                return "Inválido";
            }
        } else {
            if (digits[10] != 11 - R2) {
                return "Inválido";
            }
        }

        return "Válido";
    }
}
