package com.example.gadgetsore.specifications;

import com.example.gadgetsore.entity.Smartphone;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SmartphoneSpecifications {

    public static Specification<Smartphone> hasBrand(Set<String> brands, Set<String> models,
                                                     Set<Integer> memoryOptions, Set<Integer> storageOptions,
                                                     Set<String> colors) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (brands != null && brands.iterator().hasNext()) {
                predicates.add(root.get("brand").in(brands));
            }

            if (models != null && models.iterator().hasNext()) {
                predicates.add(root.get("model").in(models));
            }

            if (memoryOptions != null && memoryOptions.iterator().hasNext()) {
                predicates.add(root.get("memory").in(memoryOptions));
            }

            if (storageOptions != null && storageOptions.iterator().hasNext()) {
                predicates.add(root.get("storage").in(storageOptions));
            }

            if (colors != null && colors.iterator().hasNext()) {
                predicates.add(root.get("color").in(colors));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}



