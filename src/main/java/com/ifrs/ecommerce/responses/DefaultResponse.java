package com.ifrs.ecommerce.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class DefaultResponse {
    private Object data;
    private String message;
    private String statusCode;

    /**
     * Gera um response padrão status OK com o objeto informado.
     * @param data Objeto de resposta.
     * @return Response entity padronizada.
     */
    public static ResponseEntity<DefaultResponse> build(Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new DefaultResponse(
                        data,
                        HttpStatus.OK.getReasonPhrase(),
                        String.valueOf(HttpStatus.OK.value()))
        );
    }
    /**
     * Gera um response padrão status OK com o objeto e mensagem informados.
     * @param data Objeto de resposta.
     * @param message Mensagem da resposta.
     * @return Response entity padronizada.
     */
    public static ResponseEntity<DefaultResponse> build(Object data, String message) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new DefaultResponse(
                        data,
                        message,
                        String.valueOf(HttpStatus.OK.value()))
        );
    }

    /**
     * Gera um response padrão com o objeto e um statusCode informado.
     * A mensagem será o padrão do statusCode.
     * @param data Objeto resposta.
     * @param statusCode Status code.
     * @return Response entity padronizada.
     */
    public static ResponseEntity<DefaultResponse> build(Object data, HttpStatus statusCode) {
        return ResponseEntity.status(statusCode).body(
                new DefaultResponse(
                        data,
                        statusCode.getReasonPhrase(),
                        String.valueOf(statusCode.value()))
        );
    }

    /**
     * Gera um response padronizado com o objeto, mensagem e statusCode informados.
     * @param data Objeto resposta.
     * @param message Mensagem da resposta.
     * @param statusCode Status code.
     * @return Response entity padronizada.
     */
    public static ResponseEntity<DefaultResponse> build(Object data, String message, HttpStatus statusCode) {
        return ResponseEntity.status(statusCode).body(
                new DefaultResponse(
                        data,
                        message,
                        String.valueOf(statusCode.value()))
        );
    }
}
