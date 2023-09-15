package github.gmess.aded.infrastructure.utils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) -> cb.like(cb.upper(root.get(prop)), like(term.toUpperCase()));
    }

    private static String like(final String term) {
        return "%" + term + "%";
    }

    public static <T, J> Specification<T> joinLike(final String joinProp, final String pop, final String term) {
        return (root, query, cb) -> {
            Join<T, J> join = root.join(joinProp);
            Predicate predicate = cb.like(cb.upper(join.get(pop)), like(term.toUpperCase()));
            return predicate;
        };
    }

}