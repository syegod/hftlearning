package io.sye.sbe.pojo;

import io.sye.sbe.Currency;
import io.sye.sbe.Market;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class TradeData {

  private BigDecimal amount;
  private BigDecimal price;
  private Market market;
  private Currency currency;
  private String symbol;

  public BigDecimal amount() {
    return amount;
  }

  public TradeData amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public BigDecimal price() {
    return price;
  }

  public TradeData price(BigDecimal price) {
    this.price = price;
    return this;
  }

  public Market market() {
    return market;
  }

  public TradeData market(Market market) {
    this.market = market;
    return this;
  }

  public Currency currency() {
    return currency;
  }

  public TradeData currency(Currency currency) {
    this.currency = currency;
    return this;
  }

  public String symbol() {
    return symbol;
  }

  public TradeData symbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TradeData tradeData = (TradeData) o;
    return Objects.equals(amount, tradeData.amount) && Objects.equals(price,
        tradeData.price) && market == tradeData.market && currency == tradeData.currency
        && Objects.equals(symbol, tradeData.symbol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, price, market, currency, symbol);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TradeData.class.getSimpleName() + "[", "]")
        .add("amount=" + amount)
        .add("price=" + price)
        .add("market=" + market)
        .add("currency=" + currency)
        .add("symbol='" + symbol + "'")
        .toString();
  }
}
