package com.redpxnda.staminawild.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerStamina {
    private int stamina;
    private int MAX_STAMINA = 1000;
    private final int MIN_STAMINA = 0;

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina() {
        return stamina;
    }
    public void addStamina(int amount) {
        this.stamina = Math.min(stamina + amount, MAX_STAMINA);
    }
    public void takeStamina(int amount) {
        this.stamina = Math.max(stamina - amount, MIN_STAMINA);
    }
    public int getMaxStamina() {
        return MAX_STAMINA;
    }
    public void setMaxStamina(int amount) {
        this.MAX_STAMINA = amount;
    }

    public void copyFrom(PlayerStamina source) {
        this.stamina = source.stamina;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("stamina", stamina);
    }

    public void loadNBTData(CompoundTag nbt) {
        stamina = nbt.getInt("stamina");
    }
}
