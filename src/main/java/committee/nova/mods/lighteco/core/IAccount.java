package committee.nova.mods.lighteco.core;

import java.math.BigDecimal;

/**
 * IAccount
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/3 2:35
 */
public interface IAccount{
    BigDecimal getBalance();

    void setBalance(BigDecimal value);
}
