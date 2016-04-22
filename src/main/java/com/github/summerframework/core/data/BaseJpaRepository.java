package com.github.summerframework.core.data;

/**
 * Created by renan on 11/01/16.
 */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    T findByUUID(String uuid);

    List<T> findActives();

    Page<T> findPageableActives(Pageable pageable);

    Page<T> findByQuery(Map<String, String> queryParameters, Pageable pageable);

    List<T> findByQuery(Map<String, String> queryParameters);

    Object getDeclaredType();

}
