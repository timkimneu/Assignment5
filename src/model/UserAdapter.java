package model;

import provider.model.User;

public class UserAdapter implements IUser {
  private final User user;

  public UserAdapter(User user) {
    this.user = user;
  }

  @Override
  public String name() {
    return user.getUsername();
  }
}
