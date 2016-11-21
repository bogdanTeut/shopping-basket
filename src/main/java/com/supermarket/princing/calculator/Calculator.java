package com.supermarket.princing.calculator;

import com.supermarket.model.scan.ItemsGroup;
import com.supermarket.model.rule.PricingRule;

import java.util.Collection;
import java.util.List;

public interface Calculator {
  Integer calculatePrice(Collection<ItemsGroup> itemsGroups, List<PricingRule> pricingRules);
}
