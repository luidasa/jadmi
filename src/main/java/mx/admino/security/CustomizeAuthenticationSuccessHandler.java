package mx.admino.security;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	        HttpServletResponse response, Authentication authentication)
	        throws IOException, ServletException {
	    //set our response to OK status
	    response.setStatus(HttpServletResponse.SC_OK);
	    System.out.print(authentication.getName());

	    for (GrantedAuthority auth : authentication.getAuthorities()) {
	        if ("ADMIN".equals(auth.getAuthority())) {
	            response.sendRedirect("/panel");
	        }
	    }
	}
}
