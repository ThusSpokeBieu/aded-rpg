package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CharacterID extends Identifier {

    private final String value;

    private CharacterID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CharacterID unique() {
        return CharacterID.from(UUID.randomUUID());
    }

    public static CharacterID from(final String anId) {
        return new CharacterID(anId);
    }

    public static CharacterID from(final UUID anId) {
        return new CharacterID(anId.toString().toLowerCase());
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CharacterID that = (CharacterID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
