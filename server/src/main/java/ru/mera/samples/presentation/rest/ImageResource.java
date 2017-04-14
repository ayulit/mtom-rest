package ru.mera.samples.presentation.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private ImageService imageService;
	
    // Is it necessary? See Spring-REST-Book.
    public ImageResource() {}
    
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String getTestString() {
		return "Hello!";
	}
	
    // retrieving an Image by identifier
    @RequestMapping(value="/{imageId}", method = RequestMethod.GET)  // like @GET in JAX-RS
    public ImageDTO getImage(@PathVariable("imageId") long id) {

        // Data Transfer Object (DTO) pattern:
        // It provides a useful decoupling between the persistence and presentation layers.
        ImageDTO imageDTO = imageService.read(id);

        // TODO IMPORTANT!!! Remove this block on production and while testing
/*        BufferedImage bufferedImage = imageDTO.getImage();
        File outputfile = new File("image.png");
        try {
            ImageIO.write(bufferedImage, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageDTO.setImage(null);*/
        
        return imageDTO;
        
        
    }

    @RequestMapping(method = RequestMethod.POST)
    public ImageDTO addImage(@RequestBody ImageDTO image) {
        imageService.create(image);
        return image;
    }
    
    
/*	@RequestMapping(method=RequestMethod.POST)
	public Response addImage(ImageDTO image, @Context UriInfo uriInfo) {
				
		ImageDTO newImage = imageService.addImage(image); 
		String newId = String.valueOf(newImage.getId());
		
		// we will use getAbsolutePathBuilder() for added resource URI construction
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build(); 
		
		// we will return Response builder
		// .created() instead of .status(Status.CREATED) to get 201 code
		// and send URI back
		return Response.created(uri)
		               .entity(newMessage)
		               .build();
	}*/
}