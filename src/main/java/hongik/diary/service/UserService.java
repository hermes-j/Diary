package hongik.diary.service;

import hongik.diary.dto.LoginDTO;
import hongik.diary.dto.UserDTO;
import hongik.diary.entity.User;
import hongik.diary.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveEntity(User user) {
        return userRepository.save(user);
    }

    public User saveDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return saveEntity(user);
    }

    public boolean login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        User byUsername = userRepository.findByUsername(username);
        if (byUsername != null) {
            return byUsername.getPassword().equals(password);
        }
        return false;
    }
}
