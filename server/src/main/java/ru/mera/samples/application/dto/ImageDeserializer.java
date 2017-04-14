package ru.mera.samples.application.dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ImageDeserializer extends JsonDeserializer<BufferedImage> {

    @Override
    public BufferedImage deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonNode node = p.getCodec().readTree(p);
        String base64 = node.asText();
        
        System.out.println("base64 =" + base64); 

        byte[] imageInByte = Base64.getDecoder().decode(base64);
        
        System.out.println("imageInByte =" + imageInByte.length); 
        
        BufferedImage resultImage = null;
        
        try {

            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(imageInByte);
            resultImage = ImageIO.read(in);
            System.out.println("resultImage " + resultImage); 
            System.out.println("im here ="); 
        } catch (IOException e) {
            System.out.println("FATAL"+ e.getMessage());
        }
        
        System.out.println("resultImage " + resultImage); 
        
        return resultImage;
    }

 

}
