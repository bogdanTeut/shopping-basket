package com.supermarket.model.scan;

import com.supermarket.model.sku.SKU;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Test class for ItemsGroup
 */
public class ItemsGroupTest {

  @Test
  public void testEquality() {
    assertTrue (new ItemsGroup(SKU.A, 3).equals(new ItemsGroup(SKU.A, 3)));
  }
}