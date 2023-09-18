package github.gmess.aded.domain.exceptions.system;

import github.gmess.aded.domain.exceptions.DomainException;
import github.gmess.aded.domain.exceptions.Error;

import java.util.List;

public class DiceException extends DomainException {
  public DiceException(String aMessage, List<Error> errors) {
    super(aMessage, errors);
  }

}
