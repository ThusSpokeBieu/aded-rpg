package github.gmess.aded.domain.exceptions;

import github.gmess.aded.domain.AggregateRoot;
import github.gmess.aded.domain.Identifier;

import java.util.Collections;
import java.util.List;

public class ForbiddenException extends DomainException {
    protected ForbiddenException(String aMessage, List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static ForbiddenException with(
            final String anError
    ) {
        return new ForbiddenException(anError, Collections.emptyList());
    }

}
