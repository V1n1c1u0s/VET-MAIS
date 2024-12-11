package org.example.vetmais.Domain;

import java.time.LocalDate;

public class Animal {
    private String name;
    private LocalDate birth_date;
    private String breed;
    private String cpf_proprietario;

    public Animal(String name, LocalDate birth_date, String breed, String cpf_proprietario) {
        this.name = name;
        this.birth_date = birth_date;
        this.breed = breed;
        this.cpf_proprietario = cpf_proprietario;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCpf_proprietario() {
        return cpf_proprietario;
    }

    public void setCpf_proprietario(String cpf_proprietario) {
        this.cpf_proprietario = cpf_proprietario;
    }
}
