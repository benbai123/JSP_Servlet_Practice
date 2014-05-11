package modifier.protectedtest.packagea;

import modifier.protectedtest.packagea.BaseClass;

/** Derived class for protected modifier test
 * 
 * @author benbai123
 *
 */
public class DerivedClassInSamePackage extends BaseClass {
    public DerivedClassInSamePackage () {
	// super();
	System.out.println("public DerivedClassInSamePackage " + this);
    }
    protected DerivedClassInSamePackage (String test) {
	super (test);
	System.out.println("public DerivedClassInSamePackage " + this);
    }

    void publicTest () {
	System.out.println("\t\t ========== DerivedClassInSamePackage # void publicTest " + this);
	new BaseClass();

	new BaseClass().testMethod();
	
	super.testMethod();
	testMethod();
    }
    void protectedTest (String test) {
	System.out.println("\t\t ========== DerivedClassInSamePackage # void protectedTest " + this);
	// correct, within same package
	new BaseClass(test);

	// correct, within same package
	new BaseClass().testMethod("test");
	
	// correct, call protected method in
	// BaseClass via super keyword
	super.testMethod(test);
	// correct, = this.testMethod
	// call protected method by self
	// (i.e. subclass of BaseClass)
	testMethod(test);
    }
    public static void main (String args[]) {
	new DerivedClassInSamePackage().publicTest();
	new DerivedClassInSamePackage("test").protectedTest("test");
    }
}
