package ru.mera.samples.presentation.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.application.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersResource {
    
    private static final Logger logger = LoggerFactory.getLogger(UsersResource.class);
    
    @Autowired
    private UserService userService;
    
    public UsersResource() {
        // Is it necessary? See Spring-REST-Book.
    }
    
    @PreAuthorize("hasRole('ADMIN')")
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

    @RequestMapping(value="/names/{name}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable("name") String name) {        
        UserDTO userDTO = userService.load(name);        
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
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutUser (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

}
