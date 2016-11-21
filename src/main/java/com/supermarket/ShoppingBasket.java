package com.supermarket;

import com.supermarket.model.rule.PricingRule;
import com.supermarket.model.scan.ItemsGroup;
import com.supermarket.model.sku.SKU;
import com.supermarket.princing.calculator.Calculator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * This is the entry of the shopping basket
 */
public class ShoppingBasket implements Checkoutable {

  private final Calculator calculator;

  public ShoppingBasket(final Calculator calculator) {
    this.calculator = calculator;
  }

  /**
   * Groups codes per sku and counts each of their occurrences. Then calculates the total prices.
   * @param codes
   * @param rules
   * @return
   */
  public Integer checkout(final List<String> codes, final List<PricingRule> rules) {
    final Map<SKU, ItemsGroup> itemGroupMap = codes.stream()
            .map(SKU::fromString)
            .<Map<SKU, ItemsGroup>, Map<SKU, ItemsGroup>>collect(new GroupBySKUAndSumCollector());

    if (itemGroupMap.isEmpty()) return 0;
    return calculator.calculatePrice(itemGroupMap.values(), rules);
  }

  /**
   * Custom collector which groups a list skus by id and counts the items per each sku.
   * @param <T>
   */
  public class GroupBySKUAndSumCollector<T extends SKU> implements Collector<T, Map<SKU, ItemsGroup>, Map<SKU, ItemsGroup>> {

    @Override
    public Supplier<Map<SKU, ItemsGroup>> supplier() {
      return () -> new HashMap<>();
    }

    @Override
    public BiConsumer<Map<SKU, ItemsGroup>, T> accumulator() {
      return (map, sku) -> {
        final ItemsGroup itemsGroup = map.get(sku);
        if (itemsGroup == null) {
          map.put(sku, new ItemsGroup(sku, 1));
        } else {
          map.put(sku, new ItemsGroup(sku, itemsGroup.getUnits()+1));
        }
      };
    }


    @Override
    public Function<Map<SKU, ItemsGroup>, Map<SKU, ItemsGroup>> finisher() {
      return Function.identity();
    }

    @Override
    public BinaryOperator<Map<SKU, ItemsGroup>> combiner() {
      //nothing to do here. necessary for parallel processing only
      return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
      return Collections.singleton(Characteristics.IDENTITY_FINISH);
    }
  }
}
