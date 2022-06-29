package com.khyati.fbclone.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @Size(max = 20)
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Size(max = 20)
  @Column(name = "last_name")
  private String lastName;

  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "username", length = 50, unique = true)
  private String username;

  @NotNull
  @Size(max = 120)
  @Column(name = "password")
  private String password;

  @NotNull
  @Size(max = 30)
  @Email
  @Column(name = "email", unique = true)
  private String email;

  @Size(max = 15)
  @Column(name = "mobile")
  private String mobile;

  @Column(name = "is_verified")
  private boolean isVerified;

  @Size(max = 100)
  @Column(name = "profile_pic_url")
  private String profilePicUrl;

  public User() {
  }

  public User(String firstName, String lastName, String username, String password, String email, String mobile,
      String profilePicUrl, boolean isVerified) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
    this.mobile = mobile;
    this.profilePicUrl = profilePicUrl;
    this.isVerified = isVerified;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean isVerified) {
    this.isVerified = isVerified;
  }

  public String getProfilePicUrl() {
    return profilePicUrl;
  }

  public void setProfilePicUrl(String profilePicUrl) {
    this.profilePicUrl = profilePicUrl;
  }
}
