package com.khyati.fbclone.payloads.request;

import javax.validation.constraints.*;

public class SignupRequest {

  private String firstName;

  private String lastName;

  private String mobile;

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  private String profilePicUrl;

  public SignupRequest(String firstName, String lastName, String mobile,
      @NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
      @NotBlank @Size(min = 6, max = 40) String password, String profilePicUrl) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.mobile = mobile;
    this.username = username;
    this.email = email;
    this.password = password;
    this.profilePicUrl = profilePicUrl;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getProfilePicUrl() {
    return profilePicUrl;
  }

  public void setProfilePicUrl(String profilePicUrl) {
    this.profilePicUrl = profilePicUrl;
  }
}
