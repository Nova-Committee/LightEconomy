package committee.nova.lighteco.handler;

import committee.nova.lighteco.Constants;
import committee.nova.lighteco.capabilities.impl.Account;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

/**
 * Project: LightEconomy
 * Author: cnlimiter
 * Date: 2023/2/12 16:00
 * Description:
 */
public class CapHandler implements EntityComponentInitializer {
    public static final ComponentKey<Account> BALANCE =
            ComponentRegistry.getOrCreate(new ResourceLocation(Constants.MOD_ID, "balance"), Account.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(BALANCE, Account::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
