package github.gmess.aded.domain.exceptions;

import github.gmess.aded.domain.AggregateRoot;
import github.gmess.aded.domain.Identifier;
import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterID;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );

        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final String query
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                query
        );

        return new NotFoundException(anError, Collections.emptyList());
    }

    public static Error withNameOrId(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final String nameOrId
    ) {
        final var anError = "%s with ID or Name %s was not found".formatted(
                anAggregate.getSimpleName(),
                nameOrId
        );

        return new Error(anError);
    }

    public static Supplier<NotFoundException> notFoundWith(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final String param) {
        return () -> NotFoundException.with(anAggregate, param);
    }

    public static Supplier<NotFoundException> notFoundWith(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier param) {
        return () -> NotFoundException.with(anAggregate, param);
    }

}