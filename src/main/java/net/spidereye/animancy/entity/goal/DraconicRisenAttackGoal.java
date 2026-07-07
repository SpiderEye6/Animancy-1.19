package net.spidereye.animancy.entity.goal;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.spidereye.animancy.entity.custom.DraconicRisenEntity;
import net.spidereye.animancy.entity.custom.RevenantEntity;

public class DraconicRisenAttackGoal extends MeleeAttackGoal {
    private final DraconicRisenEntity risen;
    private int ticks;

    public DraconicRisenAttackGoal(DraconicRisenEntity risen, double speed, boolean pauseWhenMobIdle) {
        super(risen, speed, pauseWhenMobIdle);
        this.risen = risen;
    }

    public void start() {
        super.start();
        this.ticks = 0;
    }

    public void stop() {
        super.stop();
        this.risen.setAttacking(true);
    }

    public void tick() {
        super.tick();
        this.ticks++;
        if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
            this.risen.setAttacking(true);
        } else {
            this.risen.setAttacking(false);
        }
    }
}
