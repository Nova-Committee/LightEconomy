package committee.nova.lighteco;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Constants.MOD_ID)
public class LightEconomy {
    public LightEconomy() {
        CommonClass.init();
    }
}
