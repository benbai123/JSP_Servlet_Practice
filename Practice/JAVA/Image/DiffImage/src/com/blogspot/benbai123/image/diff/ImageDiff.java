package com.blogspot.benbai123.image.diff;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Diff 2 images and highlight differences by rectangle
 * 
 * References
 * 
 * How do you clone a BufferedImage
 * https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
 * 
 * calculating distance between a point and a rectangular box
 * https://stackoverflow.com/questions/5254838/calculating-distance-between-a-point-and-a-rectangular-box-nearest-point
 * 
 * Filling a Multidimensional Array using a Stream
 * https://stackoverflow.com/questions/26050530/filling-a-multidimensional-array-using-a-stream/26979389
 * 
 * @author benbai123
 *
 */
public class ImageDiff {

	public static void main(String[] args) throws Exception {
		// expected image
		BufferedImage expected = ImageIO.read(new File("aa.png"));
		// different actual image
		BufferedImage actual = ImageIO.read(new File("cc.png"));
		// same image with different alpha value
		BufferedImage expectedWithDifferentAlpha = ImageIO.read(new File("dd.png"));
		
		BufferedImage result = diffExpectedWithActual(expected, actual);
		ImageIO.write(result, "png", new File("output_image.png"));
		
		result = diffExpectedWithActual(expected, expectedWithDifferentAlpha);
		ImageIO.write(result, "png", new File("output_image2.png"));
		
		// diff with ignore alpha (no difference in result image
		ImageDiffImpl imgDiff = new ImageDiffImpl();
		// update configs
		imgDiff.config().ignoreAlpha(true);
		result = imgDiff.expected(expected).actual(expectedWithDifferentAlpha)
				.diff().result();
		ImageIO.write(result, "png", new File("output_image3.png"));
		
		System.out.println("done");
	}

	private static BufferedImage diffExpectedWithActual(BufferedImage expected, BufferedImage actual) throws Exception {
		ImageDiffImpl imgDiff = new ImageDiffImpl();
		// update configs
		imgDiff.config().maxDistance(30).margin(5)
			.lineWidth(3).color("#ffd294");
		BufferedImage result = imgDiff
				.expected(expected) // the image should look like
				.actual(actual) // but actually...
				.diff() // tell me
				.result(); // what the f***ing differences?
		return result;
	}
}
