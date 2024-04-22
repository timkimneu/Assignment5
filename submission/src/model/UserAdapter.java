package model;

import provider.model.User;

import java.util.Objects;

public class UserAdapter implements User {
  private final IUser user;

  public UserAdapter(IUser user) {
    this.user = user;
  }

  @Override
  public String getUsername() {
    return user.name();
  }

  @Override
  public String toString() {
    return user.name();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof User) {
      return this.user.name().equals(((User) other).getUsername());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(((User)this).getUsername());
  }
}
