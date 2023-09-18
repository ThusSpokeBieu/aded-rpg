package github.gmess.aded.domain.aggregates.characters.vo.attributes;

import github.gmess.aded.domain.ValueObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterAttribute extends ValueObject {
  protected int value;

  protected CharacterAttribute(final int aValue) {
    value = aValue;
  }

}
