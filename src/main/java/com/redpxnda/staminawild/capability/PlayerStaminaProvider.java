package com.redpxnda.staminawild.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.world.NoteBlockEvent;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStaminaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerStamina> PLAYER_STAMINA = CapabilityManager.get(new CapabilityToken<PlayerStamina>() {});

    private PlayerStamina stamina = null;
    private final LazyOptional<PlayerStamina> optional = LazyOptional.of(this::createPlayerStamina);

    private PlayerStamina createPlayerStamina() {
        if (this.stamina == null) {
            this.stamina = new PlayerStamina();
        }

        return this.stamina;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_STAMINA) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerStamina().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerStamina().loadNBTData(nbt);
    }
}
