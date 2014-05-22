package proxy.smartproxy.operator;

/** OperatorProxy, can perform at most 3 operation by RealOperator at a time.
 * 
 * @author benbai123
 *
 */
public class OperatorProxy implements Operator {
	private static Operator op = new RealOperator();
	private static Integer MAX_CONCURRENT_OP = 3;
	private static Integer count = 0;

	public void doOperation() {
		synchronized (MAX_CONCURRENT_OP) {
			if (count == MAX_CONCURRENT_OP) {
				System.out.println("At most " + MAX_CONCURRENT_OP + " operator(s) at a time, please wait.");
				try {
					MAX_CONCURRENT_OP.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			count++;
		}
		op.doOperation();
		synchronized (MAX_CONCURRENT_OP) {
			MAX_CONCURRENT_OP.notify();
			count--;
		}
	}
	
}
