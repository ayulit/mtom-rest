package ru.mera.samples.application.dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ImageSerializer extends JsonSerializer<BufferedImage> {

    @Override
    public void serialize(BufferedImage value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( value, "png", baos ); // png is lossless ?!
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            
            gen.writeObject(imageInByte);

            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        
    } 

}
