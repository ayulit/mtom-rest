package ru.mera.samples.presentation.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.application.service.AddressService;
import ru.mera.samples.application.service.ImageService;
import ru.mera.samples.application.service.UserService;
import ru.mera.samples.infrastructure.config.MtomServerConfiguration;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(new Object[] {WebApplication.class, MtomServerConfiguration.class}, args);

        ImageService imageService = context.getBean(ImageService.class);
        UserService userService = context.getBean(UserService.class);
        AddressService addressService = context.getBean(AddressService.class);
        
        String fileName = "fry.png";
        //String fileName = "spring-ws-logo.png";
        //String fileName = "bender.png";

        try {

            imageService.read(1L);

        } catch (RecordNotFoundException e) {

            
            // Way of loading classpath resources
            Resource resource = context.getResource("classpath:" + fileName);
            File file = null;

            try {
                file = resource.getFile();
            } catch (IOException e2) {
                System.err.println("File [" + fileName + "] does not exist");
                e2.printStackTrace();
                System.exit(-1);
            }

            try {

                // The line that reads the image file. Throws IOException.
                BufferedImage image = ImageIO.read(file);

                ImageDTO dtObject = new ImageDTO();

                dtObject.setName(fileName);
                dtObject.setImage(image);

                // Persisting image to DB here ...
                imageService.create(dtObject);
                
                
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setCountry("Planet Tatuin");
                addressDTO.setRegion("West Wastelands");
                addressDTO.setTown("Desert town");
                addressDTO.setStreet("Tumbleweed str");
                addressDTO.setHouse("1b");
                
//                Map<Long, String> residents = new HashMap<>();
//                residents.put(1L, "Luke Skywalker");
//                residents.put(2L, "C3PO");
//                addressDTO.setResidents(residents);

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
//                userDTO.setAddress("What this for?");

                userDTO.setAddressId(1L);
               
                userService.create(userDTO);

                // XXX Way to see how JSON will look
//                String serializedImage = new ObjectMapper().writeValueAsString(dtObject);
//                System.out.println("serialized image" + serializedImage);
                

            } catch (IOException e1) {
                
                // Log the exception. Re-throw if desired.
                e1.printStackTrace();
            }

        }

        System.out.println("Done.");
	}

}
