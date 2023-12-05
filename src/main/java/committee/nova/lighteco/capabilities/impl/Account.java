package committee.nova.lighteco.capabilities.impl;

import committee.nova.lighteco.capabilities.api.IAccount;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.math.BigDecimal;

public class Account implements IAccount {
    public static final Capability<IAccount> ACCOUNT = CapabilityManager.get(new CapabilityToken<>() {
    });
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
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putString(TAG_BALANCE, balance.toPlainString());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        balance = new BigDecimal(tag.getString(TAG_BALANCE));
    }
}
