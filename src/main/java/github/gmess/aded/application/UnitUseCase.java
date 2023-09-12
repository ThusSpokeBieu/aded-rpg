package github.gmess.aded.application;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN anIn);
}