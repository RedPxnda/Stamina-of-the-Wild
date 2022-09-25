package com.redpxnda.staminawild.capability;

import com.redpxnda.staminawild.config.CommonConfig;
import net.minecraft.nbt.CompoundTag;

public class PlayerStamina {
    private int stamina = CommonConfig.MAX_STAMINA.get();
    private double recoveryTime = 20;
    private int MAX_STAMINA = CommonConfig.MAX_STAMINA.get();
    private final int MIN_STAMINA = 0;

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina() {
        return stamina;
    }
    public void addStamina(int amount) {
        if(stamina + amount <= MAX_STAMINA) {
            this.stamina += amount;
        }
    }

    public void setRecoveryTime(double recoveryTime) {
        this.recoveryTime = recoveryTime;
    }
    public void depleteRecoveryTime() {
        if (recoveryTime > 0) recoveryTime--;
    }
    public double getRecoveryTime() {
        return recoveryTime;
    }

    public void takeStamina(int amount) {
        if(stamina - amount >= MIN_STAMINA) {
            this.stamina -= amount;
        }
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
