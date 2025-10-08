package io.sye.sbe;

import io.sye.sbe.pojo.TradeData;
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
    BigDecimalCodec.encodeBigDecimal(tradeData.price(), encoder.quote().price());
    BigDecimalCodec.encodeBigDecimal(tradeData.amount(), encoder.amount());
    encoder.quote()
        .symbol(tradeData.symbol())
        .currency(tradeData.currency())
        .market(tradeData.market());
    return buffer;
  }

  public static TradeData decodeTradeData(MutableDirectBuffer directBuffer) {
    tradeDataDecoder.wrapAndApplyHeader(directBuffer, 0, headerDecoder);
    return new TradeData()
        .amount(BigDecimalCodec.decodeBigDecimal(tradeDataDecoder.amount()))
        .market(tradeDataDecoder.quote().market())
        .currency(tradeDataDecoder.quote().currency())
        .symbol(tradeDataDecoder.quote().symbol())
        .price(BigDecimalCodec.decodeBigDecimal(tradeDataDecoder.quote().price()));
  }
}
