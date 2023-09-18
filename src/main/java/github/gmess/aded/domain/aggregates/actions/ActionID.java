package github.gmess.aded.domain.aggregates.actions;

import github.gmess.aded.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public final class ActionID extends Identifier {

  private final String value;

  private ActionID(final String value) {
    Objects.requireNonNull(value);
    this.value = value;
  }

  public static ActionID unique() {
    return ActionID.from(UUID.randomUUID());
  }

  public static ActionID from(final String anId) {
    return new ActionID(anId);
  }

  public static ActionID from(final UUID anId) {
    return new ActionID(anId.toString().toLowerCase());
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
    final ActionID that = (ActionID) o;
    return getValue().equals(that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue());
  }
}
