package dev.amiami.Service;

import dev.amiami.Model.AmiAmiUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    void createUser(AmiAmiUser user);

    void login(String username, String password);

    ResponseEntity<AmiAmiUser> updateUser(AmiAmiUser user);

    void deleteUserById(Long userId);

    Optional<ResponseEntity<AmiAmiUser>> getUserById(Long userId);

    Optional<AmiAmiUser> getUserByUsername(String username);

    ResponseEntity<List<AmiAmiUser>> getAllUsers();

}
