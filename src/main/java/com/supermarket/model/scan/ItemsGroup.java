package com.supermarket.model.scan;

import com.google.common.base.Objects;
import com.supermarket.model.sku.SKU;

/**
 * Stores the mapping between SKU and the number of occurrences after each scan.
 */
public class ItemsGroup {
  private final SKU sku;
  private final Integer units;

  public ItemsGroup(final SKU sku, final Integer units) {
    this.sku = sku;
    this.units = units;
  }

  public SKU getSku() {
    return sku;
  }

  public Integer getUnits() {
    return units;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemsGroup itemsGroup = (ItemsGroup) o;
    return Objects.equal(sku, itemsGroup.sku) &&
            Objects.equal(units, itemsGroup.units);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(sku, units);
  }
}
