package org.poo.Gofind.controllers;

import org.poo.Gofind.dto.CreateUserDto;
import org.poo.Gofind.dto.DeviceDTO;
import org.poo.Gofind.dto.LoginDto;
import org.poo.Gofind.models.User;
import org.poo.Gofind.models.appareils.Category;
import org.poo.Gofind.models.appareils.Device;
import org.poo.Gofind.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public Iterable<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findOne(id)
            .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<List<DeviceDTO>> getDevicesByCategoryId(@PathVariable Long id) {
        Optional<User> userOpt = userService.findOne(id);
        if (userOpt.isPresent()) {
            List<Device> devices = userOpt.get().getDeviceList();
            // Conversion List<Device> en List<DeviceDTO>
            List<DeviceDTO> deviceDTOs = devices.stream()
                .map(device -> DeviceDTO.builder()
                    .imei(device.getImei())
                    .marque(device.getMarque())
                    .model(device.getModel())
                    .userId(device.getUsers() != null ? device.getUsers().getId() : null)
                    .categorieId(device.getCategories() != null ? device.getCategories().getId() : null)
                    .build())
                .collect(Collectors.toList());
            return new ResponseEntity<>(deviceDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody CreateUserDto createUserDto){
        return userService.register(createUserDto);
    }

    @PatchMapping("/{id}")
    public Optional<User> update(@RequestBody CreateUserDto createUserDto, @PathVariable Long id){
        return userService.update(id, createUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto)
            .map(user -> new ResponseEntity<>("Login successful", HttpStatus.OK))
            .orElse(new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
