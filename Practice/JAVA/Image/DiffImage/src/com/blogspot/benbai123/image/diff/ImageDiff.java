package com.blogspot.benbai123.image.diff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * References
 * 	How do you clone a BufferedImage
 * 	https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
 * 
 * @author benbai123
 *
 */
public class ImageDiff {
	public static BufferedImage diffExpectedWithActual(BufferedImage expected, BufferedImage actual) throws Exception {
		BufferedImage copyOfActual = deepCopy(actual);
		List<Rectangle> differentAreas = findDifferentAreas(expected, actual);
		markDifferentAreas(copyOfActual, differentAreas);
		return copyOfActual;
	}

	private static BufferedImage deepCopy(BufferedImage src) {
		ColorModel colorModel = src.getColorModel();
		boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		WritableRaster raster = src.copyData(null);
		return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
	}

	private static List<Rectangle> findDifferentAreas(BufferedImage expected, BufferedImage actual) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void markDifferentAreas(BufferedImage image, List<Rectangle> rects) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.RED);
		for (Rectangle rect : rects) {
			g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
	    g2d.dispose();
	}
}
