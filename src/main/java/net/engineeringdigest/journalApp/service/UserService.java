package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER")); // Assign default role
        userRepository.save(user);
        logger.info("hahahahahaahah");
    }

    public void saveEntry(User user) {
        userRepository.save(user);
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(String.valueOf(id));
    }
    public void deleteById(ObjectId id) {
        userRepository.deleteById(id.toString());
    }
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
    public void deleteByUserName(String username) {
        userRepository.deleteByUsername(username);
    }
    //    public JournalEntry updateEntry(ObjectId id, User user) {
//        if (userRepository.existsById(id.toString())) {
//            user.setId(id);
//            return userRepository.save(user);
//        }
//        return null;
//    }
}
