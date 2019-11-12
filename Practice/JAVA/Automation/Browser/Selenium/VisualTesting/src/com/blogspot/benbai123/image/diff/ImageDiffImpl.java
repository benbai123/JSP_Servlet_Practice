package com.blogspot.benbai123.image.diff;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.blogspot.benbai123.image.Pixel;

/**
 * Diff 2 Images and highlight differences
 * 
 * @author benbai123
 *
 */
public class ImageDiffImpl {
	/** it should look like... */
	private BufferedImage _expected;
	private Pixel[][] _expectedPixels;
	/** it actually looks like... */
	private BufferedImage _actual;
	private Pixel[][] _actualPixels;
	/** hmm...the differences? */
	private BufferedImage _result;
	/** something affect the diff result */
	private ImageDiffConfig _config = new ImageDiffConfig();
	private boolean _different = false;
	// getters/setters that violate get/set patterns :~|
	public ImageDiffConfig config () {
		return _config;
	}
	public ImageDiffImpl expected (BufferedImage expected) throws Exception {
		_expected = expected;
		_expectedPixels = Pixel.loadPixels(_expected);
		return this;
	}
	public ImageDiffImpl actual (BufferedImage actual) throws Exception {
		_actual = actual;
		_actualPixels = Pixel.loadPixels(_actual);
		return this;
	}
	public boolean different () {
		return _different;
	}
	public BufferedImage result () {
		return _result;
	}
	
	/**
	 * diff method, will generate result
	 * 
	 * @return
	 */
	public ImageDiffImpl diff () {
		_result = getInitialResult();
		List<Rectangle> differentAreas = findDifferentAreas();
		if (differentAreas.size() > 0) {
			_different = true;
		}
		markDifferentAreas(differentAreas);
		return this;
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
	private BufferedImage getInitialResult() {
		int width = Math.max(_expected.getWidth(), _actual.getWidth());
		int height = Math.max(_expected.getHeight(), _actual.getHeight());
		BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resultImage.createGraphics();
		g2d.drawImage(_actual, 0, 0, null);
		g2d.dispose();
		return resultImage;
	}
	/**
	 * Find different areas between expected and actual
	 * 
	 * separate "find different points" from build different areas
	 * for performance
	 * 
	 * @return
	 */
	private List<Rectangle> findDifferentAreas() {
		// Find all different points
		List<Point> differentPoints = findDifferentPoints();
		// then build different areas
		List<Rectangle> differentAreas = buildDifferentAreas(differentPoints);
		return differentAreas;
	}

	/**
	 * Find different pixels
	 * do it in parallel
	 * 
	 * @return
	 */
	private List<Point> findDifferentPoints() {
		int width = Math.max(_expected.getWidth(), _actual.getWidth());
		int height = Math.max(_expected.getHeight(), _actual.getHeight());
		// for each row (0 ~ height-1)
		List<Point> allDifferentPoints = IntStream.range(0, height).parallel().mapToObj((y) -> {
			// for each col (0 ~ width-1) (actually the pixel in row)
			List<Point> differentPoints = IntStream.range(0, width).parallel().filter((x) -> {
				// get only the different pixels
				return isDifferent(x, y);
			}).mapToObj((x) -> new Point(x, y)) // and build a Point for it
			// finally get a list of different Points for this row
			.collect(Collectors.toList());
			return differentPoints;
		}).flatMap(points -> points.stream()) // merge multiple list into one
				.collect(Collectors.toList());
		return allDifferentPoints;
	}

	/**
	 * check whether two Pixels are different
	 * 
	 * @param x location
	 * @param y
	 * 
	 * @return
	 */
	private boolean isDifferent(int x, int y) {
		// location out of bound
		if (x >= _expectedPixels.length || x >= _actualPixels.length) return true;
		if (y >= _expectedPixels[0].length || y >= _actualPixels[0].length) return true;
		// Pixel different
		return differentPixelValue(_expectedPixels[x][y], _actualPixels[x][y]);
	}

	private boolean differentPixelValue(Pixel expectedPixel, Pixel actualPixel) {
		if (_config.ignoreAlpha())
			return !Arrays.equals(expectedPixel.getRGB(), actualPixel.getRGB());
		return !expectedPixel.equals(actualPixel);
	}
	/**
	 * Build areas from different Points
	 * 
	 * process it one by one for more reasonable result
	 * 
	 * @param differentPoints
	 * @return
	 */
	private List<Rectangle> buildDifferentAreas(List<Point> differentPoints) {
		final List<Rectangle> differentAreas = new ArrayList<Rectangle>();

		// for all different Points
		differentPoints.stream().forEach((p) -> {
			// whether has an area for it?
			Optional<Rectangle> closestArea = findClosestAreaOfPoint(p, differentAreas);
			if (!closestArea.isPresent()) {
				// no -> add one
				Rectangle rect = new Rectangle(p.x, p.y, 1, 1);
				differentAreas.add(rect);
			} else {
				// yes -> update that area
				involvePointToArea(p, closestArea.get());
			}
		});
		return differentAreas;
	}

	/**
	 * Find closest area for the point
	 * 
	 * @param p
	 * @param differentAreas
	 * @return
	 */
	private Optional<Rectangle> findClosestAreaOfPoint(Point p, List<Rectangle> differentAreas) {
		// for all areas
		return differentAreas.parallelStream()
				// get closest one
				.min(Comparator.comparing((rect) -> {
					return getDistanceFromPointToRect(p, rect);} ))
				// filter it out if too far (distance larger than config.maxDistance)
				.filter((rect) -> {
					return isNotTooFar(p, rect);} );
	}

	/**
	 * Check whether a Point is too far from an area
	 * 
	 * @param p
	 * @param rect
	 * @return
	 */
	private boolean isNotTooFar(Point p, Rectangle rect) {
		return getDistanceFromPointToRect(p, rect)
				<= _config.maxDistance();
	}

	/**
	 * Get the distance from Point to area
	 * 
	 * @param p
	 * @param rect
	 * @return
	 */
	private static double getDistanceFromPointToRect(Point p, Rectangle rect) {
		// get min X/Y and max X/Y of area
		int minX = new Double(rect.getMinX()).intValue();
		int minY = new Double(rect.getMinY()).intValue();
		int maxX = new Double(rect.getMaxX()).intValue();
		int maxY = new Double(rect.getMaxY()).intValue();
		// get X/Y of Point
		int px = p.x;
		int py = p.y;
		// calculate distance
		int dx = IntStream.of(minX-px, 0, px-maxX)
				.max().orElseThrow(NoSuchElementException::new);
		int dy = IntStream.of(minY-py, 0, py-maxY)
				.max().orElseThrow(NoSuchElementException::new);
		return Math.sqrt(dx*dx+dy*dy);
	}

	/**
	 * Update location/dimension of area to include a Point
	 * 
	 * @param p
	 * @param rect
	 */
	private static void involvePointToArea(Point p, Rectangle rect) {
		int minX = Math.min(p.x, new Double(rect.getMinX()).intValue());
		int minY = Math.min(p.y, new Double(rect.getMinY()).intValue());
		int maxX = Math.max(p.x, new Double(rect.getMaxX()).intValue());
		int maxY = Math.max(p.y, new Double(rect.getMaxY()).intValue());
		rect.setLocation(minX, minY);
		rect.setSize(maxX-minX, maxY-minY);
	}

	/**
	 * Highlight different areas on result image
	 * 
	 * @param rects
	 */
	private void markDifferentAreas(List<Rectangle> rects) {
		// line width
		int lw = _config.lineWidth();
		// expend rect by config.margin+line width
		int margin = _config.margin()+lw;

		Graphics2D g2d = _result.createGraphics();
		g2d.setColor(_config.color());
		g2d.setStroke(new BasicStroke(lw));
		for (Rectangle rect : rects) {
			
			// margin left/top/right/bottom
			int ml = Math.min(rect.x-lw/2, margin);
			int mt = Math.min(rect.y-lw/2, margin);
			int mr = Math.min(_result.getWidth()-rect.x-rect.width-lw, margin);
			int mb = Math.min(_result.getHeight()-rect.y-rect.height-lw, margin);
			
			g2d.drawRect(rect.x-ml, rect.y-mt,
					rect.width+mr+ml, rect.height+mb+mt);
		}
		g2d.dispose();
	}

	/**
	 * Store config values
	 * 
	 * @author benbai123
	 *
	 */
	public class ImageDiffConfig {
		/** distance limitation of join points to area */
		private int _maxDistance = 60;
		/** margin for draw area */
		private int _margin = 10;
		/** stroke size for draw area */
		private int _lineWidth = 1;
		/** border color for draw area */
		private Color _color = Color.RED;
		/** ignore alpha value when diff */
		private boolean _ignoreAlpha = false;
		// violate getter/setter pattern for convenience :~|
		public int maxDistance () {
			return _maxDistance;
		}
		public ImageDiffConfig maxDistance (int maxDistance) {
			_maxDistance = maxDistance;
			return this;
		}
		public int margin () {
			return _margin;
		}
		public ImageDiffConfig margin (int margin) {
			_margin = margin;
			return this;
		}
		public int lineWidth () {
			return _lineWidth;
		}
		public ImageDiffConfig lineWidth (int lineWidth) {
			_lineWidth = lineWidth;
			return this;
		}
		public Color color () {
			return _color;
		}
		public ImageDiffConfig color (Color color) {
			_color = color;
			return this;
		}
		public ImageDiffConfig color (String nm) {
			_color = Color.decode(nm);
			return this;
		}
		public boolean ignoreAlpha () {
			return _ignoreAlpha;
		}
		public ImageDiffConfig ignoreAlpha (boolean ignoreAlpha) {
			_ignoreAlpha = ignoreAlpha;
			return this;
		}
	}
}
