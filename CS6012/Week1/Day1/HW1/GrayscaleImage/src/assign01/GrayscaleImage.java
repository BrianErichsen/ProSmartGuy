package assign01;
//by Brian Erichsen Fagundes - HW1 CS6012 - Nov, 2023;


import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * Represents a grayscale (black and white) image as a 2D array of "pixel" brightnesses
 * 255 is "white" 127 is "gray" 0 is "black" with intermediate values in between
 * Author: Brian Erichsen Fagundes
 */
public class GrayscaleImage {
    private double[][] imageData; // the actual image data


    /**
     * Initialize an image from a 2D array of doubles
     * This constructor creates a copy of the input array
     * @param data initial pixel values
     * @throws IllegalArgumentException if the input array is empty or "jagged" meaning not all rows are the same length
     */
    public GrayscaleImage(double[][] data){
        if(data.length == 0 || data[0].length == 0){
            throw new IllegalArgumentException("Image is empty");
        }

        imageData = new double[data.length][data[0].length];
        for(var row = 0; row < imageData.length; row++){
            if(data[row].length != imageData[row].length){
                throw new IllegalArgumentException("All rows must have the same length");
            }
            for(var col = 0; col < imageData[row].length; col++){
                imageData[row][col] = data[row][col];
            }
        }
    }
    public int getNumRows() {
        return this.imageData.length;
    }
    public int getNumCol() {
        return this.imageData[0].length;
    }

    /**
     * Fetches an image from the specified URL and converts it to grayscale
     * Uses the AWT Graphics2D class to do the conversion, so it may add
     * an item to your dock/menu bar as if you're loading a GUI program
     * @param url where to download the image
     * @throws IOException if the image can't be downloaded for some reason
     */
    public GrayscaleImage(URL url) throws IOException {
        var inputImage = ImageIO.read(url);
        //convert input image to grayscale
        //based on (https://stackoverflow.com/questions/6881578/how-to-convert-between-color-models)
        var grayImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d= grayImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, null);
        g2d.dispose();
        imageData = new double[grayImage.getHeight()][grayImage.getWidth()];

        //raster is basically a width x height x 1 3-dimensional array
        var grayRaster = grayImage.getRaster();
        for(var row = 0; row < imageData.length; row++){
            for(var col = 0; col < imageData[0].length; col++){
                //getSample parameters are x (our column) and y (our row), so they're "backwards"
                imageData[row][col] = grayRaster.getSampleDouble(col, row, 0);
            }
        }
    }

    public void savePNG(File filename) throws IOException {
        var outputImage = new BufferedImage(imageData[0].length, imageData.length, BufferedImage.TYPE_BYTE_GRAY);
        var raster = outputImage.getRaster();
        for(var row = 0; row < imageData.length; row++){
            for(var col = 0; col < imageData[0].length; col++){
                raster.setSample(col, row, 0, imageData[row][col]);
            }
        }
        ImageIO.write(outputImage, "png", filename);
    }

    ///Methods to be filled in by students below

    /**
     * Get the pixel brightness value at the specified coordinates
     * (0,0) is the top left corner of the image, (width -1, height -1) is the bottom right corner
     * @param x horizontal position, increases left to right
     * @param y vertical position, **increases top to bottom**
     * @return the brightness value at the specified coordinates
     * @throws IllegalArgumentException if x, y are not within the image width/height
     */
    //Top-left is 0-0 where first index (row y is 0) & second index (collumn x is 0)
    public double getPixel(int x, int y){
        //STUDENT Fill in this in to work correctly
        if (x < 0 || x >= imageData[0].length || y < 0 || y >= imageData.length) {
            throw new IllegalArgumentException("Coordinates x or y are outside of the image bounds");
        }
       return imageData[y][x];
    }

    /**
     * Two images are equal if they have the same size and each corresponding pixel
     * in the two images is exactly equal
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GrayscaleImage)) {
            return false;
        }

        GrayscaleImage otherImage = (GrayscaleImage) other;
        //returns true if
        //imageData[0] compares the number of rows in the two images
        if (this.imageData.length != otherImage.imageData.length ||
                this.imageData[0].length != otherImage.imageData[0].length) {
            return false;
        }
            //-------------------------------------------------------------
            for (int row = 0; row < this.imageData.length; row++) {
                for (int col = 0; col < this.imageData[0].length; col++) {
                    //Only returns true if each corresponding pixel is equal
                    if (this.imageData[row][col] != otherImage.imageData[row][col]) {
                        return false;
                    }
                }
            }
            //all pixels are equal
            return true;
    }//end of method bracket


    /**
     * Computes the average of all values in image data
     * @return the average of the imageData array
     */
    public double averageBrightness(){

        //Initialises sum to be 0.0 and calculates total pixels
        double sum = 0.0;
        int totalPixels = imageData.length * imageData[0].length;

        //Iterate throw row and col data and adds its data into sum
        for (int row = 0; row < imageData.length; row++) {
            for (int col = 0; col < imageData[0].length; col++) {
                sum += imageData[row][col];
            }
        }
        //calculates the average if data has input and returns it
        if (totalPixels > 0) {
            return sum / totalPixels;
        }
        else //empty image
            return 0.0;
    }
    /**
     * Return a new GrayScale image where the average new average brightness is 127
     * To do this, uniformly scale each pixel (ie, multiply each imageData entry by the same value)
     * Due to rounding, the new average brightness will not be 127 exactly, but should be very close
     * The original image should not be modified
     * @return a GrayScale image with pixel data uniformly rescaled so that its averageBrightness() is 127
     */
    public GrayscaleImage normalized(){
        double currentAverage = averageBrightness();
        double scalingFactor = 127.0 / currentAverage;

        GrayscaleImage normalImage = new GrayscaleImage(new double[imageData.length][imageData[0].length]);

        for (int row = 0; row < imageData.length; row++) {
            for (int col = 0; col < imageData[0].length; col++) {
                double scaledBrightness = imageData[row][col] * scalingFactor;
                normalImage.imageData[row][col] = scaledBrightness;
            }
        }
        return normalImage;
    }//end of method bracket


    /**
     * Returns a new grayscale image that has been "mirrored" across the y-axis
     * In other words, each row of the image should be reversed
     * The original image should be unchanged
     * @return a new GrayscaleImage that is a mirrored version of the this
     */
    public GrayscaleImage mirrored(){
        GrayscaleImage mirrorImage = new GrayscaleImage
                (new double[imageData.length][imageData[0].length]);
        for (int row = 0; row < imageData.length; row++) {
            //Gets the current row in reverse order
            double[] reversedRow = new double[imageData[0].length];
            for (int col = 0; col < imageData[1].length; col++) {
                //[][imageData[0].length - 1 - col; when col is 0- it is the index of last column;
                // getting second to last item
                reversedRow[col] = imageData[row][imageData[0].length - 1 - col];
            }
            mirrorImage.imageData[row] = reversedRow;
        }
        return mirrorImage;
    }

    /**
     * Returns a new GrayscaleImage of size width x height, containing the part of `this`
     * from startRow -> startRow + height, startCol -> startCol + width
     * The original image should be unmodified
     * @param startRow
     * @param startCol
     * @param width
     * @param height
     * @return A new GrayscaleImage containing the sub-image in the specified rectangle
     * @throws IllegalArgumentException if the specified rectangle goes outside the bounds of the original image
     */
    public GrayscaleImage cropped(int startRow, int startCol, int width, int height){
        if (startRow < 0 || startCol < 0 || startRow + height > imageData.length
        || startCol + width > imageData[0].length) {
            throw new IllegalArgumentException("Out of bounds; illegal argument");
        }
        //Creates the new croppedImage with the given dimensions
        GrayscaleImage croppedImage = new GrayscaleImage(new double[height][width]);
        //Iterates through specified rectangle
        for (int row = startRow; row < startRow + height; row++) {
            for (int col = startCol; col < startCol + height; col++) {
                //copies each pixel value from original data
                croppedImage.imageData[row - startRow][startCol - startCol] = imageData[row][col];
            }
        }
        return croppedImage;
    }

    /**
     * Returns a new "centered" square image (new width == new height)
     * For example, if the width is 20 pixels greater than the height,
     * this should return a height x height image, with 10 pixels removed from the left and right
     * edges of the image
     * If the number of pixels to be removed is odd, remove 1 fewer pixel from the left or top part
     * (note this convention should be SIMPLER/EASIER to implement than the alternative)
     * The original image should not be changed
     * @return a new, square, GrayscaleImage
     */
    public GrayscaleImage squarified(){
        int numberOfRows = imageData.length;
        int numberOfColumns = imageData[0].length;

        //if number of rows and number of columns is the same; that means that the image is already squarified
        if (numberOfRows == numberOfColumns) {
            return this;
        }

        // Calculate the size of the square side
        int size = Math.min(numberOfRows, numberOfColumns);

        // Calculate the number of pixels to remove from each side
        int pixelsToRemove = Math.abs(numberOfColumns - numberOfRows) / 2;

        // Create a new GrayscaleImage with the square side length
        GrayscaleImage squarifiedImage = new GrayscaleImage(new double[size][size]);

        // Determine the starting row and column for copying from the original image
        int startRow;
        int startColumn;

        if (numberOfRows > numberOfColumns) {
            startRow = 0;
            startColumn = pixelsToRemove;
        } else {
            startRow = pixelsToRemove;
            startColumn = 0;
        }

        // Iterate through the specified region and copy pixel values to the new square image
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                squarifiedImage.imageData[row][col] = this.imageData[startRow + row][startColumn + col];
            }
        }
        return squarifiedImage;
    }

}//end of class bracket
