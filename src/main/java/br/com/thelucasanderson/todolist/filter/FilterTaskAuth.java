package br.com.thelucasanderson.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.thelucasanderson.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/")) {
            var authorization = request.getHeader("Authorization");

            var auth_encoded = authorization.substring("Basic".length()).trim();
            var auth_decoded = Base64.getDecoder().decode(auth_encoded);
            var auth_string = new String(auth_decoded);
            var credential = auth_string.split(":");

            String userEmail = credential[0];
            String userPassword = credential[1];

            var user = this.userRepository.findByEmail(userEmail);

            if(user == null) {
                response.sendError(401);
            } else {
                var passwordVerify = BCrypt.verifyer().verify(userPassword.toCharArray(), user.getPassword());
                if(passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
            System.out.println(userPassword);
            System.out.println(auth_string);
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
