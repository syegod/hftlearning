package io.sye.sbe;

import io.sye.sbe.pojo.TradeData;
import java.math.BigDecimal;
import org.agrona.ExpandableArrayBuffer;
import org.agrona.MutableDirectBuffer;

public class SbeMessageCodec {

  private static final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
  private static final MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
  private static final TradeDataEncoder tradeDataEncoder = new TradeDataEncoder();
  private static final TradeDataDecoder tradeDataDecoder = new TradeDataDecoder();
  private static final MutableDirectBuffer buffer = new ExpandableArrayBuffer();

  private SbeMessageCodec() {
  }

  public static MutableDirectBuffer encodeTradeData(TradeData tradeData) {
    final var encoder = tradeDataEncoder.wrapAndApplyHeader(buffer, 0, headerEncoder);
    int priceMantissa = tradeData.price().scaleByPowerOfTen(tradeData.price().scale()).intValue();
    int priceExponent = tradeData.price().scale() * -1;
    encoder.amount(tradeData.amount()).quote()
        .symbol(tradeData.symbol())
        .currency(tradeData.currency())
        .market(tradeData.market())
        .price()
        .mantissa(priceMantissa)
        .exponent((byte) priceExponent);
    return buffer;
  }

  public static TradeData decodeTradeData(MutableDirectBuffer directBuffer) {
    tradeDataDecoder.wrapAndApplyHeader(directBuffer, 0, headerDecoder);

    var price = BigDecimal.valueOf(tradeDataDecoder.quote().price().mantissa())
        .scaleByPowerOfTen(tradeDataDecoder.quote().price().exponent());

    return new TradeData()
        .amount(tradeDataDecoder.amount())
        .market(tradeDataDecoder.quote().market())
        .currency(tradeDataDecoder.quote().currency())
        .symbol(tradeDataDecoder.quote().symbol())
        .price(price);
  }
}
