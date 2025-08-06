package com.airportsim.factories;

import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;
import com.airportsim.config.SimulationSettings;
import com.airportsim.manager.GroupForwarderInterface;
import java.util.concurrent.ThreadLocalRandom;

public class GroupGenerator {

    private GroupForwarderInterface forwardTarget;
    private volatile boolean running = true;

    public GroupGenerator() {}

    public void setForwarder(GroupForwarderInterface forwarder) {
        this.forwardTarget = forwarder;
    }

    public void stop() {
        running = false;
    }

    public void startGenerating() {
        while (running) {
            int groupCount = ThreadLocalRandom.current().nextInt(SimulationSettings.min_group_count, SimulationSettings.max_group_count + 1);
            int groupIntervall = ThreadLocalRandom.current().nextInt(SimulationSettings.min_generation_interval, SimulationSettings.max_generation_interval + 1);

            for (int i = 0; i < groupCount; i++) {
                simulateTimeDelay(SimulationSettings.delay_between_groups);
                Group<Passenger> group = GroupFactory.createRandomGroup();
                forwardTarget.forwardGroup(group);
            }
            simulateTimeDelay(groupIntervall);
        }
    }

    private void simulateTimeDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("GroupGenerator: Time delay interrupted.");
        }
    }
}
