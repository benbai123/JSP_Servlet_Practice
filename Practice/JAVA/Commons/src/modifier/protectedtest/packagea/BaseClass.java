package modifier.protectedtest.packagea;

/** Base class for protected modifier test
 * 
 * You can access protected method of a Class within
 * same package or by its subclass
 * 
 * regarding "by its subclass", you can do below in
 * subclasses that are not in same package:
 * 
 * super() // call protected super constructor directly via super keyword
 * super.protectedMethod() // call protected method in super class
 * this.protectedMethod() // call protected method in self
 * protectedMethod() // equals to this.protectedMethod
 * 
 * but cannot do below:
 * 
 * ProtectedSuperConstructor() // call protected super constructor directly
 * SuperConstructor().protectedMethod(); // call protected method by instance of super class
 * 
 * @author benbai123
 *
 */
public class BaseClass {
    public BaseClass () {
	System.out.println("public BaseClass " + this);
    }
    protected BaseClass (String test) {
	System.out.println("protected BaseClass " + this);
    }
    public void testMethod () {
	System.out.println("BaseClass # public void testMethod " + this);
    }
    protected void testMethod (String test) {
	System.out.println("BaseClass # protected int testMethod " + this);
    }
}
