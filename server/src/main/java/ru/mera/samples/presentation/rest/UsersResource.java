package ru.mera.samples.presentation.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.application.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersResource {
    
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(UsersResource.class);
    
    @Autowired
    private UserService userService;
    
    public UsersResource() {
    }
    
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> getAllUsers() {
        return userService.readAll();
    }
    
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/{userId}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable("userId") long id) {        
        UserDTO userDTO = userService.read(id);        
        return userDTO;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/names/{name}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable("name") String name) {        
        UserDTO userDTO = userService.load(name);        
        return userDTO;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public UserDTO addUser(@RequestBody UserDTO user) {
        userService.create(user);
        return user;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public UserDTO updateUser(@PathVariable("userId") long id, @RequestBody UserDTO updatedUser) {

        UserDTO userDTO = userService.read(id);
        
        userDTO.setLogin(updatedUser.getLogin());
        userDTO.setFirstName(updatedUser.getFirstName());
        userDTO.setLastName(updatedUser.getLastName());
        userDTO.setAddressId(updatedUser.getAddressId());
        
        userService.update(userDTO);

        return userDTO;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("userId") long id) {
        userService.delete(id);
    }

    @PreAuthorize("hasRole('USER')") 
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginSuccess (@RequestParam("success") boolean isSuccess) {
        if (isSuccess) {
            return "Login successful.";
        }
        return "Login error.";
    }

}
