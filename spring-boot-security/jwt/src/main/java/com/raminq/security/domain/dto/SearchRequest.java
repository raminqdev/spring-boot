package com.raminq.security.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest<T> {

    public SearchRequest(T query) {
        this.pagingModel = new PagingModel();
        this.query = query;
    }

    @Valid
    @NotNull
    private PagingModel pagingModel;

    @Valid
    @NotNull
    private T query;

}
