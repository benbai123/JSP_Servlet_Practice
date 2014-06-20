package test;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/** Test for WeakReference
 * 
 * Ref:
 * 		https://weblogs.java.net/blog/2006/05/04/understanding-weak-references
 * 
 * @author benbai123
 *
 */
public class WeakReferenceTest {
	public static void main (String args[]) throws InterruptedException {
		System.out.println("test WeakReference");
		// strong reference of an integer
		Integer iS = new Integer(0);
		// weak reference with strong reference (the Integer above)
		WeakReference<Integer> iW = new WeakReference<Integer>(iS);
		// weak reference without strong reference
		WeakReference<Integer> iW2 = new WeakReference<Integer>(new Integer(1));
		// weak hash map
		Map<Integer, String> wM = new WeakHashMap<Integer, String>();
		// put value with strong reference key
		wM.put(iS, "aaa");
		// put value without strong reference key
		wM.put(new Integer(2), "bbb");

		// output strong reference
		System.out.println("iS = " + iS);
		// output weak reference
		System.out.println("iW = " + iW.get());
		System.out.println("iW2 = " +iW2.get());
		// output weak hash map
		System.out.println("wM[0] = " + wM.get(0));
		System.out.println("wM[2] = " + wM.get(2));
		System.out.println("\nfirst GC\n");
		// call gc and wish it do its work soon
		Runtime.getRuntime().gc();
		// wait for gc
		Thread.sleep(1000);

		// weak reference is removed as soon as no strong reference exists.
		// iW still has strong reference (iS) so still exists,
		// iW2 becomes null
		System.out.println("iS = " + iS);
		System.out.println("iW = " + iW.get());
		System.out.println("iW2 = " +iW2.get());
		// 0 is the key with strong reference, still exists
		System.out.println("wM[0] = " + wM.get(0));
		// 2 is the key without strong reference, is removed
		System.out.println("wM[2] = " + wM.get(2));

		// remove strong reference
		System.out.println("\nremove iS");
		iS = null;
		System.out.println("second GC\n");
		// call gc
		Runtime.getRuntime().gc();
		// wait for gc
		Thread.sleep(1000);
		// iW also becomes null now
		System.out.println("iS = " + iS);
		System.out.println("iW = " + iW.get());
		System.out.println("iW2 = " +iW2.get());
		// the key "0" is also removed
		System.out.println("wM[0] = " + wM.get(0));
		System.out.println("wM[2] = " + wM.get(2));
	}
}
