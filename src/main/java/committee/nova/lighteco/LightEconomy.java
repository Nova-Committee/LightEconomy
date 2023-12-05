package committee.nova.lighteco;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(LightEconomy.MODID)
public class LightEconomy {
    public static final String MODID = "lighteco";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LightEconomy() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
