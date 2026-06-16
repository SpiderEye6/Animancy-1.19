package net.spidereye.animancy.util;

import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {
    NbtCompound getPersistentData();
}