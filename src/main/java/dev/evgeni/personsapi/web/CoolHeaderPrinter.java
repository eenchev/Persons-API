package dev.evgeni.personsapi.web;

import java.io.IOException;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CoolHeaderPrinter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // if request contains username & pasword
        // check username and password
        // if OK -> add security context
        // if not -> return 401
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getHeader("CoolHeader") != null) {
            System.out.println(httpRequest.getHeader("CoolHeader"));
        }

        chain.doFilter(request, response);
    }
    
}
