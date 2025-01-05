package com.shoppingcart.cart.service.user;

import com.shoppingcart.cart.model.User;
import com.shoppingcart.cart.request.CreateUserRequest;
import com.shoppingcart.cart.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

}

