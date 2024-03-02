package committee.nova.mods.lighteco;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
/**
 * LightEconomy
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/3 2:36
 */
@Mod(LightEconomy.MODID)
public class LightEconomy {
    public static final String MODID = "lighteco";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LightEconomy() {
        NeoForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MODID, path);
    }
}
