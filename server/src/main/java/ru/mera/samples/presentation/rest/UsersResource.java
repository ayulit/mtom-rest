package ru.mera.samples.presentation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.application.service.AddressService;
import ru.mera.samples.application.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersResource {
    
    @Autowired
    private UserService userService;
    
    public UsersResource() {
        // Is it necessary? See Spring-REST-Book.
    }
    
    @RequestMapping(value="/{userId}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable("userId") long id) {        
        UserDTO userDTO = userService.read(id);        
        return userDTO;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public UserDTO addUser(@RequestBody UserDTO user) {
        userService.create(user);
        return user;
    }
    
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public UserDTO updateUser(@PathVariable("userId") long id, @RequestBody UserDTO updatedUser) {

        // TODO implement try-catch with RecordNotFoundException
        UserDTO userDTO = userService.read(id);
        
        userDTO.setLogin(updatedUser.getLogin());
        userDTO.setFirstName(updatedUser.getFirstName());
        userDTO.setLastName(updatedUser.getLastName());
        userDTO.setAddressId(updatedUser.getAddressId());
        
        userService.update(userDTO);

        // FIXME will return old address string
        return userDTO;
    }
    
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("userId") long id) {
            
        // TODO implement try-catch with RecordNotFoundException
        userService.delete(id);
    }

}
