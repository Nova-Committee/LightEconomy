package committee.nova.lighteco.util;

import committee.nova.lighteco.handler.CapHandler;
import net.minecraft.world.entity.player.Player;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Project: LightEconomy
 * Author: cnlimiter
 * Date: 2023/2/12 16:29
 * Description:
 */
public class EcoUtils {
    public static final BigDecimal ZERO = new BigDecimal(0);
    public static final BiPredicate<Player, BigDecimal> NON_NEGATIVE = (p, v) -> v.compareTo(ZERO) > -1;

    public static EcoActionResult vary(
            Player player, BigDecimal value, BiPredicate<Player, BigDecimal> argChecker,
            BiFunction<Player, BigDecimal, BigDecimal> processor, BiPredicate<Player, BigDecimal> resultChecker
    ) {
        if (!argChecker.test(player, value)) return EcoActionResult.ARG_ILLEGAL;
        final AtomicReference<EcoActionResult> result = new AtomicReference<>(EcoActionResult.CAPABILITY_FAILURE);
        Optional.of(CapHandler.BALANCE.get(player)).ifPresent(
                a -> {
                    final BigDecimal newValue = a.getBalance().add(processor.apply(player, value));
                    if (!resultChecker.test(player, newValue)) {
                        result.set(EcoActionResult.RESULT_ILLEGAL);
                        return;
                    }
                    a.setBalance(newValue);
                    result.set(EcoActionResult.SUCCESS);
                }
        );
        return result.get();
    }

    public static EcoActionResult debt(Player player, BigDecimal value) {
        return vary(player, value, NON_NEGATIVE, (p, v) -> v, NON_NEGATIVE);
    }

    public static EcoActionResult credit(Player player, BigDecimal value) {
        return vary(player, value, NON_NEGATIVE, (p, v) -> v.negate(), NON_NEGATIVE);
    }

    public static Optional<BigDecimal> getBalance(Player player) {
        final AtomicReference<BigDecimal> result = new AtomicReference<>(null);
        Optional.of(CapHandler.BALANCE.get(player)).ifPresent(a -> result.set(a.getBalance()));
        return result.get() == null ? Optional.empty() : Optional.of(result.get());
    }

    public enum EcoActionResult {
        SUCCESS("success"),
        ARG_ILLEGAL("arg_illegal"),
        RESULT_ILLEGAL("result_illegal"),
        CAPABILITY_FAILURE("capability_failure");

        private final String id;

        EcoActionResult(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
