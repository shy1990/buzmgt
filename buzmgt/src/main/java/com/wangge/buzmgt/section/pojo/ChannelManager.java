package com.wangge.buzmgt.section.pojo;

/**
 * Created by joe on 16-11-8.
 */
public class ChannelManager {

  private String userId;

  private String name;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ChannelManager{" +
            "userId='" + userId + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
