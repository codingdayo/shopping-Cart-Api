package com.codingdayo.shopping_cart.service.user;

import com.codingdayo.shopping_cart.dto.UserDto;
import com.codingdayo.shopping_cart.model.User;
import com.codingdayo.shopping_cart.request.UserRequest;
import com.codingdayo.shopping_cart.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);

    User createUser(UserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);


    //    helper method
    UserDto convertUserToDto(User user);
}
