package com.supermarket.model.rule;

import com.supermarket.model.sku.SKU;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test class for ItemsGroup
 */
public class PricingRuleTest {

  @Test
  public void testEquality() {
    assertTrue (new PricingRule(SKU.A, 4, 3, 130).equals(new PricingRule(SKU.A, 4, 3, 130)));
  }

}