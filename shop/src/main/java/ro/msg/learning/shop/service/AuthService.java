package ro.msg.learning.shop.service;

import ro.msg.learning.shop.dto.AuthResponse;
import ro.msg.learning.shop.entity.UserAccount;

public interface AuthService {
    AuthResponse login(String username, String password);
    UserAccount register(UserAccount userAccount);
}
