package com.application.travo.specifications;

import com.application.travo.Entity.GuideEntity;
import com.application.travo.dtos.GuideFilterRequest;
import org.springframework.data.jpa.domain.Specification;

public class GuideSpecification {

    public static Specification<GuideEntity> getGuides(GuideFilterRequest filter) {

        return (root, query, cb) -> {

            var predicates = cb.conjunction();

            if (filter.getBaseLocation() != null) {
                predicates.getExpressions().add(
                        cb.equal(root.get("baseLocation"), filter.getBaseLocation())
                );
            }

            if (filter.getExperienceYears() != null) {
                predicates.getExpressions().add(
                        cb.greaterThanOrEqualTo(root.get("experienceYears"), filter.getExperienceYears())
                );
            }

            // JSON search (Postgres)
            // Languages filter
            if (filter.getSelectedLanguages() != null && !filter.getSelectedLanguages().isEmpty()) {

                for (String lang : filter.getSelectedLanguages()) {
                    predicates.getExpressions().add(
                            cb.like(
                                    root.get("languages").as(String.class),   // 👈 important
                                    "%" + lang + "%"
                            )
                    );
                }
            }

            if (filter.getExpertise() != null && !filter.getExpertise().isEmpty()) {

                for (String tag : filter.getExpertise()) {
                    predicates.getExpressions().add(
                            cb.like(
                                    root.get("expertise_tags").as(String.class),
                                    "%" + tag + "%"
                            )
                    );
                }
            }

            return predicates;
        };
    }
}