package committee.nova.lighteco.util;

import committee.nova.lighteco.capabilities.impl.Account;
import net.minecraft.world.entity.player.Player;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;

public class EcoUtils {
    public static final BigDecimal ZERO = new BigDecimal(0);
    public static final Predicate<BigDecimal> NON_NEGATIVE = v -> v.compareTo(ZERO) > -1;

    private static EcoActionResult vary(Player player, BigDecimal value, Predicate<BigDecimal> argChecker,
                                        Function<BigDecimal, BigDecimal> processor, Predicate<BigDecimal> resultChecker) {
        if (!argChecker.test(value)) return EcoActionResult.ARG_ILLEGAL;
        final AtomicReference<EcoActionResult> result = new AtomicReference<>(EcoActionResult.CAPABILITY_FAILURE);
        player.getCapability(Account.ACCOUNT).ifPresent(a -> {
                    final BigDecimal newValue = a.getBalance().add(processor.apply(value));
                    if (!resultChecker.test(newValue)) {
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
        return vary(player, value, NON_NEGATIVE, Function.identity(), NON_NEGATIVE);
    }

    public static EcoActionResult credit(Player player, BigDecimal value) {
        return vary(player, value, NON_NEGATIVE, BigDecimal::negate, NON_NEGATIVE);
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
