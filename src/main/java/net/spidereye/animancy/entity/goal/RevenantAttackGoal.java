package net.spidereye.animancy.entity.goal;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.spidereye.animancy.entity.custom.DregZombieEntity;
import net.spidereye.animancy.entity.custom.RevenantEntity;

public class RevenantAttackGoal extends MeleeAttackGoal {
    private final RevenantEntity revenant;
    private int ticks;

    public RevenantAttackGoal(RevenantEntity revenant, double speed, boolean pauseWhenMobIdle) {
        super(revenant, speed, pauseWhenMobIdle);
        this.revenant = revenant;
    }

    public void start() {
        super.start();
        this.ticks = 0;
    }

    public void stop() {
        super.stop();
        this.revenant.setAttacking(true);
    }

    public void tick() {
        super.tick();
        this.ticks++;
        if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
            this.revenant.setAttacking(true);
        } else {
            this.revenant.setAttacking(false);
        }
    }
}
