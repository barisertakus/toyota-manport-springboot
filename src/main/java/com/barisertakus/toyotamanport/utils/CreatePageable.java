package com.barisertakus.toyotamanport.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class CreatePageable {
    public static Pageable create(int pageNo, int pageSize, String sortType, String sortField) {
        Sort sort = null;
        boolean sortFieldEmpty = sortField == null || sortField.isEmpty();
        if ("asc".equals(sortType) && !sortFieldEmpty) {
            sort = Sort.by(Sort.Direction.ASC, sortField);
        } else if ("desc".equals(sortType) && !sortFieldEmpty)
            sort = Sort.by(Sort.Direction.DESC, sortField);

        Pageable pageable;

        if (!Objects.isNull(sort)) {
            pageable = PageRequest.of(pageNo, pageSize, sort);
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }

        return pageable;
    }
}