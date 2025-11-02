package core.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import core.pojo.Core;

@RestControllerAdvice
public class ExceptionAdvice {
	private static final Logger logger = LogManager.getLogger(ExceptionAdvice.class);

	@ExceptionHandler(Exception.class)
	public Core handleException(Exception e) {
		logger.error(e.getMessage(), e);
		Core core = new Core();
		core.setSuccessful(false);
		core.setMessage(e.getMessage());
		return core;
	}
}
