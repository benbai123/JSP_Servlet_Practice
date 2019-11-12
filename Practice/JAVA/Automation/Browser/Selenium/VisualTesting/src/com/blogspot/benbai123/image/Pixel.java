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
 * Class that represents a pixel in an image
 * 
 * @author benbai123
 *
 */
public class Pixel {
	private byte _alpha;
	private byte _red;
	private byte _green;
	private byte _blue;
	/**
	 * 
	 * @return {_alpha, _blue, _green, _red}
	 */
	public byte[] getData () {
		return new byte[] {_alpha, _blue, _green, _red};
	}
	/**
	 * 
	 * @return {_blue, _green, _red}
	 */
	public byte[] getRGB () {
		return new byte[] {_blue, _green, _red};
	}
	/**
	 * @param argb real order: 0/alpha, 1/blue, 2/green, 3/red
	 */
	public Pixel (byte[] argb) {
		_red = argb[3];
		_green = argb[2];
		_blue = argb[1];
		_alpha = argb[0];
	}
	public int intValue() {
		int argb = 0;
		argb += (((int) _alpha & 0xff) << 24); // alpha
		argb += ((int) _blue & 0xff); // blue
		argb += (((int) _green & 0xff) << 8); // green
		argb += (((int) _red & 0xff) << 16); // red
		return argb;
	}
	
	public boolean equalsInt (int argb) {
		return argb == intValue();
	}
	
	public boolean equals (Object obj) {
		if (obj instanceof Pixel) {
			Pixel another = (Pixel)obj;
			return Arrays.equals(getData(), another.getData());
		}
		return false;
	}
	
	/**
	 * Load data of each pixel from BufferedImage
	 * 
	 * @param bufferedImage
	 * @return
	 * @throws Exception 
	 */
	public static Pixel[][] loadPixels (BufferedImage bufferedImage) throws Exception {
		// byte array for pixels in image
		final byte[] pixelDatas = getImageBytes(bufferedImage.getRaster().getDataBuffer());
		final int width = bufferedImage.getWidth();
		final int height = bufferedImage.getHeight();
		// whether has alpha
		final boolean hasAlphaChannel = bufferedImage.getAlphaRaster() != null;
		/* 4 bytes: [alpha]	[blue][green][red]
		 * 3 bytes: 		[blue][green][red]
		 */
		final int pixelLength = hasAlphaChannel? 4 : 3;
		Pixel[][] pixels = new Pixel[width][height];
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
				pixels[x][y] = new Pixel(argb);
			});
		});
		return pixels;
	}
	private static byte[] getImageBytes (DataBuffer dataBuffer) throws Exception {
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
