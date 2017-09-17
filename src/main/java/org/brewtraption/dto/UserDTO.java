package org.brewtraption.dto;


import java.io.Serializable;
import java.util.HashMap;

public class UserDTO implements Serializable {

  private static final long serialVersionUID = 1632374172575749275L;

  private String userName = "";
  private String firstName = "";
  private String lastName = "";
  private HashMap<String, String> customFields = new HashMap<>();

  public UserDTO() {
  }

  public void setUserName(final String userName) {
    this.userName = userName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public void setCustomFields(final HashMap<String, String> customFields) {
    this.customFields = customFields;
  }

  public String getUserName() {
    return userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public HashMap<String, String> getCustomFields() {
    return customFields;
  }

  public static class Builder {
    private String userName;
    private String firstName;
    private String lastName;
    private HashMap<String, String> customFields;

    public Builder userName(final String userName) {
      this.userName = userName;
      return this;
    }

    public Builder firstName(final String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(final String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder customFields(final HashMap<String, String> customFields) {
      this.customFields = customFields;
      return this;
    }

    public UserDTO build() {
      return new UserDTO(this);
    }
  }

  private UserDTO(final Builder builder) {
    userName = builder.userName;
    firstName = builder.firstName;
    lastName = builder.lastName;
    customFields = builder.customFields;
  }
}
