package committee.nova.mods.lighteco.handler;

import committee.nova.mods.lighteco.api.ModCapability;
import committee.nova.mods.lighteco.core.Account;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * CapHandler
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/3 2:46
 */
@Mod.EventBusSubscriber
public class CapHandler {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(ModCapability.ECONOMY, EntityType.PLAYER, (player, var) -> new Account());
    }
}
