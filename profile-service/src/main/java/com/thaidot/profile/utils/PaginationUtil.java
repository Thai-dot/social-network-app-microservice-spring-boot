package com.thaidot.profile.utils;

import com.thaidot.profile.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginationUtil<T> {

    public static Pageable getPageable(int page, int size) {
        return PageRequest.of(page-1, size);
    }

    public static Pageable getPageableWithSort(int page, int size, String sortBy, boolean isDesc) {
        Sort sort = isDesc ? Sort.by(Sort.Order.desc(sortBy)) : Sort.by(Sort.Order.asc(sortBy));
        return PageRequest.of(page-1, size, sort);
    }

    public PageResponse<T> getPageResponse(Page<T> page, List<T> data) {
        return PageResponse.<T>builder()
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() +1)
                .data(data)
                .build();
    }
}
