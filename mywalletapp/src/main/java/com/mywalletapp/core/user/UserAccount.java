package com.mywalletapp.core.user;

import com.mywalletapp.core.entity.ObjectEntity;
import com.mywalletapp.core.entity.ObjectHome;

public class UserAccount extends ObjectEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 4112176087557741793L;

  private String login;
  private String password;
  private String firstName;
  private String lastName;
  private String salt;

  public UserAccount(long id, ObjectHome<?> home) {
    super(id, home);
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  @Override
  public boolean equals(Object arg) {
    if (!super.equals(arg)) {
      return false;
    }

    if (getClass() != arg.getClass()) {
      return false;
    }

    UserAccount obj = (UserAccount)arg;

    if (null != login ? !login.equals(obj.login) : null != obj.login) {
      return false;
    }
    if (null != password ? !password.equals(obj.password) : null != obj.password) {
      return false;
    }
    if (null != firstName ? !firstName.equals(obj.firstName) : null != obj.firstName) {
      return false;
    }
    if (null != lastName ? !lastName.equals(obj.lastName) : null != obj.lastName) {
      return false;
    }
    if (null != salt ? !salt.equals(obj.salt) : null != obj.salt) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int sup = 18;
    int result = sup * super.hashCode();
    result = sup * result + (null != login ? login.hashCode() : 0);
    result = sup * result + (null != password ? password.hashCode() : 0);
    result = sup * result + (null != firstName ? firstName.hashCode() : 0);
    result = sup * result + (null != lastName ? lastName.hashCode() : 0);
    result = sup * result + (null != salt ? salt.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(super.toString());
    sb.append(",");
    sb.append(null != login ? login : "{null}");
    sb.append(",");
    sb.append(null != password ? password : "{null}");
    sb.append(",");
    sb.append(null != firstName ? firstName : "{null}");
    sb.append(",");
    sb.append(null != lastName ? lastName : "{null}");
    sb.append(",");
    sb.append(null != salt ? salt : "{null}");
    sb.append(",");
    sb.append(null != created ? created : "{null}");
    sb.append(",");
    sb.append(null != modified ? modified : "{null}");
    return sb.toString();
  }

}
