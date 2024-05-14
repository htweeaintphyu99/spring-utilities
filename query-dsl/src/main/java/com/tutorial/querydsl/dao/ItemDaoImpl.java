package com.tutorial.querydsl.dao;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tutorial.querydsl.model.entity.Item;
import com.tutorial.querydsl.model.entity.QItem;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao{

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    @PostConstruct
    public void init()
    {
        this.jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
    }

    @Override
    public List<Item> getItemsGreaterThanPrice(BigDecimal price) {
        final JPAQuery<Item> query = new JPAQuery<>(entityManager);
        final QItem item = QItem.item;
        return query.from(item)
                .where(item.price.gt(price))
                .fetch();
    }

    @Override
    public List<Item> getItemBySku(Long sku) {
        final QItem item = QItem.item;
        return (List<Item>) jpaQueryFactory.from(item)
                .where(item.sku.eq(sku))
                .fetch();
    }
}
