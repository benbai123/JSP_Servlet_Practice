package com.blogspot.benbai123.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Utilities that process pixels in an image
 * 
 * Ref:
 * 	https://stackoverflow.com/questions/29301838/converting-bufferedimage-to-bytebuffer
 * 
 * @author benbai123
 *
 */
public class PixelUtils {
	/**
	 * 
	 * @return {_blue, _green, _red}
	 */
	public static byte[] getRGB (byte[] pixelBytes) {
		return new byte[] {pixelBytes[1], pixelBytes[2], pixelBytes[3]};
	}

	public static int intValue(byte[] pixelBytes) {
		int argb = 0;
		argb += (((int) pixelBytes[0] & 0xff) << 24); // alpha
		argb += ((int) pixelBytes[1] & 0xff); // blue
		argb += (((int) pixelBytes[2] & 0xff) << 8); // green
		argb += (((int) pixelBytes[3] & 0xff) << 16); // red
		return argb;
	}
	
	public static boolean equalsInt (byte[] pixelBytes, int argb) {
		return argb == intValue(pixelBytes);
	}
	
	public static boolean equals (byte[] expectedPixelBytes, byte[] actualPixelBytes) {
		return Arrays.equals(expectedPixelBytes, actualPixelBytes);
	}
	
	/**
	 * Load data of each pixel from BufferedImage
	 * 
	 * @param bufferedImage
	 * @return
	 * @throws Exception 
	 */
	public static byte[][][] loadPixels (BufferedImage bufferedImage) throws Exception {
		// byte array for pixels in image
		final byte[] pixelDatas = getImageBytes(bufferedImage);
		final int width = bufferedImage.getWidth();
		final int height = bufferedImage.getHeight();
		// whether has alpha
		final boolean hasAlphaChannel = bufferedImage.getAlphaRaster() != null;
		/* 4 bytes: [alpha]	[blue][green][red]
		 * 3 bytes: 		[blue][green][red]
		 */
		final int pixelLength = hasAlphaChannel? 4 : 3;
		byte[][][] pixels = new byte[width][height][4];
		// for each row (0 ~ height-1)
		IntStream.range(0, height).parallel().forEach((y) -> {
			// for each col (0 ~ width-1)
			IntStream.range(0, width).parallel().forEach((x) -> {
				// it really run in different threads :o
//				System.out.println(Thread.currentThread().getId()+": "+x+", "+y);
				byte[] argb = new byte[4];
				// get start index of byte array of current pixel
				int start = y*width*pixelLength + x*pixelLength;
				// assign alpha value
				argb[0] = hasAlphaChannel? pixelDatas[start] : (byte)255;
				// assign rgb
				IntStream.rangeClosed(1, 3).forEach((bidx) -> {
					/* has alpha: start+1~start+3
					 * no alpha: start~start+2
					 */
					argb[bidx] = hasAlphaChannel? pixelDatas[start+bidx] : pixelDatas[start-1+bidx];
				});
				pixels[x][y] = argb;
			});
		});
		return pixels;
	}
	/**
	 * Get byte array from dataBuffer of BufferedImage
	 * 
	 * @param dataBuffer
	 * @return
	 * @throws Exception
	 */
	private static byte[] getImageBytes (BufferedImage bufferedImage) throws Exception {
		DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
		if (dataBuffer instanceof DataBufferByte)
			return getBytes((DataBufferByte)dataBuffer);
		if (dataBuffer instanceof DataBufferUShort)
			return getBytes((DataBufferUShort)dataBuffer);
		if (dataBuffer instanceof DataBufferShort)
			return getBytes((DataBufferShort)dataBuffer);
		if (dataBuffer instanceof DataBufferInt)
			return getBytes((DataBufferInt)dataBuffer);
		throw new Exception("no proper method for dataBuffer "+dataBuffer.getClass());
	}
	private static byte[] getBytes (DataBufferByte dataBuffer) {
		return dataBuffer.getData();
	}
	private static byte[] getBytes (DataBufferUShort dataBuffer) {
		short[] pixelData = dataBuffer.getData();
		ByteBuffer byteBuffer = ByteBuffer.allocate(pixelData.length * 2);
		byteBuffer.asShortBuffer().put(ShortBuffer.wrap(pixelData));
		return byteBuffer.array();
	}
	private static byte[] getBytes (DataBufferShort dataBuffer) {
		short[] pixelData = dataBuffer.getData();
		ByteBuffer byteBuffer = ByteBuffer.allocate(pixelData.length * 2);
		byteBuffer.asShortBuffer().put(ShortBuffer.wrap(pixelData));
		return byteBuffer.array();
	}
	private static byte[] getBytes (DataBufferInt dataBuffer) {
		int[] pixelData = dataBuffer.getData();
		ByteBuffer byteBuffer = ByteBuffer.allocate(pixelData.length * 4);
		byteBuffer.asIntBuffer().put(IntBuffer.wrap(pixelData));
		return byteBuffer.array();
	}
}
