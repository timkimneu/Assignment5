package model;

import provider.model.Event;

public class LocationAdapter implements ILocation {

  private final Event event;

  public LocationAdapter(Event event) {
    this.event = event;
  }

  @Override
  public boolean online() {
    return event.isOnline();
  }

  @Override
  public String place() {
    return event.getLocation();
  }
}
