package com.raminq.security.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingModel {

    @Min(value = 1, message = "Page number must start with page 1")
    private int pageNumber = 1;

    @Min(value = 1, message = "You can request minimum 1 records")
    @Max(value = 100, message = "You can request maximum 100 records")
    private int pageSize = 10;

    private Sort.Direction sortDirection = Sort.Direction.ASC;

    @NotBlank
    private String sortBy;

    public Pageable toPageable() {
        return PageRequest.of(
                this.pageNumber - 1, this.pageSize,
                Sort.by(this.sortDirection, this.getSortBy()));
    }

}
