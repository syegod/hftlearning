package io.sye.sbe;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class TradeData {

  private int amount;
  private BigDecimal price;
  private Market market;
  private Currency currency;
  private String symbol;

  public int amount() {
    return amount;
  }

  public TradeData amount(int amount) {
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
    if (!(o instanceof TradeData that)) {
      return false;
    }
    return amount == that.amount && Objects.equals(price, that.price)
        && market == that.market && currency == that.currency && Objects.equals(symbol,
        that.symbol);
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
