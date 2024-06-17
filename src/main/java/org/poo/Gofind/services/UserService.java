package org.poo.Gofind.services;

import org.poo.Gofind.dto.CreateUserDto;
import org.poo.Gofind.dto.LoginDto;
import org.poo.Gofind.models.User;
import org.poo.Gofind.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(long id){
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

//    public List<DeviceDto> getUserDevices(Long userId) {
//        return userRepository.findById(userId)
//            .map(user -> user.getDeviceList().stream()
//                .map(deviceService::toDeviceDto)
//                .collect(Collectors.toList()))
//            .orElse(null);
//    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
    public User register(CreateUserDto createUserDto){
        User user = new User();
        user.setName(createUserDto.getNom());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        return userRepository.save(user);
    }

    public Optional<User> update(Long id, CreateUserDto createUserDto){
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            user.setName(createUserDto.getNom());
            user.setEmail(createUserDto.getEmail());
            userRepository.save(user);
        }
        return userOpt;
    }
    public Optional<User> login(LoginDto loginDto) {
        Optional<User> userOpt = userRepository.findByEmail(loginDto.getEmail());
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if(user.getPassword().equals(loginDto.getPassword())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public CreateUserDto toUserDto(User user) {
        CreateUserDto userDto = new CreateUserDto();
        userDto.setId(user.getId());
        userDto.setNom(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
