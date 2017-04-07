package ru.mera.samples.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.mera.samples.application.service.ImageService;
import ru.mera.samples.config.MtomServerTestConfiguration;

/**
 * Test that the Spring wiring can be loaded.
 *
 * @author Ludovic Dewailly
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MtomServerTestConfiguration.class})
public class WiringTest {

    @Autowired
    private ImageService imageService;

    @Test
    public void test() {
        assertNotNull(imageService);
    }
    
    @Test
    public void testConcatenateInventory() {
        assertEquals("one", "two");
    }
}
