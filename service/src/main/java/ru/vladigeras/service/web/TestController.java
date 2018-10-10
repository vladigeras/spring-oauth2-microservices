package ru.vladigeras.service.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vladigeras.service.model.HttpResponseTemplate;
import ru.vladigeras.service.util.AuthenticationUtil;

import java.util.Optional;

/**
 * @author vladi_geras on 09/10/2018
 */
@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/test")
	public HttpResponseTemplate<Long> hello(Authentication authentication) {
		Optional<Long> optPrincipalId = AuthenticationUtil.getUserIdFromAuthentication(authentication);

		return optPrincipalId
				.map(principalId -> new HttpResponseTemplate<>(HttpStatus.OK.value(), principalId))
				.orElseGet(() -> new HttpResponseTemplate<>(false, HttpStatus.FORBIDDEN.value(), "Доступ запрещен", null));
	}
}
