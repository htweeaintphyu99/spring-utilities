package com.tutorial.criteria.service.specification;

import com.tutorial.criteria.entity.Item;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;


public class ItemSpecification {

    public static Specification<Item> getItemByName(String name) {
        return new Specification<Item>() {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.<String>get("name"), name);
            }
        };
    }

    public static Specification<Item> getItemBySkuOrName(String sku, String name) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<Predicate>();
                if(sku != null) {
                    predicates.add(criteriaBuilder.equal(root.get("sku"), sku));
                }
                if(name != null) {
                    predicates.add(criteriaBuilder.equal(root.get("name"), name));
                }
                Predicate or_predicate = criteriaBuilder.or(predicates.toArray(new Predicate[] {}));
//                query.where(or_predicate);
                return or_predicate;
            }
        };
    }

    public static Specification<Item> getItemBySkuAndName(String sku, String name) {
        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<Predicate>();
                if(sku != null) {
                    predicates.add(criteriaBuilder.equal(root.get("sku"), sku));
                }
                if(name != null) {
                    predicates.add(criteriaBuilder.equal(root.get("name"), name));
                }
                Predicate or_predicate = criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
//                query.where(or_predicate);
                return or_predicate;
            }
        };
    }

    public static Specification<Item> getItemByNameLike(String keyword) {
//        return ((root, query, criteriaBuilder) -> {
//            return criteriaBuilder.like(root.get("name"), keyword);
//        });

        return new Specification<Item>() {
            @Override
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%"+keyword+"%");

            }
        };
    }

    public static Specification<Item> getItemNotNameLike(String keyword) {

        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.not(
                    criteriaBuilder.like(root.get("name"), "%"+keyword+"%")
            );
        });
    }

    public static Specification<Item> getItemInName(List<String> names) {
        return ((root, query, criteriaBuilder) ->  {
            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("name"));
            for(String name: names) {
                inClause.value(name);
            }
            return inClause;
        });
    }

}
