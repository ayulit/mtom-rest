package ru.mera.samples.infrastructure.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.application.service.AddressService;
import ru.mera.samples.application.service.ImageService;
import ru.mera.samples.application.service.UserService;

public class DbFillingBean {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DbFillingBean.class);

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @PostConstruct 
    public void init()
    {

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry("Planet Tatuin");
        addressDTO.setRegion("West Wastelands");
        addressDTO.setTown("Desert town");
        addressDTO.setStreet("Tumbleweed str");
        addressDTO.setHouse("1b");
        addressService.create(addressDTO);

        // creating second address
        addressDTO.setCountry("England");
        addressDTO.setRegion("London");
        addressDTO.setTown("City of London");
        addressDTO.setStreet("Baker str");
        addressDTO.setHouse("221b");              
        addressService.create(addressDTO);


        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("jedi");
        userDTO.setFirstName("Luke");
        userDTO.setLastName("Skywalker");
        userDTO.setAddressId(1L);
        userService.create(userDTO);

        // creating 2nd user
        userDTO.setLogin("obiwan");
        userDTO.setFirstName("Obi-Wan");
        userDTO.setLastName("Kenobi");
        userDTO.setAddressId(1L);
        userService.create(userDTO);

        // creating 3d user
        userDTO.setLogin("sherl");
        userDTO.setFirstName("Sherlock");
        userDTO.setLastName("Holmes");
        userDTO.setAddressId(2L);
        userService.create(userDTO);
        
        
        String fileName = "fry.png";
        
        Resource resource = new ClassPathResource(fileName);

        try {

            File file = resource.getFile();
            BufferedImage image = ImageIO.read(file);

            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setName(fileName);
            imageDTO.setImage(image);

            imageService.create(imageDTO);
            
        } catch (IOException e) {
            System.err.println("File [" + fileName + "] possibly does not exist");
            e.printStackTrace();
        }

    }    

}
