package github.gmess.aded.domain.aggregates.battles;

import github.gmess.aded.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public final class BattleID extends Identifier {

  private final String value;

  private BattleID(final String value) {
    Objects.requireNonNull(value);
    this.value = value;
  }

  public static BattleID unique() {
    return BattleID.from(UUID.randomUUID());
  }

  public static BattleID from(final String anId) {
    return new BattleID(anId);
  }

  public static BattleID from(final UUID anId) {
    return new BattleID(anId.toString().toLowerCase());
  }

  @Override
  public String getValue() {
    return value;
  }

  public UUID getUUID() {
    return UUID.fromString(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final BattleID that = (BattleID) o;
    return getValue().equals(that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue());
  }
}
