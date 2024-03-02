package committee.nova.mods.lighteco.core;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.math.BigDecimal;

/**
 * Account
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/3 2:42
 */
public class Account implements IAccount, INBTSerializable<CompoundTag> {
    private static final String TAG_BALANCE = "lighteco_balance";
    private BigDecimal balance = new BigDecimal("0");
    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void setBalance(BigDecimal value) {
        this.balance = value;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putString(TAG_BALANCE, balance.toPlainString());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.balance = new BigDecimal(tag.getString(TAG_BALANCE));
    }
}
