package com.redpxnda.staminawild.packet;

import com.redpxnda.staminawild.client.ClientStaminaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;

import java.util.function.Supplier;

public class StaminaSyncToClientPacket {
    private final int stamina;


    public StaminaSyncToClientPacket(int stamina) {
        this.stamina = stamina;
    }

    public StaminaSyncToClientPacket(FriendlyByteBuf buffer) {
        this.stamina = buffer.readInt();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(stamina);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientStaminaData.set(stamina);
        });

        return true;
    }
}
