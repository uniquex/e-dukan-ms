package com.example.edukan.service;

import com.example.edukan.model.dto.TokenDto;
import com.example.edukan.model.dto.UserDto;
import com.example.edukan.model.exception.NotFoundException;
import com.example.edukan.model.exception.PasswordException;
import com.example.edukan.model.request.SignInRequest;
import com.example.edukan.model.request.UserRequest;
import com.example.edukan.entity.UserEntity;
import com.example.edukan.mapper.UserMapper;
import com.example.edukan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class UserService {

    SecretKey secretKey = generateSecretKey();

    @Autowired
    private CustomPasswordValidator passwordValidator;

    private final TokenService tokenService ;
    private final UserRepository repository;

    public UserService(TokenService tokenService,
                       UserRepository repository) throws Exception {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    public UserDto getUser(Long id) {
        log.info("Action.log.getUser method started");
        return UserMapper.INSTANCE.entityToDto(
                repository.findById(id).orElseThrow(() ->
                        new NotFoundException("THIS ID DOES NOT EXIST!"))
        );
    }

    public List<UserDto> getUsers() {
        return UserMapper.INSTANCE.entityToDtoList(repository.findAll());
    }

    public ResponseEntity<String> signUp(UserRequest request) {
        log.info("Action.log.signUp method started");

        String password = request.getPassword();
        if (!passwordValidator.validatePassword(password)){
            throw new PasswordException("Password does not comply with the rules");
        }

        UserEntity user = repository.findByEmail(request.getEmail());
        if ((user == null)) {
            user = UserMapper.INSTANCE.requestToEntity(request);
            user.setPassword(encrypt(request.getPassword(), secretKey));
            repository.save(user);
            log.info("User with email {} successfully signed up", request.getEmail());
            return new ResponseEntity<>("Sign up successful", HttpStatus.OK);
        } else {
            log.info("Can't sign up this email {} already signed up", request.getEmail());
            //@responseentity
            return new ResponseEntity<>("This email already signed up", HttpStatus.CONFLICT);
        }
    }

    public TokenDto signIn(SignInRequest request) {
        log.info("Action.log.signIn method started");

        String email = request.getEmail();
        String password = request.getPassword();
        UserEntity user = repository.findByEmail(email);

        if (user == null) {
            log.info("User with email {} not found", email);
            throw new NotFoundException("USER_NOT_FOUND_WITH_THIS_EMAIL");
        }

        if (!user.getPassword().equals(encrypt(password, secretKey))) {
            log.error("ActionLog.signIn.error invalid password");
            throw new NotFoundException("USER_NOT_FOUND_WITH_THIS_PASSWORD");
        }

        TokenDto tokenDto = tokenService.generateToken(user);

        log.info("User with email {} successfully signed in", email);
        return tokenDto;
    }

    public String deleteUser(Long id) {
        log.info("Action.log.deleteUser method started");
        UserEntity deletedUser = repository.findById(id).orElseThrow(() ->
                {
                    log.error("Action.log.error.deleteUser exception happened!");
                    return new NotFoundException("THIS ID DOES NOT EXIST!");
                }
        );
        repository.delete(deletedUser);
        log.info("Action.log.deleteUser method finished");
        return "User was deleted successfully!";
    }

    public ResponseEntity<UserEntity> updateUser(Long id,
                                                 UserRequest request) throws Exception {
        log.info("Action.log.updateUser method started");
        UserEntity updatedUser = repository.findById(id).orElseThrow(() -> {
                    log.error("Action.log.error.updateUser something went wrong");
                    return new NotFoundException("THIS ID DOES NOT EXIST!");
                }
        );
        if (!passwordValidator.validatePassword(request.getPassword())){
            throw new IllegalArgumentException("Password does not comply with the rules");
        }

        updatedUser.setName(request.getName());
        updatedUser.setSurname(request.getSurname());
        updatedUser.setEmail(request.getEmail());
        updatedUser.setPhone(request.getPhone());
        updatedUser.setAddress(request.getAddress());
        updatedUser.setPassword(request.getPassword());
        updatedUser.setPassword(encrypt(request.getPassword(), secretKey));

        repository.save(updatedUser);
        log.info("Action.log.info.updateUser method finished, user was updated successfully!");
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    public UserDto updatePassword(Long id,
                                  String password) throws Exception {
        log.error("Action.log.updatePassword method started");

        if (!passwordValidator.validatePassword(password)){
            throw new IllegalArgumentException("Password does not comply with the rules");
        }
        UserEntity user = repository.findById(id).orElseThrow(() ->
        {
            log.error("Action.log.error.updatePassword wrong id number!");
            return new NotFoundException("NO MATCH FOR GIVEN ID NUMBER!");
        });
        user.setPassword(encrypt(password, secretKey));
        repository.save(user);

        log.info("Action.log.updatePassword method finished, password was updated successfully!");
        return UserMapper.INSTANCE.entityToDto(user);
    }

    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static String encrypt(String text, SecretKey secretKey) {
        byte[] encryptedBytes;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
           encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            throw new PasswordException("NO_SUCH_ALGORITHM");
        }

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}