package github.gmess.aded.domain.aggregates.characters.vo.attributes;

import github.gmess.aded.domain.exceptions.aggregates.characters.CharacterAttributeException;

public class Defense extends CharacterAttribute {
  protected Defense(int aValue) {
    super(aValue);
  }

  public static Defense from(final int value) {
    return new Defense(value);
  }

  public void validate() {
    if (value < 0) {
      throw new CharacterAttributeException("Defense - Defense of an character must be 0 or greater.");
    }

    if (value > 10) {
      throw new CharacterAttributeException("Defense - Defense of an character must not be greater than 10.");
    }
  }
}
