package org.example.vetmais.Domain;

import java.time.LocalDate;

public class Consulta {
    private String namevet;
    private LocalDate data_agendada;
    private String pet;
    private String cpf_proprietario;

    public Consulta(String namevet, LocalDate data_agendada, String pet, String cpf_proprietario) {
        this.namevet = namevet;
        this.data_agendada = data_agendada;
        this.pet = pet;
        this.cpf_proprietario = cpf_proprietario;
    }

    public String getNamevet() {
        return namevet;
    }
    public void setNamevet(String namevet) {
        this.namevet = namevet;
    }
    public LocalDate getData_agendada() {
        return data_agendada;
    }
    public void setData_agendada(LocalDate data_agendada) {
        this.data_agendada = data_agendada;
    }
    public String getPet() {
        return pet;
    }
    public void setPet(String pet) {
        this.pet = pet;
    }
    public String getCpf_proprietario() {
        return cpf_proprietario;
    }
    public void setCpf_proprietario(String cpf_proprietario) {
        this.cpf_proprietario = cpf_proprietario;
    }
}
