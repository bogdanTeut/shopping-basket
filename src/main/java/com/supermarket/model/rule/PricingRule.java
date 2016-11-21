package com.supermarket.model.rule;

import com.google.common.base.Objects;
import com.supermarket.model.sku.SKU;

/**
 * This is a model class designed to store the pricing rule.
 */
public class PricingRule {

  private final SKU sku;
  private final Integer unitPrice;
  private final Integer specialPriceUnits;
  private final Integer specialPrice;

  public PricingRule(final SKU sku, final Integer unitPrice, final Integer specialPriceUnits, final Integer specialPrice) {
    this.sku = sku;
    this.unitPrice = unitPrice;
    this.specialPriceUnits = specialPriceUnits;
    this.specialPrice = specialPrice;
  }

  public SKU getSku() {
    return sku;
  }

  public Integer getUnitPrice() {
    return unitPrice;
  }

  public Integer getSpecialPriceUnits() {
    return this.specialPriceUnits;
  }

  public Integer getSpecialPrice() {
    return specialPrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PricingRule that = (PricingRule) o;
    return Objects.equal(sku, that.sku) &&
            Objects.equal(unitPrice, that.unitPrice) &&
            Objects.equal(specialPriceUnits, that.specialPriceUnits) &&
            Objects.equal(specialPrice, that.specialPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(sku, unitPrice, specialPriceUnits, specialPrice);
  }
}
