package com.lunaltas.ListaUsuarios.controller;

import com.lunaltas.ListaUsuarios.dto.UserForm;
import com.lunaltas.ListaUsuarios.model.User;
import com.lunaltas.ListaUsuarios.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String REDIRECT_INDEX = "redirect:/users";

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // READ - lista
    @GetMapping({"", "/"})
    public String index(ModelMap model) {
        var users = service.findAll();
        model.addAttribute("users", users);
        model.addAttribute("size", users.size());
        return "users/index";
    }

    // CREATE - formulário (declarado antes de /{id} por clareza)
    @GetMapping("/new")
    public String newForm(ModelMap model) {
        model.addAttribute("user", new UserForm());
        model.addAttribute("action", "/users");
        model.addAttribute("title", "Novo usuário");
        return "users/form";
    }

    // CREATE - salvar
    @PostMapping
    public String create(@Valid @ModelAttribute("user") UserForm form,
                         BindingResult result, ModelMap model, RedirectAttributes ra) {
        if (!result.hasFieldErrors("cpf") && service.cpfInUse(form.getCpf(), null)) {
            result.rejectValue("cpf", "duplicate", "CPF já cadastrado");
        }
        if (result.hasErrors()) {
            model.addAttribute("action", "/users");
            model.addAttribute("title", "Novo usuário");
            return "users/form";
        }
        service.create(form);
        ra.addFlashAttribute("message", "Usuário criado com sucesso!");
        return REDIRECT_INDEX;
    }

    // READ - detalhes
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, ModelMap model, RedirectAttributes ra) {
        Optional<User> user = service.findById(id);
        if (user.isEmpty()) {
            return notFound(ra);
        }
        model.addAttribute("user", user.get());
        return "users/show";
    }

    // UPDATE - formulário
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, ModelMap model, RedirectAttributes ra) {
        Optional<User> user = service.findById(id);
        if (user.isEmpty()) {
            return notFound(ra);
        }
        model.addAttribute("user", service.toForm(user.get()));
        model.addAttribute("action", "/users/" + id);
        model.addAttribute("title", "Editar usuário");
        return "users/form";
    }

    // UPDATE - salvar
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("user") UserForm form,
                         BindingResult result, ModelMap model, RedirectAttributes ra) {
        if (!result.hasFieldErrors("cpf") && service.cpfInUse(form.getCpf(), id)) {
            result.rejectValue("cpf", "duplicate", "CPF já cadastrado");
        }
        if (result.hasErrors()) {
            model.addAttribute("action", "/users/" + id);
            model.addAttribute("title", "Editar usuário");
            return "users/form";
        }
        if (service.update(id, form).isEmpty()) {
            return notFound(ra);
        }
        ra.addFlashAttribute("message", "Usuário atualizado com sucesso!");
        return REDIRECT_INDEX;
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        if (service.delete(id)) {
            ra.addFlashAttribute("message", "Usuário excluído com sucesso!");
        } else {
            ra.addFlashAttribute("error", "Usuário não encontrado.");
        }
        return REDIRECT_INDEX;
    }

    private String notFound(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Usuário não encontrado.");
        return REDIRECT_INDEX;
    }
}
