package com.blogspot.benbai123.image.diff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

import com.blogspot.benbai123.image.Pixel;

/**
 * Diff 2 images and highlight differences by rectangle
 * 
 * TODO move implementation to impl class
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
	/** distance limitation of join points to area */
	private static int _maxDistance = 60;
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
		List<Point> differentPoints = findDifferentPoints(expected, actual);
		List<Rectangle> differentAreas = buildDifferentAreas(differentPoints);
		return differentAreas;
	}

	private static List<Point> findDifferentPoints(BufferedImage expected, BufferedImage actual) {
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

	private static boolean isDifferent(int x, int y, Pixel[][] expectedPixels, Pixel[][] actualPixels) {
		if (x >= expectedPixels.length || x >= actualPixels.length) return true;
		if (y >= expectedPixels[0].length || y >= actualPixels[0].length) return true;
		if (!expectedPixels[x][y].equals(actualPixels[x][y])) return true;
		return false;
	}

	private static List<Rectangle> buildDifferentAreas(List<Point> differentPoints) {
		final List<Rectangle> differentAreas = new ArrayList<Rectangle>();

		differentPoints.stream().forEach((p) -> {
			Optional<Rectangle> closestArea = findClosestAreaOfPoint(p, differentAreas);
			if (!closestArea.isPresent()) {
				Rectangle rect = new Rectangle(p.x, p.y, 1, 1);
				differentAreas.add(rect);
			} else {
				involvePointToArea(p, closestArea.get());
			}
		});
		return differentAreas;
	}

	private static Optional<Rectangle> findClosestAreaOfPoint(Point p, List<Rectangle> differentAreas) {
		return differentAreas.parallelStream().min(Comparator.comparing((rect) -> {
			return getDistanceFromPointToRect(p, rect);
		})).filter((rect) -> {
			return isNotTooFar(p, rect);
		});
	}

	private static boolean isNotTooFar(Point p, Rectangle rect) {
		return getDistanceFromPointToRect(p, rect) <= _maxDistance;
	}

	private static double getDistanceFromPointToRect(Point p, Rectangle rect) {
		int minX = new Double(rect.getMinX()).intValue();
		int minY = new Double(rect.getMinY()).intValue();
		int maxX = new Double(rect.getMaxX()).intValue();
		int maxY = new Double(rect.getMaxY()).intValue();
		int px = p.x;
		int py = p.y;
		int dx = IntStream.of(minX-px, 0, px-maxX)
				.max().orElseThrow(NoSuchElementException::new);
		int dy = IntStream.of(minY-py, 0, py-maxY)
				.max().orElseThrow(NoSuchElementException::new);
		return Math.sqrt(dx*dx+dy*dy);
	}

	private static void involvePointToArea(Point p, Rectangle rect) {
		int minX = Math.min(p.x, new Double(rect.getMinX()).intValue());
		int minY = Math.min(p.y, new Double(rect.getMinY()).intValue());
		int maxX = Math.max(p.x, new Double(rect.getMaxX()).intValue());
		int maxY = Math.max(p.y, new Double(rect.getMaxY()).intValue());
		rect.setLocation(minX, minY);
		rect.setSize(maxX-minX, maxY-minY);
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

		BufferedImage result = diffExpectedWithActual(expected, actual);
		ImageIO.write(result, "png", new File("output_image.png"));
		
		System.out.println("done");
	}
}
