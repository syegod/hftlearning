package io.sye.sbe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SbeCodecTest {

  private static TradeData tradeData;

  @BeforeAll
  static void setup() {
    tradeData = new TradeData().amount(2).price(BigDecimal.valueOf(187.62)).market(Market.NASDAQ)
        .currency(Currency.USD).symbol("NVDA");
  }

  @Test
  void testTradeDataEncodeDecode() {
    final var buffer = SbeMessageCodec.encodeTradeData(tradeData);
    final var decodedData = SbeMessageCodec.decodeTradeData(buffer);

    assertEquals(tradeData, decodedData);
  }

  @Test
  void testTradeDataEncodeDecodeMultiple() {
    IntStream.range(0, 100).forEach((e) -> {
      final var buffer = SbeMessageCodec.encodeTradeData(tradeData);
      final var decodedData = SbeMessageCodec.decodeTradeData(buffer);

      assertEquals(tradeData, decodedData);
    });
  }

}
