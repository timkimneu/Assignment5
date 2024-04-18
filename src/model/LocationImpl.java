package model;

import java.util.Objects;

/**
 * Represents the location of an Event. A location is denoted by a place but can also be online.
 */
public class LocationImpl implements ILocation {
  private final boolean online;
  private final String place;

  /**
   * Instantiates the location of the place, which includes parameters of whether
   * the event is online, as well as where it is.
   *
   * @param online Boolean that describes whether the event is online or not
   * @param place  String that describes where the event takes place.
   */
  public LocationImpl(boolean online, String place) {
    this.online = online;
    this.place = place;
  }

  @Override
  public boolean online() {
    return this.online;
  }

  @Override
  public String place() {
    return this.place;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof LocationImpl)) {
      return false;
    }
    LocationImpl loc = (LocationImpl) other;
    return this.online == (loc.online) && this.place.equals(loc.place);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.place, this.online);
  }
}
