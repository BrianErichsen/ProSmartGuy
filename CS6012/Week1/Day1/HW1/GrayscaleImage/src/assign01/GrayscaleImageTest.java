package assign01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrayscaleImageTest {

    private GrayscaleImage smallSquare;
    private GrayscaleImage smallWide;

    @BeforeEach
    void setUp() {
        smallSquare = new GrayscaleImage(new double[][]{{1,2},{3,4}});
        smallWide = new GrayscaleImage(new double[][]{{1,2,3},{4,5,6}});
    }

    @Test
    void testGetPixel(){
        assertEquals(smallSquare.getPixel(0,0), 1);
        assertEquals(smallSquare.getPixel(1,0), 2);
        assertEquals(smallSquare.getPixel(0,1), 3);
        assertEquals(smallSquare.getPixel(1,1), 4);

    }

    @Test
    void testEquals() {
        assertEquals(smallSquare, smallSquare);
        var equivalent = new GrayscaleImage(new double[][]{{1,2},{3,4}});
        var equivalent1 = new GrayscaleImage(new double[][]{{2,2},{4,4}});
        assertEquals(smallSquare, equivalent);
        assertNotEquals(smallSquare, equivalent1);
    }

    @Test
    void averageBrightness() {
        assertEquals(smallSquare.averageBrightness(), 2.5, 2.5*.001);
        var bigZero = new GrayscaleImage(new double[1000][1000]);
        assertEquals(bigZero.averageBrightness(), 0);

        //makes sure that average brightness is within normal range
        GrayscaleImage image = smallWide.normalized();
        double image2 = image.averageBrightness();;
        assertTrue(image2 >= 0 && image2 <= 127);
    }

    @Test
    void normalized() {
        var smallNorm = smallSquare.normalized();
        assertEquals(smallNorm.averageBrightness(), 127, 127*.001);
        var scale = 127/2.5;
        var expectedNorm = new GrayscaleImage(new double[][]{{scale, 2*scale},{3*scale, 4*scale}});
        for(var row = 0; row < 2; row++){
            for(var col = 0; col < 2; col++){
                assertEquals(smallNorm.getPixel(col, row), expectedNorm.getPixel(col, row),
                        expectedNorm.getPixel(col, row)*.001,
                        "pixel at row: " + row + " col: " + col + " incorrect");
            }
        }
    }

    @Test
    void mirrored() {
        var expected = new GrayscaleImage(new double[][]{{2,1},{4,3}});

        var mirror = smallSquare.mirrored();
        var mirrorTwice = mirror.mirrored();

        //Asserts that mirrored twice is what is expected
        assertEquals(smallSquare.mirrored(), expected);
        assertEquals(mirrorTwice, expected.mirrored());
    }

    @Test
    void cropped() {
        var cropped = smallSquare.cropped(1,1,1,1);
        assertEquals(cropped, new GrayscaleImage(new double[][]{{4}}));

    }
    @Test
    void testCropped() {
        assertThrows(IllegalArgumentException.class, () -> {smallWide.cropped(-1,1, -2, 0);});
    }

    @Test
    void squarified() {
        //Add if it is squarified already
        var squared = smallWide.squarified();
        var expected = new GrayscaleImage(new double[][]{{1,2},{4,5}});
        //Test for an already square image
        var alreadySquare = new GrayscaleImage(new double[][]{{2,2},{2,2}});
        var expected2 = alreadySquare.squarified();
        assertEquals(squared, expected);
        assertEquals(alreadySquare, expected2);
    }

    @Test
    void testGetPixelThrowsOnNegativeX(){
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(-1,0);});
    }
}

