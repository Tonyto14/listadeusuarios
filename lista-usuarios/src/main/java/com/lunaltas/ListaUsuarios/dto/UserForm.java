package com.lunaltas.ListaUsuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Objeto usado pelos formulários (binding + validação). */
public class UserForm {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String name;

    @NotBlank(message = "Arroba é obrigatória")
    @Size(max = 50, message = "Arroba deve ter no máximo 50 caracteres")
    private String arroba;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos (somente números)")
    private String cpf;

    public UserForm() {
    }

    public UserForm(Long id, String name, String arroba, String cpf) {
        this.id = id;
        this.name = name;
        this.arroba = arroba;
        this.cpf = cpf;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getArroba() { return arroba; }
    public void setArroba(String arroba) { this.arroba = arroba; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
