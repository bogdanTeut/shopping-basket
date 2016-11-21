package com.supermarket;

import com.google.common.collect.ImmutableList;
import com.supermarket.model.rule.PricingRule;
import com.supermarket.model.scan.ItemsGroup;
import com.supermarket.model.sku.SKU;
import com.supermarket.princing.calculator.Calculator;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ShoppingBasketTest {

  private Checkoutable shoppingBasket;

  @Mock
  private Calculator calculator;

  @BeforeMethod
  public void onSetup() {

    MockitoAnnotations.initMocks(this);
    shoppingBasket = new ShoppingBasket(calculator);
  }

  @Test
  public void givenExistingCodes_whenCheckingOut_thenItShouldReturnTheCorrectPrice() {
    //prepare captors
    final ArgumentCaptor<Collection> itemsList = ArgumentCaptor.forClass(Collection.class);
    final ArgumentCaptor<List> priceRulesList = ArgumentCaptor.forClass(List.class);

    //prepare data
    final PricingRule pricingRuleA = new PricingRule(SKU.A, 50, 3, 130);
    final PricingRule pricingRuleB = new PricingRule(SKU.B, 30, 2, 45);

    //method under test
    shoppingBasket.checkout(ImmutableList.of("A", "B", "C", "D", "A", "B", "A", "B", "A"),
            ImmutableList.of(pricingRuleA, pricingRuleB));

    //verify results
    verifyItemGroupList(itemsList, priceRulesList);
    verifyPricingRuleList(priceRulesList);
  }

  @Test
  public void givenEmptyListOfCodes_whenCheckingOut_thenItShouldReturnZero() {
    //prepare data
    final PricingRule pricingRuleA = new PricingRule(SKU.A, 50, 3, 130);
    final PricingRule pricingRuleB = new PricingRule(SKU.B, 30, 2, 45);

    //method under test
    shoppingBasket.checkout(ImmutableList.of(),
            ImmutableList.of(pricingRuleA, pricingRuleB));

    //verify results
    verify(this.calculator, never()).calculatePrice(anyCollection(), anyList());
  }

  private void verifyItemGroupList(ArgumentCaptor<Collection> itemsList, ArgumentCaptor<List> priceRulesList) {
    verify(calculator).calculatePrice(itemsList.capture(), priceRulesList.capture());
    final Collection<ItemsGroup> itemsListValue = itemsList.getValue();
    assertThat(itemsListValue, hasItem(new ItemsGroup(SKU.A, 4)));
    assertThat(itemsListValue, hasItem(new ItemsGroup(SKU.B, 3)));
    assertThat(itemsListValue, hasItem(new ItemsGroup(SKU.C, 1)));
    assertThat(itemsListValue, hasItem(new ItemsGroup(SKU.D, 1)));
  }

  private void verifyPricingRuleList(ArgumentCaptor<List> priceRulesList) {
    final List<PricingRule> priceRulesListValue = priceRulesList.getValue();
    assertThat(priceRulesListValue, hasItem(new PricingRule(SKU.A, 50, 3, 130)));
    assertThat(priceRulesListValue, hasItem(new PricingRule(SKU.A, 50, 3, 130)));
  }

}