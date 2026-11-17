package com.lunaltas.ListaUsuarios.config;

import com.lunaltas.ListaUsuarios.model.User;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Executa na inicialização: cria a tabela (se não existir) e insere dados de exemplo.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${app.db.driver}")
    private String driver;
    @Value("${app.db.url}")
    private String url;
    @Value("${app.db.user}")
    private String user;
    @Value("${app.db.password}")
    private String password;

    @Override
    public void run(String... args) {
        Base.open(driver, url, user, password);
        try {
            Base.exec("""
                    CREATE TABLE IF NOT EXISTS users (
                        id     INTEGER PRIMARY KEY AUTOINCREMENT,
                        name   TEXT NOT NULL,
                        arroba TEXT NOT NULL,
                        cpf    TEXT NOT NULL UNIQUE
                    )
                    """);

            if (User.count() == 0) {
                seed("Maria Silva", "@maria", "11111111111");
                seed("João Souza", "@joao", "22222222222");
                seed("Ana Lima", "@ana", "33333333333");
                System.out.println(">>> Banco inicializado com usuários de exemplo.");
            }
        } finally {
            Base.close();
        }
    }

    private void seed(String name, String arroba, String cpf) {
        User u = new User();
        u.setName(name);
        u.setArroba(arroba);
        u.setCpf(cpf);
        u.saveIt();
    }
}
