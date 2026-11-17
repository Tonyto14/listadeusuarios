package com.lunaltas.ListaUsuarios.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * O ActiveJDBC trabalha com uma conexão por thread (Base.open / Base.close).
 * Este filtro abre a conexão no início da requisição e fecha no final,
 * cobrindo controller, service e renderização do Thymeleaf.
 */
@Component
public class DbConnectionFilter extends OncePerRequestFilter {

    @Value("${app.db.driver}")
    private String driver;
    @Value("${app.db.url}")
    private String url;
    @Value("${app.db.user}")
    private String user;
    @Value("${app.db.password}")
    private String password;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/css/") || path.startsWith("/js/") || path.equals("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        boolean opened = false;
        try {
            if (!Base.hasConnection()) {
                Base.open(driver, url, user, password);
                opened = true;
            }
            chain.doFilter(request, response);
        } finally {
            if (opened) {
                Base.close();
            }
        }
    }
}
