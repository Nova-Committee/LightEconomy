package committee.nova.lighteco.capabilities.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.math.BigDecimal;

public interface IAccount extends INBTSerializable<CompoundTag> {
    BigDecimal getBalance();

    void setBalance(BigDecimal value);
}
