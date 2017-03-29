package ru.mera.samples.presentation.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageResource {
	
	@RequestMapping(value="/{imageId}", method=RequestMethod.GET)
	public String getImage(@PathVariable("imageId") long id) {
		return "Here is your image #" + id + ", sir!";
	}
	
}