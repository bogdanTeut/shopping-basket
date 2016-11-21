package com.supermarket.pricing.calculator;

import com.google.common.collect.ImmutableList;
import com.supermarket.model.scan.ItemsGroup;
import com.supermarket.princing.calculator.Calculator;
import com.supermarket.princing.calculator.impl.PriceCalculator;
import com.supermarket.model.rule.PricingRule;
import com.supermarket.model.sku.SKU;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CalculatorTest {

  private Calculator priceCalculator;

  @BeforeMethod
  public void onSetup() {
     priceCalculator = new PriceCalculator();
  }

  @Test
  public void givenTwoAItems_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupA = new ItemsGroup(SKU.A, 2);

    //method under test
    final Integer totalPrice = priceCalculator.calculatePrice(ImmutableList.of(itemsGroupA), Collections.emptyList());

    //verify results
    assertThat(totalPrice, is(100));
  }

  @Test
  public void givenNoItem_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //method under test
    final Integer totalPrice = priceCalculator.calculatePrice(ImmutableList.of(), Collections.emptyList());

    //verify results
    assertThat(totalPrice, is(0));
  }

  @Test
  public void givenTwoBItems_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupB = new ItemsGroup(SKU.B, 2);

    //method under test
    final Integer totalPrice = priceCalculator.calculatePrice(ImmutableList.of(itemsGroupB), Collections.emptyList());

    //verify results
    assertThat(totalPrice, is(60));
  }

  @Test
  public void givenTwoCItems_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupC = new ItemsGroup(SKU.C, 2);

    //method under test
    final Integer totalPrice = priceCalculator.calculatePrice(ImmutableList.of(itemsGroupC), Collections.emptyList());

    //verify results
    assertThat(totalPrice, is(40));
  }

  @Test
  public void givenTwoDItems_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupD = new ItemsGroup(SKU.D, 2);

    //method under test
    final Integer totalPrice = priceCalculator.calculatePrice(ImmutableList.of(itemsGroupD), Collections.emptyList());

    //verify results
    assertThat(totalPrice, is(30));
  }

  @Test
  public void givenTwoAItemsAndTwoDItems_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupA = new ItemsGroup(SKU.A, 2);
    final ItemsGroup itemsGroupD = new ItemsGroup(SKU.D, 2);

    //method under test
    final Integer totalPrice = priceCalculator.calculatePrice(ImmutableList.of(itemsGroupA, itemsGroupD), Collections.emptyList());

    //verify results
    assertThat(totalPrice, is(130));
  }

  @Test
  public void givenFourAItemsAndAPricingStrategy_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupA = new ItemsGroup(SKU.A, 4);
    final PricingRule pricingRule = new PricingRule(SKU.A, 50, 3, 130);

    //method under test
    final Integer totalPrice = priceCalculator.calculatePrice(ImmutableList.of(itemsGroupA), ImmutableList.of(pricingRule));

    //verify results
    assertThat(totalPrice, is(180));
  }

  @Test
  public void givenThreeBItemsAndAPricingStrategy_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupA = new ItemsGroup(SKU.B, 3);
    final PricingRule pricingRule = new PricingRule(SKU.B, 30, 2, 45);

    //method under test
    final Integer totalPrice = priceCalculator.calculatePrice(ImmutableList.of(itemsGroupA), ImmutableList.of(pricingRule));

    //verify results
    assertThat(totalPrice, is(75));
  }

  @Test
  public void givenMixedItemsAndAPricingStrategies_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupA = new ItemsGroup(SKU.A, 4);
    final ItemsGroup itemsGroupB = new ItemsGroup(SKU.B, 3);
    final ItemsGroup itemsGroupC = new ItemsGroup(SKU.C, 2);
    final ItemsGroup itemsGroupD = new ItemsGroup(SKU.D, 1);
    final PricingRule pricingRuleA = new PricingRule(SKU.A, 50, 3, 130);
    final PricingRule pricingRuleB = new PricingRule(SKU.B, 30, 2, 45);

    //method under test
    final Integer totalPrice = priceCalculator
            .calculatePrice(ImmutableList.of(itemsGroupA, itemsGroupB, itemsGroupC, itemsGroupD), ImmutableList.of(pricingRuleA, pricingRuleB));

    //verify results
    assertThat(totalPrice, is(310));
  }

  @Test
  public void givenMixedItemsAndAPricingStrategiesIncludingChangedPrices_whenCheckingOut_thenItShouldCalculateTheCorrectPrice() {
    //prepare data
    final ItemsGroup itemsGroupA = new ItemsGroup(SKU.A, 4);
    final ItemsGroup itemsGroupB = new ItemsGroup(SKU.B, 3);
    final ItemsGroup itemsGroupC = new ItemsGroup(SKU.C, 2);
    final ItemsGroup itemsGroupD = new ItemsGroup(SKU.D, 1);

    final PricingRule pricingRuleA = new PricingRule(SKU.A, 50, 2, 130);
    final PricingRule pricingRuleB = new PricingRule(SKU.B, 40, 2, 45);//unit price changed 40 should take precedence over 30
    final PricingRule pricingRuleC = new PricingRule(SKU.C, 20, 2, 30);
    final PricingRule pricingRuleD = new PricingRule(SKU.D, 15, 2, 8);

    //method under test
    final Integer totalPrice = priceCalculator
            .calculatePrice(ImmutableList.of(itemsGroupA, itemsGroupB, itemsGroupC, itemsGroupD),
                    ImmutableList.of(pricingRuleA, pricingRuleB, pricingRuleC, pricingRuleD));

    //verify results
    assertThat(totalPrice, is(390));
  }

}
