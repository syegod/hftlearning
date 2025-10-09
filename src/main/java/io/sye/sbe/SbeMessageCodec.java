package io.sye.sbe;

import io.sye.sbe.pojo.TradeData;
import org.agrona.ExpandableArrayBuffer;
import org.agrona.MutableDirectBuffer;

public class SbeMessageCodec {

  private final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
  private final MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
  private final TradeDataEncoder tradeDataEncoder = new TradeDataEncoder();
  private final TradeDataDecoder tradeDataDecoder = new TradeDataDecoder();
  private final MutableDirectBuffer buffer = new ExpandableArrayBuffer();

  public MutableDirectBuffer encodeTradeData(TradeData tradeData) {
    final var encoder = tradeDataEncoder.wrapAndApplyHeader(buffer, 0, headerEncoder);
    BigDecimalCodec.encodeBigDecimal(tradeData.price(), encoder.quote().price());
    BigDecimalCodec.encodeBigDecimal(tradeData.amount(), encoder.amount());
    encoder.quote()
        .symbol(tradeData.symbol())
        .currency(tradeData.currency())
        .market(tradeData.market());
    return buffer;
  }

  public TradeData decodeTradeData(MutableDirectBuffer directBuffer) {
    tradeDataDecoder.wrapAndApplyHeader(directBuffer, 0, headerDecoder);
    return new TradeData()
        .amount(BigDecimalCodec.decodeBigDecimal(tradeDataDecoder.amount()))
        .market(tradeDataDecoder.quote().market())
        .currency(tradeDataDecoder.quote().currency())
        .symbol(tradeDataDecoder.quote().symbol())
        .price(BigDecimalCodec.decodeBigDecimal(tradeDataDecoder.quote().price()));
  }
}
