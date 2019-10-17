package basic.thread.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import basic.thread.wrapper.DebounceRunnable;
import basic.thread.wrapper.ThrottleRunnable;

public class ThreadUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/** Make a runnable become debounce
	 * 
	 * usage: to reduce the real processing for some task
	 * 
	 * example: the stock price sometimes probably changes 1000 times in 1 second,
	 * 	but you just want redraw the candlestick of k-line chart after last change+"delay ms"
	 * 
	 * @param realRunner Runnable that has something real to do
	 * @param delay milliseconds that realRunner should wait since last call
	 * @return
	 */
	public static Runnable debounce (Runnable realRunner, long delay) {
		return DebounceRunnable.wrap(realRunner, delay);
	}
	/**
	 * pass true to immediate to prevent a runnable called multiple times
	 * 
	 * @param realRunner
	 * @param delay
	 * @param immediate
	 * @return
	 */
	public static Runnable debounce (Runnable realRunner, long delay, boolean immediate) {
		return DebounceRunnable.wrap(realRunner, delay, immediate);
	}
	/** Make a runnable become throttle
	 * 
	 * usage: to smoothly reduce running times of some task
	 * 
	 * example: assume the price of a stock often updated 1000 times per second
	 * but you want to redraw the candlestick of k-line at most once per 300ms
	 * 
	 * @param realRunner
	 * @param delay
	 * @return
	 */
	public static Runnable throttle (Runnable realRunner, long delay) {
		return ThrottleRunnable.wrap(realRunner, delay);
	}

	public static String getTime() {
		return sdf.format(new Date());
	}
}
