package com.blogspot.benbai123.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import javax.imageio.ImageIO;

/** Test some basic operation of image
 * 
 * ref
 * 	https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
 * 	https://www.deadcoderising.com/2015-05-19-java-8-replace-traditional-for-loops-with-intstreams/
 * 
 * @author benbai123
 *
 */
public class TestBasicOperations {
	private static BufferedImage _image;

	public static void main(String[] args) {
		try {
			testLoadImageToBufferedImage();
			// get data of pixels by method from reference
			int[][] intPixels = testLoadPixelsFromBufferedImage(_image);
			// get data of pixels by modified method
			Pixel[][] pixels = Pixel.loadPixels(_image);
			// verify my modification is working
			boolean equals = true;
			for (int x = 0; x < intPixels.length; x++) {
				for (int y = 0; y < intPixels[x].length; y++) {
					if (intPixels[x][y] != pixels[y][x].intValue()) {
						equals = false;
						break;
					}
				}
				if (!equals) break;
			}
			System.out.println("equals: "+equals);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testLoadImageToBufferedImage() throws Exception {
		// load image file
		_image = ImageIO.read(new File("aa.png"));
		// get size
		System.out.println("width: " + _image.getWidth());
		System.out.println("height: " + _image.getHeight());
	}
	
	/**
	 * the method get from reference
	 * ref
	 * 	https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
	 * @param image
	 * @return
	 */
	private static int[][] testLoadPixelsFromBufferedImage(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}
}
