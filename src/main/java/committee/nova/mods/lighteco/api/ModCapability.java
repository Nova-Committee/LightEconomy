package committee.nova.mods.lighteco.api;

import committee.nova.mods.lighteco.LightEconomy;
import committee.nova.mods.lighteco.core.IAccount;
import net.neoforged.neoforge.capabilities.EntityCapability;

/**
 * ModCapability
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/3 2:39
 */
public class ModCapability {
    public static final EntityCapability<IAccount, Void> ECONOMY = EntityCapability.createVoid(LightEconomy.rl("economy"), IAccount.class);

}
