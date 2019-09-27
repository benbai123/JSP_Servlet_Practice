package crypto.test.helper;

/**
 * Helper class for simplify performance testing.
 * 
 * @author benbai123
 *
 */
public abstract class PerformanceTestHelper {
	/** how many runs to do run method */
	private int _runs = 10;
	/** name to identify this test, for show result */
	private String _name = "";
	/** the time used of slowest run */
	private long _slowest;
	/** the time used of fastest run */
	private long _fastest;
	/** total time used */
	private long _total = 0;
	/** average time */
	private double _average;
	/** whether doTest is called with positive _runs */
	private boolean _tested = false;
	/** whether should do warm up run */
	private boolean _shuldWarmUp = false;
	// Constructor
	public PerformanceTestHelper (String name) {
		_name = name;
	}
	/**
	 * What you want to do for performance testing
	 * 
	 * @throws Exception
	 */
	public abstract void run () throws Exception ;
	/**
	 * Warm up run, call run method once by default
	 * override it as needed
	 * 
	 * @throws Exception
	 */
	public void warmup() throws Exception {
		run();
	}
	/**
	 * Test it
	 * 
	 * @return
	 * @throws Exception
	 */
	public PerformanceTestHelper doTest () throws Exception {
		if (_runs <= 0) return this;
		_tested = true;
		if (_shuldWarmUp) {
			warmup();
		}
		
		long start = System.nanoTime();
		for (int i = 0; i < _runs; i++) {
			run();
			long end = System.nanoTime();
			long used = end-start;
			if (i == 0 || used > _slowest) _slowest = used;
			if (i == 0 || used < _fastest) _fastest = used;
			_total = _total+(used);
			start = end;
		}
		_average = Double.valueOf(_total)/Double.valueOf(_runs);
		return this;
	}
	/**
	 * Output test result
	 */
	public void showResult () {
		if (!_tested) return;
		System.out.println("\n### Result of Performance Test "+_name
				+"\n\t Total runs: "+_runs
				+"\n\t Time used: "+nanoToMicro(_total)
				+"\n\t Fastest: "+nanoToMicro(_fastest)
				+"\n\t Slowest: "+nanoToMicro(_slowest)
				+"\n\t Average: "+nanoToMicro(_average) );
	}
	private static String nanoToMicro (long nano) {
		return nanoToMicro(Double.valueOf(nano));
	}
	private static String nanoToMicro (double nano) {
		return String.format("%.2f", nano/1000.0)+" micro seconds";
	}
	// setters
	public PerformanceTestHelper setRuns (int runs) {
		_runs = runs;
		return this;
	}
	public PerformanceTestHelper setShouldWarmUp (boolean shuldWarmUp) {
		_shuldWarmUp = shuldWarmUp;
		return this;
	}
}
