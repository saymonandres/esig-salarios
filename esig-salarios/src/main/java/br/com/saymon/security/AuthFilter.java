package br.com.saymon.security;

import br.com.saymon.view.AuthView;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Inject
    private AuthView auth;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse s = (HttpServletResponse) res;

        String path = r.getRequestURI();
        boolean recursoEstatico = path.contains("/javax.faces.resource/");
        boolean paginaLogin = path.endsWith("login.xhtml");

        if (recursoEstatico || paginaLogin) {
            chain.doFilter(req, res);
            return;
        }

        if (auth != null && auth.isLogado()) {
            chain.doFilter(req, res);
        } else {
            s.sendRedirect(r.getContextPath() + "/login.xhtml");
        }
    }
}
