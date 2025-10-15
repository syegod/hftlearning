package io.sye.sbe.pojo.summary;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class Participant {

  private long id;
  private String name;
  private byte[] publicKey;

  public long id() {
    return id;
  }

  public Participant id(long id) {
    this.id = id;
    return this;
  }

  public String name() {
    return name;
  }

  public Participant name(String name) {
    this.name = name;
    return this;
  }

  public byte[] publicKey() {
    return publicKey;
  }

  public Participant publicKey(byte[] publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Participant that = (Participant) o;
    return id == that.id && Objects.equals(name, that.name) && Arrays.equals(
        publicKey, that.publicKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, Arrays.hashCode(publicKey));
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Participant.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("name='" + name + "'")
        .add("publicKey=" + Arrays.toString(publicKey))
        .toString();
  }
}
