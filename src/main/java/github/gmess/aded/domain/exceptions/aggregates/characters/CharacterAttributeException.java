package github.gmess.aded.domain.exceptions.aggregates.characters;

import github.gmess.aded.domain.exceptions.NoStacktraceException;

public class CharacterAttributeException extends NoStacktraceException {
    public CharacterAttributeException(String aMessage) {
        super(aMessage);
    }

}
