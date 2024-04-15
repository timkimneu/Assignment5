package model;

import java.util.List;

public interface IEvent {

  String name();

  TimeImpl time();

  LocationImpl location();

  List<UserImpl> users();

  UserImpl host();

  boolean isHost(UserImpl user);

}
