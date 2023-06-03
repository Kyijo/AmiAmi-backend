package dev.amiami.Service.Impl;

import dev.amiami.Exception.CustomException;
import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements dev.amiami.Service.UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");
        return pattern.matcher(email).matches();
    }
    @Override
        public void createUser (AmiAmiUser user) {
        if(user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new CustomException("Username, email, and password are required");
        }
            if (user.getUsername().length() < 3 || user.getUsername().length() > 20) {
                throw new CustomException("Username must be between 3 and 20 characters");
            }
            if (!validateEmail(user.getEmail())) {
                throw new CustomException("Invalid email");
            }
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new CustomException("Username already exists");
            }
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new CustomException("Email already exists");
            }
             String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
             user.setPassword(encodedPassword);
        userRepository.save(user);
        }

        @Override
        public void login (String username, String password) {
        Optional<AmiAmiUser> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new CustomException("User not found");
        }
        if(userOptional.get().getUsername() == null || userOptional.get().getPassword() == null) {
            throw new CustomException("Username and password are required");
        }
        AmiAmiUser user = userOptional.get();
        if (!verifyPassword(password, user.getPassword())) {
            throw new CustomException("Invalid password");
        }
    }
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
    @Override
    public ResponseEntity<AmiAmiUser> updateUser(AmiAmiUser user) {
        // TODO: Implement this method to update user
        return null;
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<ResponseEntity<AmiAmiUser>> getUserById(Long userId) {
        return Optional.of(ResponseEntity.of(userRepository.findById(userId)));
    }

    @Override
    public Optional<AmiAmiUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<List<AmiAmiUser>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
