package com.raminq.security.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse<T> {

    private List<T> items;

    private long totalCount;

    private int pageSize;

    private int pageNumber;

}
