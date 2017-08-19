package com.sample.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sample.domain.User;

@Repository
public class SampleRepository {

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    users.add(new User(Long.valueOf(0L), "user0"));
    users.add(new User(Long.valueOf(1L), "user1"));
    return users;
  }

  public User getUser(Long userId) {
    return new User(Long.valueOf(0L), "user0");
  }

}
