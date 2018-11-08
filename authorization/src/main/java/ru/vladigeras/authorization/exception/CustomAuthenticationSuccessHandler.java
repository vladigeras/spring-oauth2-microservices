package ru.vladigeras.authorization.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.vladigeras.authorization.model.HttpResponseTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author vladi_geras on 08/11/2018
 */
@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		var apiResponse = new HttpResponseTemplate(HttpStatus.OK.value());

		var out = response.getOutputStream();
		var mapper = new ObjectMapper();
		mapper.writeValue(out, apiResponse);
		out.flush();
	}
}