package net.theevilreaper.xerus.api.mocks;

import net.theevilreaper.xerus.api.phase.TickingPhase;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.TemporalUnit;

public class TestTickingPhase extends TickingPhase {

    public TestTickingPhase(@NotNull String name, @NotNull TemporalUnit temporalUnit, long interval) {
        super(name, temporalUnit, interval);
    }

    public TestTickingPhase(@NotNull String name, @NotNull TemporalUnit temporalUnit) {
        super(name, temporalUnit);
    }

    @Override
    public void onUpdate() {
        throw new RuntimeException("On update");
    }
}
