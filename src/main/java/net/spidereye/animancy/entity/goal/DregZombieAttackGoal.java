package net.spidereye.animancy.entity.goal;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.spidereye.animancy.entity.custom.DregZombieEntity;

public class DregZombieAttackGoal extends MeleeAttackGoal {
    private final DregZombieEntity dreg;
    private int ticks;

    public DregZombieAttackGoal(DregZombieEntity dreg, double speed, boolean pauseWhenMobIdle) {
        super(dreg, speed, pauseWhenMobIdle);
        this.dreg = dreg;
    }

    public void start() {
        super.start();
        this.ticks = 0;
    }

    public void stop() {
        super.stop();
        this.dreg.setAttacking(true);
    }

    public void tick() {
        super.tick();
        this.ticks++;
        if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
            this.dreg.setAttacking(true);
        } else {
            this.dreg.setAttacking(false);
        }
    }
}
