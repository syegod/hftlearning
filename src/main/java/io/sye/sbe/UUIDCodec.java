package io.sye.sbe;

import java.util.UUID;

public class UUIDCodec {

  private UUIDCodec() {
  }

  public static void encode(UUID uuid, UUIDEncoder encoder) {
    encoder.leastSigBits(uuid.getLeastSignificantBits());
    encoder.mostSigBits(uuid.getMostSignificantBits());
  }

  public static UUID decode(UUIDDecoder decoder) {
    return new UUID(decoder.mostSigBits(), decoder.leastSigBits());
  }
}
