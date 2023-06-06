package dev.amiami.Controller;

import dev.amiami.Exception.CustomException;
import dev.amiami.Model.AmiAmiUser;
import dev.amiami.Model.Tag;
import dev.amiami.Roles.Role;
import dev.amiami.Security.Jwt.JwtTokenUtil;
import dev.amiami.Security.Jwt.JwtUserDetailsService;
import dev.amiami.Service.TagService;
import dev.amiami.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtTokenUtil jwtTokenUtil;

        @Autowired
        private JwtUserDetailsService userDetailsService;

        @Autowired
        private  UserService userService;

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private TagService tagService;

        @CrossOrigin
        @PostMapping("/login")
        public ResponseEntity<String> login(String username, String password) throws Exception {
            userService.login(username, password);
            authenticate(username, password);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            final String token = jwtTokenUtil.generateToken(userDetails);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new Exception("Invalid username/password");
        }
    }
    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<String> register(String username, String email, String password) throws Exception {
        AmiAmiUser user = new AmiAmiUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.addRole(Role.USER);
        userService.createUser(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<ResponseEntity<AmiAmiUser>> user = userService.getUserById(id);
        if(user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
         userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AmiAmiUser> getUserById(@PathVariable Long id) {
        Optional<ResponseEntity<AmiAmiUser>> user = userService.getUserById(id);
        if (user.isEmpty()) {
            throw new CustomException("User not found");
     }
        return new ResponseEntity<>(user.get().getBody(), HttpStatus.OK);
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<AmiAmiUser> getUserByUsername(@PathVariable String username) {
        Optional<AmiAmiUser> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            throw new CustomException("User not found");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<AmiAmiUser>> getAllUsers() {
        return ResponseEntity.ok((List<AmiAmiUser>) userService.getAllUsers().getBody());

    }
}
