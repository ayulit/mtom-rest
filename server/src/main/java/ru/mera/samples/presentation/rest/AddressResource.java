package ru.mera.samples.presentation.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public List<AddressDTO> getAllAddresses() {
        return addressService.readAll();
    }
    
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/{addressId}", method = RequestMethod.GET)
    public AddressDTO getAddress(@PathVariable("addressId") long id) {        
        AddressDTO addressDTO = addressService.read(id);        
        return addressDTO;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public AddressDTO addAddress(@RequestBody AddressDTO address) {
        addressService.create(address);
        return address;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{addressId}", method = RequestMethod.PUT)
    public AddressDTO updateAddress(@PathVariable("addressId") long id, @RequestBody AddressDTO updatedAddress) {

        // TODO implement try-catch with RecordNotFoundException
        AddressDTO addressDTO = addressService.read(id);
        
        addressDTO.setCountry(updatedAddress.getCountry());
        addressDTO.setRegion(updatedAddress.getRegion());
        addressDTO.setTown(updatedAddress.getTown());
        addressDTO.setStreet(updatedAddress.getStreet());
        addressDTO.setHouse(updatedAddress.getHouse());
        
        addressService.update(addressDTO);

        return addressDTO;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{addressId}", method = RequestMethod.DELETE)
    public void deleteAddress(@PathVariable("addressId") long id) {
            
        // TODO implement try-catch with RecordNotFoundException
        addressService.delete(id);
    }

}
