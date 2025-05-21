package com.api.desafiopanapiclentes.infrastructure.response;

import com.api.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseWrapper<T> {

    @Builder.Default
    private Detail<T> detail = new Detail<>();

    @Builder.Default
    private List<String> erros = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail<T> {
        private T data;
    }

    public static <T> ApiResponseWrapper<T> success(T data) {
        Detail<T> detail = new Detail<>();
        detail.setData(data);

        return ApiResponseWrapper.<T>builder()
                .detail(detail)
                .build();
    }

    public static <T> ApiResponseWrapper<T> error(List<String> errors) {
        return ApiResponseWrapper.<T>builder()
                .erros(errors)
                .build();
    }

    public static <T> ApiResponseWrapper<T> error(String resourceName, String fieldName, Object fieldValue) {
        return error(new ResourceNotFoundException(resourceName, fieldName, fieldValue));
    }

    public static <T> ApiResponseWrapper<T> error(ResourceNotFoundException ex) {
        return error(ex.getMessage());
    }

    public static <T> ApiResponseWrapper<T> error(String errorMessage) {
        List<String> errors = new ArrayList<>();
        errors.add(errorMessage);

        return error(errors);
    }
}
