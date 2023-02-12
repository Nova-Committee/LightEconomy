package committee.nova.lighteco.capabilities.api;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;

import java.math.BigDecimal;

/**
 * Project: LightEconomy
 * Author: cnlimiter
 * Date: 2023/2/12 15:45
 * Description:
 */
public interface IAccount extends PlayerComponent {
    BigDecimal getBalance();

    void setBalance(BigDecimal value);
}
