package committee.nova.lighteco.handler;

import committee.nova.lighteco.Constants;
import committee.nova.lighteco.capabilities.api.IAccount;
import committee.nova.lighteco.capabilities.impl.Account;
import committee.nova.lighteco.capabilities.provider.AccountProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapHandler {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Account.class);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;
        event.addCapability(new ResourceLocation(Constants.MOD_ID, "account"), new AccountProvider());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        final Player original = event.getOriginal();
        original.reviveCaps();
        final LazyOptional<IAccount> oldCap = original.getCapability(Account.ACCOUNT);
        final LazyOptional<IAccount> newCap = event.getEntity().getCapability(Account.ACCOUNT);
        newCap.ifPresent((n) -> oldCap.ifPresent((o) -> n.deserializeNBT(o.serializeNBT())));
        original.invalidateCaps();
    }
}
