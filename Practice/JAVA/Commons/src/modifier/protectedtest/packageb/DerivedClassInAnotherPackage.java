package modifier.protectedtest.packageb;

import modifier.protectedtest.packagea.BaseClass;

/** Derived class for protected modifier test
 * 
 * @author benbai123
 *
 */
public class DerivedClassInAnotherPackage extends BaseClass {
    public DerivedClassInAnotherPackage () {
	// super();
	System.out.println("public DerivedClassInAnotherPackage " + this);
    }
    protected DerivedClassInAnotherPackage (String test) {
	super (test);
	System.out.println("public DerivedClassInAnotherPackage " + this);
    }

    void publicTest () {
	System.out.println("\t\t ========== DerivedClassInAnotherPackage # void publicTest " + this);
	new BaseClass();

	new BaseClass().testMethod();
	
	super.testMethod();
	testMethod();
    }
    void protectedTest (String test) {
	System.out.println("\t\t ========== DerivedClassInAnotherPackage # void protectedTest " + this);
	// error,
	// cannot call protected constructor of BaseClass directly
	// new BaseClass(test);

	// error,
	// cannot call protected method by instance of BaseClass itself
	// new BaseClass().testMethod("test");
	
	// correct, call protected method in
	// BaseClass via super keyword
	super.testMethod(test);
	// correct, = this.testMethod
	// call protected method by self
	// (i.e. subclass of BaseClass)
	testMethod(test);
    }
    public static void main (String args[]) {
	new DerivedClassInAnotherPackage().publicTest();
	new DerivedClassInAnotherPackage("test").protectedTest("test");
    }
}
