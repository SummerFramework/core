package com.github.summerframework.core.data;

import com.github.summerframework.core.model.DomainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by renan on 11/01/16.
 */
public class BaseJpaRepositoryImp<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T,ID> {

    private final JpaEntityInformation entityInformation;

    private final EntityManager entityManager;

    public BaseJpaRepositoryImp(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    public T findByUUID(String uuid) {
        if ( uuid == null){
            return null;
        }

        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final Class javaType = entityInformation.getJavaType();
        final CriteriaQuery<T> criteriaQuery = builder.createQuery(javaType);
        final Root<T> root = criteriaQuery.from(javaType);
        criteriaQuery.select(root);
        criteriaQuery.where( builder.equal(root.get(DomainEntity.UUID),uuid));
        final TypedQuery<T> query = this.entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public List<T> findActives() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final Class javaType = entityInformation.getJavaType();
        final CriteriaQuery<T> criteriaQuery = builder.createQuery(javaType);
        final Root<T> root = criteriaQuery.from(javaType);
        criteriaQuery.select(root);
        criteriaQuery.where( builder.equal(root.get(DomainEntity.ACTIVE), true));
        return this.entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Page<T> findPageableActives(Pageable pageable) {

        String select = "" +
                " SELECT * FROM " +
                " " + this.entityInformation.getEntityName() + " " +
                " WHERE active=1 ";

        String count = "" +
                " SELECT COUNT(id) FROM " +
                " " + this.entityInformation.getEntityName() + " " +
                " WHERE active=1";


        Long total = getQueryCountEntity(count);
        if (total == null) return null;

        Query query = this.entityManager.createNativeQuery(select, getDomainClass());
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<T>(query.getResultList(), pageable,total);
    }

    private Long getQueryCountEntity(String query) {

        Query queryCount = this.entityManager.createNativeQuery(query);
        List resultList = queryCount.getResultList();

        if ( resultList.isEmpty() == true ){
            return null;
        }

        Long total = ((BigInteger)resultList.get(0)).longValue();
        return total;
    }

    @Autowired
    public List<T> findByQuery(Map<String, String> queryParameters){

        String select = "" +
                " SELECT * FROM " +
                " " + this.entityInformation.getEntityName() + " " +
                " WHERE 1=1 ";


        if ( queryParameters != null ){
            Iterator<Map.Entry<String, String>> iterator = queryParameters.entrySet().iterator();
            while ( iterator.hasNext()){
                Map.Entry<String, String> item = iterator.next();
                String and = " AND  " + item.getKey() + "=";
                try{
                    long value = Long.parseLong( item.getValue().toString() );
                    and += value + " ";
                }catch (NumberFormatException e){
                    if ("true".equals(item.getValue()) || "false".equals(item.getValue())){
                        and += "" + (Boolean.valueOf(item.getValue()) == Boolean.TRUE ? 1 : 0) + " ";
                    }else{
                        and += "'" + item.getValue().toString() + "' ";
                    }
                }
                select += and;
            }
        }

        return this.entityManager.createNativeQuery(select, getDomainClass()).getResultList();
    }

    @Autowired
    public Page<T> findByQuery(Map<String, String> queryParameters, Pageable pageable){

        String select = "" +
                " SELECT * FROM " +
                " " + this.entityInformation.getEntityName() + " " +
                " WHERE 1=1 ";

        String count = "" +
                " SELECT COUNT(id) FROM " +
                " " + this.entityInformation.getEntityName() + " " +
                " WHERE 1=1 ";

        if ( queryParameters != null ){
            Iterator<Map.Entry<String, String>> iterator = queryParameters.entrySet().iterator();
            while ( iterator.hasNext()){
                Map.Entry<String, String> item = iterator.next();
                String and = " AND  " + item.getKey() + "=";
                try{
                    long value = Long.parseLong( item.getValue().toString() );
                    and += value + " ";
                }catch (NumberFormatException e){
                    if ("true".equals(item.getValue()) || "false".equals(item.getValue())){
                        and += "" + (Boolean.valueOf(item.getValue()) == Boolean.TRUE ? 1 : 0) + " ";
                    }else{
                        and += "'" + item.getValue().toString() + "' ";
                    }
                }
                select += and;
                count  += and;
            }
        }

        Long total = getQueryCountEntity(count);
        if (total == null) return null;

        if ( pageable.getSort() != null){

            String orderBy = " ORDER BY ";
            Iterator<Sort.Order> iterator = pageable.getSort().iterator();
            Sort.Order order = null;
            while (iterator.hasNext()){
                order =  iterator.next();
                orderBy += order.getProperty() + ", ";
            }

            orderBy = orderBy.substring(0, orderBy.lastIndexOf(","));
            orderBy += (order.isAscending() ? " ASC" : " DESC");
            select += orderBy;
        }

        Query query = this.entityManager.createNativeQuery(select, getDomainClass());
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<T>(query.getResultList(), pageable,total);
    }

    public Object getDeclaredType(){
        try {
            return this.entityInformation.getJavaType().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
