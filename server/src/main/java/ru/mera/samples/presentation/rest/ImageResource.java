package ru.mera.samples.presentation.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.application.service.ImageService;

@RestController
@RequestMapping("/images")
public class ImageResource {

    private static final Logger logger = LoggerFactory.getLogger(ImageResource.class);
    
	@Autowired
	private ImageService imageService;
	
    public ImageResource() {}
	
    @RequestMapping(value="/{imageId}", method = RequestMethod.GET)
    public ImageDTO getImage(@PathVariable("imageId") long id) {

        ImageDTO imageDTO = imageService.read(id);

        BufferedImage bufferedImage = imageDTO.getImage();
        File outputfile = new File("image.png");
        logger.info("outputfile's path: " + outputfile.getAbsolutePath());
        try {
            ImageIO.write(bufferedImage, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return imageDTO;
        
        
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ImageDTO addImage(@RequestBody ImageDTO image) {
        imageService.create(image);
        return image;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{imageId}", method = RequestMethod.PUT)
    public ImageDTO updateImage(@PathVariable("imageId") long id, @RequestBody ImageDTO updatedImage) {

        ImageDTO imageDTO = imageService.read(id);
        
        imageDTO.setName(updatedImage.getName());
        imageDTO.setImage(updatedImage.getImage());
        
        imageService.update(imageDTO);

        return imageDTO;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{imageId}", method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("imageId") long id) {
        imageService.delete(id);
    }
    
}