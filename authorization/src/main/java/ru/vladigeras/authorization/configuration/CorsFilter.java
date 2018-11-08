package ru.vladigeras.authorization.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author vladi_geras on 08/11/2018
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		var response = (HttpServletResponse) res;
		var request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Max-Age", "3600");


		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
}

