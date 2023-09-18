package github.gmess.aded.application.battles.pve.start;

import github.gmess.aded.domain.aggregates.battles.Battle;

public record StartPveBattleOutput(
    String id,
    String battleCode,
    String contender,
    String contenderClass,
    int contenderHp,
    String contested,
    String contestedClass,
    int contestedHp,
    int round,
    String turn) {
  public static StartPveBattleOutput from(final Battle battle) {
    return new StartPveBattleOutput(
        battle.getId().getValue(),
        battle.getCode().getValue(),
        battle.getContender(),
        battle.getContenderCharacter().getCharacterClass(),
        battle.getContenderCurrentHp().getValue(),
        battle.getContested(),
        battle.getContestedCharacter().getCharacterClass(),
        battle.getContestedCurrentHp().getValue(),
        battle.getRound().get(),
        battle.getTurn().name());
  }
}
