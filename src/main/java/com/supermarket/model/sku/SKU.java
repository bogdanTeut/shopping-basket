package com.supermarket.model.sku;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains all the elements.
 * Also contains the default prices. If no pricing rule is being
 * provided at runtime for a certain SKU these prices are being used.
 */
public enum SKU {
  A("A", 50),
  B("B", 30),
  C("C", 20),
  D("D", 15);

  private static Map<String, SKU> skuMap;

  static {
    skuMap = Stream.of(SKU.values())
            .collect(Collectors.toMap(sku -> sku.getCode(),
                    Function.identity()));
  }

  private final String code;
  private final Integer price;

  SKU(final String code, final Integer price) {
    this.code = code;
    this.price = price;
  }

  public String getCode() {
    return code;
  }

  public Integer getPrice() {
    return price;
  }

  public static SKU fromString(final String code) {
    final SKU sku = skuMap.get(code);

    if (sku == null) {
      throw  new IllegalArgumentException("Unknown item X");
    }

    return sku;
  }
}
