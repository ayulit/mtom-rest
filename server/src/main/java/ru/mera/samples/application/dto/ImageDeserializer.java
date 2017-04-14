package ru.mera.samples.application.dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ImageDeserializer extends JsonDeserializer<BufferedImage> {

    @Override
    public BufferedImage deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        byte[] imageInByte = p.getBinaryValue();
        
        BufferedImage resultImage = null;
        
        try {
            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(imageInByte);
            resultImage = ImageIO.read(in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        return resultImage;
    }

 

}
