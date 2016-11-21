package com.supermarket.model.sku;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test class for SKU
 */
public class SKUTest {

  @DataProvider(name = "skuProvider")
  public Object[][] skuProvider() {
    return new Object[][] {
            {"A", SKU.A},
            {"B", SKU.B},
            {"C", SKU.C},
            {"D", SKU.D}
    };
  }

  @Test(dataProvider = "skuProvider")
  public void givenAnExistingCode_thenItShouldReturnTheCorrectElement(final String code, final SKU expectedResult) {
    assertThat(SKU.fromString(code), is(expectedResult));
  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Unknown item X")
  public void givenNonExistingCode_thenItShouldThrowException () {
    SKU.fromString("X");
  }
}