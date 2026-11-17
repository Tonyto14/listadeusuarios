package com.lunaltas.ListaUsuarios.service;

import com.lunaltas.ListaUsuarios.dto.UserForm;
import com.lunaltas.ListaUsuarios.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Regras de negócio / acesso a dados. A conexão é aberta pelo DbConnectionFilter.
 */
@Service
public class UserService {

    public List<User> findAll() {
        return new ArrayList<>(User.findAll().orderBy("id"));
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(User.findById(id));
    }

    public User create(UserForm form) {
        User user = new User();
        fill(user, form);
        if (!user.saveIt()) {
            throw new IllegalStateException("Erro ao salvar: " + user.errors());
        }
        return user;
    }

    public Optional<User> update(Long id, UserForm form) {
        User user = User.findById(id);
        if (user == null) {
            return Optional.empty();
        }
        fill(user, form);
        if (!user.saveIt()) {
            throw new IllegalStateException("Erro ao atualizar: " + user.errors());
        }
        return Optional.of(user);
    }

    public boolean delete(Long id) {
        User user = User.findById(id);
        return user != null && user.delete();
    }

    /** Verifica se o CPF já pertence a outro usuário (ignoreId = usuário sendo editado). */
    public boolean cpfInUse(String cpf, Long ignoreId) {
        if (ignoreId == null) {
            return User.count("cpf = ?", cpf) > 0;
        }
        return User.count("cpf = ? AND id <> ?", cpf, ignoreId) > 0;
    }

    public UserForm toForm(User user) {
        return new UserForm(((Number) user.getId()).longValue(),
                user.getName(), user.getArroba(), user.getCpf());
    }

    private void fill(User user, UserForm form) {
        user.setName(form.getName().trim());
        user.setArroba(form.getArroba().trim());
        user.setCpf(form.getCpf());
    }
}
