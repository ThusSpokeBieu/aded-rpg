package github.gmess.aded.domain.exceptions.system;

import github.gmess.aded.domain.exceptions.NoStacktraceException;

public class DiceException extends NoStacktraceException {
    public DiceException(String aMessage) {
        super(aMessage);
    }

}
