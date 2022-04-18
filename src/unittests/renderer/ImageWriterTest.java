package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void testImageWriter() {

        ImageWriter imgw = new ImageWriter("simple_view", 800, 500);

        for (int i = 0; i < imgw.getNx(); i++) {
            for (int j = 0; j < imgw.getNy(); j++) {

                 if (i % 50 == 0 || j % 50 == 0)
                    imgw.writePixel(i, j, new Color(0, 0, 0));
                else
                    imgw.writePixel(i, j, new Color(255, 20, 147));
            }
        }
        imgw.writeToImage();
    }
}

