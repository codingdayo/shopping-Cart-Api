package com.codingdayo.shopping_cart.service.user;

import com.codingdayo.shopping_cart.Exceptions.AlreadyExistsException;
import com.codingdayo.shopping_cart.Exceptions.ResourceNotFoundException;
import com.codingdayo.shopping_cart.dto.UserDto;
import com.codingdayo.shopping_cart.model.User;
import com.codingdayo.shopping_cart.repository.UserRepository;
import com.codingdayo.shopping_cart.request.UserRequest;
import com.codingdayo.shopping_cart.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(UserRequest request) {
        return Optional.of(request).filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassWord());

                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException(request.getEmail() + " already exist!"));

    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
       return  userRepository.findById(userId)
               .map(existingUser -> {
                   existingUser.setFirstName(request.getFirstName());
                   existingUser.setLastName(request.getLastName());
                   return  userRepository.save(existingUser);
               }).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete,
                () -> {throw new ResourceNotFoundException("User not found");
        });
    }


//    helper method
    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
