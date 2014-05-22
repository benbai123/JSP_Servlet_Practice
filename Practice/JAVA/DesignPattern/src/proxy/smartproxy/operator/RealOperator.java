package proxy.smartproxy.operator;

/** RealOperator, implements Operator, will do some operation.
 * 
 * @author benbai123
 *
 */
public class RealOperator implements Operator {

	public void doOperation() {
		// assume some heavy operation here
		System.out.println("Do operation...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

}
