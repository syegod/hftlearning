package io.sye.sbe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.sye.sbe.pojo.TradeData;
import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SbeCodecTest {

  private static TradeData tradeData;

  @BeforeAll
  static void setup() {
    tradeData = new TradeData()
        .amount(BigDecimal.valueOf(10))
        .price(BigDecimal.valueOf(187.62))
        .market(Market.NASDAQ)
        .currency(Currency.USD)
        .symbol("NVDA");
  }

  @Test
  void testTradeDataEncodeDecode() {
    final var buffer = SbeMessageCodec.encodeTradeData(tradeData);
    final var decodedData = SbeMessageCodec.decodeTradeData(buffer);

    assertEquals(tradeData, decodedData);
  }

  @Test
  void testTradeDataEncodeDecodeMultiple() {
    final var symbols = new String[]{"NVDA", "MSFT", "AAPL", "TSLA", "META", "GOOG"};
    final var rand = new Random();
    IntStream.range(0, 100).forEach((e) -> {

      final var data = new TradeData()
          .amount(BigDecimal.valueOf(rand.nextDouble(1000)))
          .price(BigDecimal.valueOf(rand.nextDouble(100000)))
          .market(Market.get((byte) rand.nextInt(2)))
          .currency(Currency.get((byte) rand.nextInt(2)))
          .symbol(symbols[rand.nextInt(0, symbols.length)]);

      final var buffer = SbeMessageCodec.encodeTradeData(data);
      final var decodedData = SbeMessageCodec.decodeTradeData(buffer);

      assertEquals(data, decodedData);
    });
  }

}
