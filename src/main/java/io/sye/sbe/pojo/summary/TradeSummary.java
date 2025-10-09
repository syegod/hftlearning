package io.sye.sbe.pojo.summary;

import java.util.List;
import java.util.Objects;

public class TradeSummary {

  private long id;
  private String symbol;
  private long timestamp;
  private long totalTrades;
  private long totalVolume;
  private List<Participant> participants;
  private List<TradeRecord> tradeRecords;

  public long id() {
    return id;
  }

  public TradeSummary id(long id) {
    this.id = id;
    return this;
  }

  public String symbol() {
    return symbol;
  }

  public TradeSummary symbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public long timestamp() {
    return timestamp;
  }

  public TradeSummary timestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public long totalTrades() {
    return totalTrades;
  }

  public TradeSummary totalTrades(long totalTrades) {
    this.totalTrades = totalTrades;
    return this;
  }

  public long totalVolume() {
    return totalVolume;
  }

  public TradeSummary totalVolume(long totalVolume) {
    this.totalVolume = totalVolume;
    return this;
  }

  public List<Participant> participants() {
    return participants;
  }

  public TradeSummary participants(List<Participant> participants) {
    this.participants = participants;
    return this;
  }

  public List<TradeRecord> tradeRecords() {
    return tradeRecords;
  }

  public TradeSummary tradeRecords(List<TradeRecord> tradeRecords) {
    this.tradeRecords = tradeRecords;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TradeSummary that = (TradeSummary) o;
    return id == that.id && timestamp == that.timestamp && totalTrades == that.totalTrades
        && totalVolume == that.totalVolume && Objects.equals(symbol, that.symbol)
        && Objects.equals(participants, that.participants) && Objects.equals(
        tradeRecords, that.tradeRecords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, symbol, timestamp, totalTrades, totalVolume, participants,
        tradeRecords);
  }
}
