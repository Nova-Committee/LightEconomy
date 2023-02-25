package committee.nova.lighteco.util;

import committee.nova.lighteco.capabilities.impl.Account;
import committee.nova.lighteco.event.BalanceVaryEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class EcoUtils {
    public static final BigDecimal ZERO = new BigDecimal(0);
    public static final BiPredicate<Player, BigDecimal> NON_NEGATIVE = (p, v) -> v.compareTo(ZERO) > -1;

    public static EcoActionResult vary(
            Player player, BigDecimal value, BiPredicate<Player, BigDecimal> argChecker,
            BiFunction<Player, BigDecimal, BigDecimal> processor, BiPredicate<Player, BigDecimal> resultChecker
    ) {
        final BalanceVaryEvent.Pre pre = new BalanceVaryEvent.Pre(player, value, argChecker, processor, resultChecker);
        if (!MinecraftForge.EVENT_BUS.post(pre)) return EcoActionResult.EVENT_CANCELED;
        final BigDecimal baseValue = pre.getBaseValue();
        if (!argChecker.test(player, baseValue)) return EcoActionResult.ARG_ILLEGAL;
        final AtomicReference<EcoActionResult> result = new AtomicReference<>(EcoActionResult.CAPABILITY_FAILURE);
        player.getCapability(Account.ACCOUNT).ifPresent(a -> {
            final BigDecimal pastValue = a.getBalance();
            final BigDecimal newValue = pastValue.add(processor.apply(player, baseValue));
            if (!resultChecker.test(player, newValue)) {
                result.set(EcoActionResult.RESULT_ILLEGAL);
                return;
            }
            a.setBalance(newValue);
            result.set(EcoActionResult.SUCCESS);
            MinecraftForge.EVENT_BUS.post(new BalanceVaryEvent.Post(player, baseValue, pastValue, newValue));
        });
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
        player.getCapability(Account.ACCOUNT).ifPresent(a -> result.set(a.getBalance()));
        return result.get() == null ? Optional.empty() : Optional.of(result.get());
    }

    public enum EcoActionResult {
        SUCCESS("success"),
        ARG_ILLEGAL("arg_illegal"),
        RESULT_ILLEGAL("result_illegal"),
        CAPABILITY_FAILURE("capability_failure"),
        EVENT_CANCELED("event_canceled");

        private final String id;

        EcoActionResult(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
