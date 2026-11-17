package com.lunaltas.ListaUsuarios.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * Model do ActiveJDBC: não possui campos, os atributos vêm das colunas da tabela.
 * Os getters/setters abaixo são apenas atalhos (e permitem ${user.name} no Thymeleaf).
 */
@Table("users")
public class User extends Model {

    static {
        validatePresenceOf("name", "arroba", "cpf");
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getArroba() {
        return getString("arroba");
    }

    public void setArroba(String arroba) {
        set("arroba", arroba);
    }

    public String getCpf() {
        return getString("cpf");
    }

    public void setCpf(String cpf) {
        set("cpf", cpf);
    }
}
