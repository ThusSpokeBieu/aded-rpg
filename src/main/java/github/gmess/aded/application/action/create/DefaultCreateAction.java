package github.gmess.aded.application.action.create;

import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.actions.ActionGateway;

import java.util.Objects;

public class DefaultCreateAction extends CreateActionUseCase {

  private final ActionGateway actionGateway;

  public DefaultCreateAction(final ActionGateway actionGateway) {
    this.actionGateway = Objects.requireNonNull(actionGateway);
  }

  @Override
  public Action execute(Action input) {
    return actionGateway.create(input);
  }
}
