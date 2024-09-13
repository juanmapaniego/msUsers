package com.globallogic.jmpaniego.msusers.exception;

import com.globallogic.jmpaniego.msusers.model.dto.ErrorDTO;
import com.globallogic.jmpaniego.msusers.model.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({InvalidTokenException.class, UserException.class})
    public ResponseEntity<ErrorResponseDTO> handleUserException(RuntimeException e){
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(
                        Collections.singletonList(ErrorDTO.builder().code(HttpStatus.CONFLICT.value()).timestamp(LocalDateTime.now()).detail(e.getMessage()).build())
                ).build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<ErrorDTO> errors = e.getAllErrors().stream()
                .map(error -> {
                        return ErrorDTO.builder()
                            .code(HttpStatus.CONFLICT.value())
                            .timestamp(LocalDateTime.now())
                            .detail(error.getDefaultMessage())
                            .build();
                    }
                )
                .collect(Collectors.toList());

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(
                        errors
                ).build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingRequestHeaderException(MissingRequestHeaderException e){
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(
                        Collections.singletonList(ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now()).detail(e.getMessage()).build())
                ).build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
