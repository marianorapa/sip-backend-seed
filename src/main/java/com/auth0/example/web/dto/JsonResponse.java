package com.auth0.example.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResponse<E> {

    private E data;

    private List<String> errors;

    public void addError(String error) {
        if (errors == null){
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
    }
}
