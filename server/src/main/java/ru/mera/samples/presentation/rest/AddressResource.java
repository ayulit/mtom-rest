package ru.mera.samples.presentation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.application.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressResource {
    
    @Autowired
    private AddressService addressService;
    
    public AddressResource() {
        // Is it necessary? See Spring-REST-Book.
    }
    
    @RequestMapping(value="/{addressId}", method = RequestMethod.GET)
    public AddressDTO getAddress(@PathVariable("addressId") long id) {        
        AddressDTO addressDTO = addressService.read(id);        
        return addressDTO;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public AddressDTO addAddress(@RequestBody AddressDTO address) {
        addressService.create(address);
        return address;
    }

}
