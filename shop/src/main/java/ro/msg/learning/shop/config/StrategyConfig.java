package ro.msg.learning.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ro.msg.learning.shop.service.strategy.FallbackLocationStrategy;
import ro.msg.learning.shop.service.strategy.LocationStrategy;

import java.util.List;
import java.util.Map;

@Configuration
public class StrategyConfig {
    private final String strategyName;
    Map<String, LocationStrategy> strategies;

    public StrategyConfig(Map<String,LocationStrategy> strategies,@Value("${order.strategy:singleLocation}")
    String strategyName) {
        this.strategies=strategies;
        this.strategyName = strategyName;
    }

    @Bean
    @Primary
    public LocationStrategy getLocationStrategy() {
        if ("fallback".equals(strategyName)) {
            return new FallbackLocationStrategy(List.of(
                    strategies.get("singleLocationStrategy"),
                    strategies.get("mostAbundantStrategy"),
                    strategies.get("greedyStrategy")
            ));
        }

        LocationStrategy locationStrategy=strategies.get(strategyName);
        if(locationStrategy==null){
            throw new RuntimeException("Strategy "+strategyName+" not found");
        }
        return locationStrategy;
    }
}
