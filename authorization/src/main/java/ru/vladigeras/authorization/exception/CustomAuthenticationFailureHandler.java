package ru.vladigeras.authorization.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import ru.vladigeras.authorization.model.HttpResponseTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author vladi_geras on 08/11/2018
 */
@Configuration
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
		String message;

		if (ex.getMessage().toLowerCase().equals("bad credentials")) {
			ex.printStackTrace();
			message = "Неверные данные учетной записи";
		} else {
			message = ex.getMessage();
		}

		var apiError = new HttpResponseTemplate(HttpStatus.FORBIDDEN.value(), message);

		var out = response.getOutputStream();
		var mapper = new ObjectMapper();
		mapper.writeValue(out, apiError);
		out.flush();
	}
}