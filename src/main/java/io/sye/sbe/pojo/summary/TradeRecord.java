package io.sye.sbe.pojo.summary;

//      <field name="tradeId" id="11" type="int64"/>
//      <field name="participantId" id="12" type="int64"/>
//      <field name="price" id="13" type="BigDecimal"/>
//      <field name="quantity" id="14" type="BigDecimal"/>
//      <field name="type" id="15" type="TradeType"/>
//      <field name="uuid" id="16" type="UUID"/>

import io.sye.sbe.TradeType;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class TradeRecord {

  private long id;
  private long participantId;
  private BigDecimal price;
  private BigDecimal quantity;
  private TradeType type;
  private UUID uuid;

  public long id() {
    return id;
  }

  public TradeRecord id(long id) {
    this.id = id;
    return this;
  }

  public long participantId() {
    return participantId;
  }

  public TradeRecord participantId(long participantId) {
    this.participantId = participantId;
    return this;
  }

  public BigDecimal price() {
    return price;
  }

  public TradeRecord price(BigDecimal price) {
    this.price = price;
    return this;
  }

  public BigDecimal quantity() {
    return quantity;
  }

  public TradeRecord quantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

  public TradeType type() {
    return type;
  }

  public TradeRecord type(TradeType type) {
    this.type = type;
    return this;
  }

  public UUID uuid() {
    return uuid;
  }

  public TradeRecord uuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TradeRecord that = (TradeRecord) o;
    return id == that.id && participantId == that.participantId && Objects.equals(price,
        that.price) && Objects.equals(quantity, that.quantity) && type == that.type
        && Objects.equals(uuid, that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, participantId, price, quantity, type, uuid);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TradeRecord.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("participantId=" + participantId)
        .add("price=" + price)
        .add("quantity=" + quantity)
        .add("type=" + type)
        .add("uuid=" + uuid)
        .toString();
  }
}
