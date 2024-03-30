package com.ict.careus.service;

import com.ict.careus.dto.request.EditProfileRequest;
import com.ict.careus.model.user.User;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUser();

    Optional<User> findById(Long id);

    User editProfile (EditProfileRequest editProfileRequest) throws BadRequestException;
}
