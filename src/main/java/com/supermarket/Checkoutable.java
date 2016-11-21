package com.supermarket;

import com.supermarket.model.rule.PricingRule;

import java.util.List;

public interface Checkoutable {

  Integer checkout(List<String> codes, List<PricingRule> rules);
}
