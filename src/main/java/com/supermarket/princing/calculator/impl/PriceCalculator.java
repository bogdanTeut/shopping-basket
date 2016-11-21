package com.supermarket.princing.calculator.impl;

import com.supermarket.model.scan.ItemsGroup;
import com.supermarket.princing.calculator.Calculator;
import com.supermarket.model.rule.PricingRule;
import com.supermarket.model.sku.SKU;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Default Calculator implementation
 */
public class PriceCalculator implements Calculator {

  public Integer calculatePrice(final Collection<ItemsGroup> itemsGroups, final List<PricingRule> pricingRules) {

    //storing the pricing rules in a map in order to make the retrieval easier
    final Map<SKU, PricingRule> pricingRulesMap = pricingRules.stream()
                                                    .collect(Collectors.toMap(pricingRule -> pricingRule.getSku(),
                                                            Function.identity()));

    return itemsGroups.stream()
          .mapToInt(itemsGroup -> calculatePrice(pricingRulesMap, itemsGroup))
          .sum();
  }

  /**
   * Calculates the prices per item, by either using the prices provided by within pricing rule
   * or by using the default prices
   * @param pricingRulesMap
   * @param itemsGroup
   * @return
   */
  private int calculatePrice(final Map<SKU, PricingRule> pricingRulesMap, final ItemsGroup itemsGroup) {
    final PricingRule pricingRule = pricingRulesMap.get(itemsGroup.getSku());

    final Integer units = itemsGroup.getUnits();
    final Integer defaultPrice = itemsGroup.getSku().getPrice();

    //pricing rule exists
    if (pricingRule != null) {
      final Integer specialPriceUnits = units / pricingRule.getSpecialPriceUnits();
      final Integer remainingUnits = units % pricingRule.getSpecialPriceUnits();
      return specialPriceUnits * pricingRule.getSpecialPrice() + remainingUnits * pricingRule.getUnitPrice();
    } else {
    //use the default pricing
      return units * defaultPrice;
    }
  }
}
