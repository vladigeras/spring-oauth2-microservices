package ru.vladigeras.authorization.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Класс Http ответов сервера на запросы
 *
 * @author vladi_geras on 09/10/2018
 */
public class HttpResponseTemplate<T> {

	public String timestamp = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

	public Boolean success;

	public Integer code;

	public String message;

	public T body;

	/**
	 * Конструктор для запросов, завершившихся ошибками
	 *
	 * @param code    HTTP код ошибки
	 * @param message сообщение ошибки
	 */
	public HttpResponseTemplate(Integer code, String message) {
		this.code = code;
		this.message = message;
		this.success = false;
	}

	/**
	 * Конструктор для запросов, завершившихся успешно
	 *
	 * @param code HTTP код успешного действия
	 * @param body тело ответа
	 */
	public HttpResponseTemplate(Integer code, T body) {
		this.code = code;
		this.body = body;
		this.success = true;
	}

	/**
	 * Конструктор для запросов, завершившихся успешно, не требующих результата в теле
	 *
	 * @param code HTTP код успешного действия
	 */
	public HttpResponseTemplate(Integer code) {
		this.code = code;
		this.success = true;
	}
}
