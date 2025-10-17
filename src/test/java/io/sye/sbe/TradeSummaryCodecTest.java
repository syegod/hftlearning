package io.sye.sbe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.sye.sbe.pojo.summary.Participant;
import io.sye.sbe.pojo.summary.TradeRecord;
import io.sye.sbe.pojo.summary.TradeSummary;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TradeSummaryCodecTest {

  private TradeSummaryCodec codec;
  private static Random random;

  @BeforeAll
  static void beforeAll() {
    random = new Random();
  }

  @BeforeEach
  void beforeEach() {
    codec = new TradeSummaryCodec();
  }

  @Test
  public void testEncodeDecode() {
    IntStream.range(0, 100).forEach((i) -> {
      final var data = new TradeSummary()
          .id(random.nextInt(0, 100))
          .symbol("AAPL")
          .timestamp(System.currentTimeMillis() - random.nextLong(120) * 1000 * 60)
          .totalTrades(random.nextLong(0, 1_000_000))
          .totalVolume(random.nextLong(0, 1_000_000));

      var totalParticipants = random.nextInt(1, 20);
      var totalRecords = random.nextInt(1, 20);

      var participants = new Participant[totalParticipants];
      IntStream.range(0, totalParticipants).forEach((j) -> {
        final var randBytes = new byte[32];
        random.nextBytes(randBytes);
        final var newPart = new Participant()
            .id(random.nextLong(0, Long.MAX_VALUE))
            .name("participant-" + random.nextLong(0, 1000))
            .publicKey(randBytes);
        participants[j] = newPart;
      });

      var records = new TradeRecord[totalRecords];
      IntStream.range(0, totalRecords).forEach((j) -> {
        final var newRec = new TradeRecord()
            .id(random.nextLong(0, Long.MAX_VALUE))
            .participantId(random.nextLong(0, Long.MAX_VALUE))
            .price(BigDecimal.valueOf(random.nextDouble(0.0001, 100000)))
            .quantity(BigDecimal.valueOf(random.nextDouble(0.0001, 100000)))
            .type(random.nextBoolean() ? TradeType.BUY : TradeType.SELL)
            .uuid(UUID.randomUUID())
            .settlementTimestamp(System.currentTimeMillis());
        records[j] = newRec;
      });

      data.participants(participants).tradeRecords(records);

      final var buffer = codec.encode(data);
      final var decoded = codec.decode(buffer);

      assertEquals(data, decoded);
    });
  }
}
