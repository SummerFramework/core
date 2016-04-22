package com.github.summerframework.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by renan on 27/02/16.
 */
public class DomainPageImpl<T> extends PageImpl<T>{

    public DomainPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public DomainPageImpl(List<T> content) {
        super(content);
    }

    public DomainPageImpl(Page<T> page, Pageable pageable) {
        super(page.getContent(), pageable, page.getTotalPages());
    }

    @JsonProperty("number_of_elements")
    @Override
    public int getNumberOfElements() {
        return super.getNumberOfElements();
    }

    @JsonProperty("total_pages")
    @Override
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @JsonProperty("total_elements")
    @Override
    public long getTotalElements() {
        return super.getTotalElements();
    }
}
