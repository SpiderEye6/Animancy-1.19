package net.spidereye.animancy.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.entity.custom.DregZombieEntity;

public class ModEntities {
    public static final EntityType<DregZombieEntity> DREG = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(AnimancyMod.MOD_ID, "dreg"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DregZombieEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 2.0f)).build());



    public static void registerModEntites() {
        AnimancyMod.LOGGER.debug("Registering Mod Entities for " + AnimancyMod.MOD_ID);
    }
}
