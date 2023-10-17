package br.com.andrelima.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.andrelima.todolist.users.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {
            // Pagar a autenticação (usuario e senha)
            var authorization = request.getHeader("Authorization");
            // System.out.println("Authorization");
            // System.out.println(authorization);

            var userPasswordAuthDecode = authorization.substring("Basic".length()).trim();
            // System.out.println("Authorization");
            // System.out.println(userPasswordAuthDecode);

            byte[] AuthDecode = Base64.getDecoder().decode(userPasswordAuthDecode);
            // System.out.println("Authorization");
            // System.out.println(AuthDecode);

            var authString = new String(AuthDecode);
            // System.out.println("Authorization");
            // System.out.println(authString);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            System.out.println("Authorization");
            System.out.println(username);
            System.out.println(password);

            // Validar usuario
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Usuário sem autorização");
            } else {
                // Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (passwordVerify.verified) {
                    // Segue o caminho...
                    request.setAttribute("idUser", user.getId());
                    System.out.println("Chegou no filtro");
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Usuário sem autorização");
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }
}
