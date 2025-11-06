package ro.msg.learning.shop.service;

import ro.msg.learning.shop.entity.UserAccount;

public interface UserService {
    UserAccount findByUsername(String username);
}
