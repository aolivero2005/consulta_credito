package aom.credito.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CreditoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(CreditoNotFoundException creditoNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(creditoNotFoundException.getMessage(), HttpStatus.NOT_FOUND));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        //Coletar erros de validação
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();

        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);
            errorMessage.append(fieldError.getDefaultMessage());

            //Adicione um ponto e vírgula somente se não for o último erro
            if (i < fieldErrors.size() - 1) {
                errorMessage.append("; ");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(errorMessage.toString(), HttpStatus.BAD_REQUEST));
    }

    private ErrorResponse createErrorResponse(String message, HttpStatus httpStatus) {
        return new ErrorResponse(message, httpStatus.value());
    }

}
