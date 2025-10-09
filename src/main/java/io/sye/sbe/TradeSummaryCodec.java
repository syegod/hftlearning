package io.sye.sbe;

import io.sye.sbe.pojo.summary.Participant;
import io.sye.sbe.pojo.summary.TradeRecord;
import io.sye.sbe.pojo.summary.TradeSummary;
import java.util.ArrayList;
import org.agrona.ExpandableArrayBuffer;
import org.agrona.MutableDirectBuffer;

public class TradeSummaryCodec {

  private final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
  private final MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
  private final TradeSummaryEncoder encoder = new TradeSummaryEncoder();
  private final TradeSummaryDecoder decoder = new TradeSummaryDecoder();
  private final MutableDirectBuffer buffer = new ExpandableArrayBuffer();

  public MutableDirectBuffer encode(TradeSummary summary) {
    encoder.wrapAndApplyHeader(buffer, 0, headerEncoder);
    encoder.id(summary.id())
        .symbol(summary.symbol())
        .timestamp(summary.timestamp())
        .totalTrades(summary.totalTrades())
        .totalVolume(summary.totalVolume());

    var participants = encoder.participantsCount(summary.participants().size());
    for (var p : summary.participants()) {
      participants.next()
          .id(p.id())
          .name(p.name())
          .putPublicKey(p.publicKey(), 0, p.publicKey().length);
    }
    var records = encoder.tradeRecordsCount(summary.tradeRecords().size());
    for (var r : summary.tradeRecords()) {
      records.next().id(r.id()).participantId(r.participantId()).type(r.type());
      UUIDCodec.encode(r.uuid(), records.uuid());
      BigDecimalCodec.encodeBigDecimal(r.price(), records.price());
      BigDecimalCodec.encodeBigDecimal(r.quantity(), records.quantity());
    }

    return buffer;
  }

  public TradeSummary decode(MutableDirectBuffer directBuffer) {
    decoder.wrapAndApplyHeader(directBuffer, 0, headerDecoder);
    final var summary = new TradeSummary();
    summary.id(decoder.id())
        .symbol(decoder.symbol())
        .timestamp(decoder.timestamp())
        .totalTrades(decoder.totalTrades())
        .totalVolume(decoder.totalVolume());
    final var participants = decoder.participants();
    final var sumParticipants = new ArrayList<Participant>();
    while (participants.hasNext()) {
      final var p = participants.next();
      final var newPart = new Participant();
      final var pKey = new byte[p.publicKeyLength()];
      p.getPublicKey(pKey, 0, pKey.length);
      newPart.id(p.id()).name(p.name()).publicKey(pKey);
      sumParticipants.add(newPart);
    }
    final var records = decoder.tradeRecords();
    final var sumRecords = new ArrayList<TradeRecord>();
    while (records.hasNext()) {
      final var r = records.next();
      final var newRec = new TradeRecord();
      newRec.id(r.id()).participantId(r.participantId())
          .price(BigDecimalCodec.decodeBigDecimal(r.price()))
          .quantity(BigDecimalCodec.decodeBigDecimal(r.quantity())).type(r.type())
          .uuid(UUIDCodec.decode(r.uuid()));
      sumRecords.add(newRec);
    }
    summary.participants(sumParticipants);
    summary.tradeRecords(sumRecords);
    return summary;
  }
}
