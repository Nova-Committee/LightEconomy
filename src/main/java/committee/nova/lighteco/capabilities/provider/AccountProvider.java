package committee.nova.lighteco.capabilities.provider;

import committee.nova.lighteco.capabilities.api.IAccount;
import committee.nova.lighteco.capabilities.impl.Account;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@MethodsReturnNonnullByDefault
public class AccountProvider implements ICapabilitySerializable<CompoundTag> {
    private IAccount account;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == Account.ACCOUNT ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    public IAccount getOrCreateCapability() {
        if (account == null) this.account = new Account();
        return this.account;
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        getOrCreateCapability().deserializeNBT(tag);
    }
}
