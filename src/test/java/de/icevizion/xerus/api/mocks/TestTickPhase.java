package de.icevizion.xerus.api.mocks;

import de.icevizion.xerus.api.phase.TickedPhase;

public class TestTickPhase extends TickedPhase {

    public TestTickPhase(String name) {
        super(name);
    }

    @Override
    public void onStart() {
        throw new RuntimeException("On start");
    }

    @Override
    public void onUpdate() {
        throw new RuntimeException("On update");
    }
}
