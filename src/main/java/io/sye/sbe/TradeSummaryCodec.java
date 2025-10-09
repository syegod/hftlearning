package io.sye.sbe;

public class TradeSummaryCodec {

  private final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
  private final MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
  private final TradeSummaryEncoder summaryEncoder = new TradeSummaryEncoder();
  private final TradeSummaryDecoder summaryDecoder = new TradeSummaryDecoder();

}
