package committee.nova.lighteco.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class BalanceVaryEvent extends PlayerEvent {
    protected BigDecimal baseValue;

    private BalanceVaryEvent(Player player, BigDecimal baseValue) {
        super(player);
        this.baseValue = baseValue;
    }

    public BigDecimal getBaseValue() {
        return baseValue;
    }

    @Cancelable
    public static class Pre extends BalanceVaryEvent {
        private final BiPredicate<Player, BigDecimal> argChecker;
        private final BiFunction<Player, BigDecimal, BigDecimal> processor;
        private final BiPredicate<Player, BigDecimal> resultChecker;

        public Pre(
                Player player, BigDecimal baseValue, BiPredicate<Player, BigDecimal> argChecker,
                BiFunction<Player, BigDecimal, BigDecimal> processor, BiPredicate<Player, BigDecimal> resultChecker) {
            super(player, baseValue);
            this.argChecker = argChecker;
            this.processor = processor;
            this.resultChecker = resultChecker;
        }

        public void setBaseValue(BigDecimal baseValue) {
            this.baseValue = baseValue;
        }

        public BiPredicate<Player, BigDecimal> getArgChecker() {
            return argChecker;
        }

        public BiFunction<Player, BigDecimal, BigDecimal> getProcessor() {
            return processor;
        }

        public BiPredicate<Player, BigDecimal> getResultChecker() {
            return resultChecker;
        }
    }

    public static class Post extends BalanceVaryEvent {
        private final BigDecimal pastValue;
        private final BigDecimal newValue;

        public Post(Player player, BigDecimal baseValue, BigDecimal pastValue, BigDecimal newValue) {
            super(player, baseValue);
            this.pastValue = pastValue;
            this.newValue = newValue;
        }

        public BigDecimal getPastValue() {
            return pastValue;
        }

        public BigDecimal getNewValue() {
            return newValue;
        }
    }
}
