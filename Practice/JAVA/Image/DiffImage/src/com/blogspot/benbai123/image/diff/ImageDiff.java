package com.blogspot.benbai123.image.diff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import com.blogspot.benbai123.image.Pixel;

/**
 * References
 * 
 * How do you clone a BufferedImage
 * https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
 * 
 * Filling a Multidimensional Array using a Stream
 * https://stackoverflow.com/questions/26050530/filling-a-multidimensional-array-using-a-stream/26979389
 * 
 * @author benbai123
 *
 */
public class ImageDiff {
	public static BufferedImage diffExpectedWithActual(BufferedImage expected, BufferedImage actual) throws Exception {
		BufferedImage resultImage = getInitialResult(expected, actual);
		List<Rectangle> differentAreas = findDifferentAreas(expected, actual);
		markDifferentAreas(resultImage, differentAreas);
		return resultImage;
	}

	/**
	 * Get initial result image
	 * 
	 * Create a new blank image that larger than or equal to both expected and
	 * actual images in dimension, then overlay actual image onto it.
	 * 
	 * @param expected
	 * @param actual
	 * @return
	 */
	private static BufferedImage getInitialResult(BufferedImage expected, BufferedImage actual) {
		int width = Math.max(expected.getWidth(), actual.getWidth());
		int height = Math.max(expected.getHeight(), actual.getHeight());
		BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resultImage.createGraphics();
		g2d.drawImage(actual, 0, 0, null);
		g2d.dispose();
		return resultImage;
	}

	private static List<Rectangle> findDifferentAreas(BufferedImage expected, BufferedImage actual) {
		// TODO Auto-generated method stub
		List<Point> differentPoints = findDifferentPoints(expected, actual);
		List<Rectangle> differentAreas = buildDifferentAreas(differentPoints);
		return null;
	}

	private static List<Point> findDifferentPoints(BufferedImage expected, BufferedImage actual) {
		// TODO Auto-generated method stub
		int width = Math.max(expected.getWidth(), actual.getWidth());
		int height = Math.max(expected.getHeight(), actual.getHeight());
		Pixel[][] expectedPixels = Pixel.loadPixels(expected);
		Pixel[][] actualPixels = Pixel.loadPixels(actual);
		// for each row (0 ~ height-1)
		List<Point> allDifferentPoints = IntStream.range(0, height).parallel().mapToObj((y) -> {
			// for each col (0 ~ width-1) (actually the pixel in row)
			List<Point> differentPoints = IntStream.range(0, width).parallel().filter((x) -> {
				// get only the different pixels
				return isDifferent(x, y, expectedPixels, actualPixels);
			}).mapToObj((x) -> new Point(x, y)) // and build a Point for it
			// finally get a list of different Points for this row
			.collect(Collectors.toList());
			return differentPoints;
		}).flatMap(points -> points.stream()) // merge multiple list into one
				.collect(Collectors.toList());
		return allDifferentPoints;
	}

	private static List<Rectangle> buildDifferentAreas(List<Point> differentPoints) {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean isDifferent(int x, int y, Pixel[][] expectedPixels, Pixel[][] actualPixels) {
		if (x >= expectedPixels.length || x >= actualPixels.length) return true;
		if (y >= expectedPixels[0].length || y >= actualPixels[0].length) return true;
		if (!expectedPixels[x][y].equals(actualPixels[x][y])) return true;
		return false;
	}

	private static void markDifferentAreas(BufferedImage image, List<Rectangle> rects) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.RED);
		for (Rectangle rect : rects) {
			g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
		g2d.dispose();
	}

	public static void main(String[] args) throws Exception {
		BufferedImage expected = ImageIO.read(new File("aa.png"));
		BufferedImage actual = ImageIO.read(new File("cc.png"));
		List<Point> differentPoints = findDifferentPoints(expected, actual);
		for (int i = 0; i < 100; i++) {
			List<Point> differentPoints2 = findDifferentPoints(expected, actual);
			for (int j = 0; j < differentPoints.size(); j++) {
				if (differentPoints.get(j).getX() != differentPoints2.get(j).getX()
						|| differentPoints.get(j).getY() != differentPoints2.get(j).getY()) {
					System.out.println("different");
					break;
				}
			}
		}
		System.out.println("equal "+differentPoints.size());
		BufferedImage result = getInitialResult(expected, actual);
		ImageIO.write(result, "png", new File("output_image.png"));
	}
}
