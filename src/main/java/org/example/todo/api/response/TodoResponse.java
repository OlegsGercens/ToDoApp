package org.example.todo.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponse<T> {

    private String error;

    private List<T> t;

    public TodoResponse() {
        //
    }

    public TodoResponse(String error, List<T> t) {
        this.error = error;
        this.t = t;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setT(List<T> t) {
        this.t = t;
    }

    public String getError() {
        return error;
    }

    public List<T> getT() {
        return t;
    }

    public static  <T> TodoResponse<T> success (List<T> t){
        TodoResponse<T> response = new TodoResponse<>();
        response.setT(t);
        return response;
    }

    public static TodoResponse error (String error) {
        TodoResponse<String> response = new TodoResponse<>();
        response.setError(error);
        return response;
    }
}
