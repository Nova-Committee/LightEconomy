package committee.nova.lighteco.capabilities.impl;

import committee.nova.lighteco.capabilities.api.IAccount;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.math.BigDecimal;

/**
 * Project: LightEconomy
 * Author: cnlimiter
 * Date: 2023/2/12 15:49
 * Description:
 */
public class Account implements IAccount {

    private static final String TAG_BALANCE = "lighteco_balance";
    private BigDecimal balance;

    public Account(Player player) {
        this(player, 0);
    }

    public Account(Player player, double v) {
        this.balance = new BigDecimal(Double.toString(v));
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void setBalance(BigDecimal value) {
        this.balance = value;
    }


    @Override
    public void readFromNbt(CompoundTag tag) {
        balance = new BigDecimal(tag.getString(TAG_BALANCE));
    }


    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putString(TAG_BALANCE, balance.toPlainString());
    }
}
