package fintech_team1.remittance_server.global.exception;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse errorResponse = new ApiResponse(
                "400",
                "유효성 검증 오류",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException ex) {
        ApiResponse errorResponse = new ApiResponse(
                "400",
                "잘못된 요청입니다.",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        ApiResponse errorResponse = new ApiResponse(
                "400",
                "잘못된 요청입니다.",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ProductAlreadyInWishlistException.class)
    public ResponseEntity<ApiResponse> handleProductAlreadyInWishlistException(ProductAlreadyInWishlistException ex) {
        ApiResponse errorResponse = new ApiResponse(
                "400",
                "잘못된 요청입니다.",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        String errorMessage = String.format("요청 파라미터 '%s' 를 입력해주세요", ex.getParameterName());
        ApiResponse apiResponse = new ApiResponse("400", "Bad Request", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    //    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
//        ApiResponse errorResponse = new ApiResponse(
//                "500",
//                "서버 오류",
//                "서버에 문제가 발생했습니다."
//        );
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }
}
