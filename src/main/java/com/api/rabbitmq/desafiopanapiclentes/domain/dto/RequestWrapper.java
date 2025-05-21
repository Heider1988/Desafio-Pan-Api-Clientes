package com.api.rabbitmq.desafiopanapiclentes.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestWrapper<T> {

    @NotNull(message = "O campo detail é obrigatório")
    private Detail<T> detail;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail<T> {
        @Valid
        @NotNull(message = "O campo data é obrigatório")
        private T data;
    }

    public static <T> RequestWrapper<T> of(T data) {
        Detail<T> detail = new Detail<>();
        detail.setData(data);
        RequestWrapper<T> wrapper = new RequestWrapper<>();
        wrapper.setDetail(detail);
        return wrapper;
    }
}