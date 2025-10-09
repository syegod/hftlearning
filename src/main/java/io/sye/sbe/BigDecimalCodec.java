package io.sye.sbe;

import java.math.BigDecimal;

public class BigDecimalCodec {

  private BigDecimalCodec() {
  }

  public static void encodeBigDecimal(BigDecimal value, BigDecimalEncoder encoder) {
    long unscaled = value.unscaledValue().longValueExact();
    encoder.value(unscaled);
    encoder.scale((byte) value.scale());
  }

  public static BigDecimal decodeBigDecimal(BigDecimalDecoder decoder) {
    return BigDecimal.valueOf(decoder.value(), decoder.scale());
  }

}
