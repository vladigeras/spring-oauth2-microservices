package ru.vladigeras.service.model;

/**
 * @author vladi_geras on 09/10/2018
 */
public class HttpResponseTemplate<T> {

	public Boolean success = true;
	public Integer code;
	public String message;
	public T body;

	public HttpResponseTemplate(Boolean success, Integer code, String message, T body) {
		this.success = success;
		this.code = code;
		this.message = message;
		this.body = body;
	}

	public HttpResponseTemplate(Integer code, T body) {
		this.code = code;
		this.body = body;
		this.message = null;
	}
}
