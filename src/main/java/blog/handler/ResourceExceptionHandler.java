package blog.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import blog.payload.response.MessageResponse;

@ControllerAdvice

public class ResourceExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<MessageResponse> handleBookNotFoundException(AuthenticationException e,
			HttpServletRequest request) {

		MessageResponse msg = new MessageResponse("Usuário inexistente e/ou senha incorreta.");
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);
	}

}
