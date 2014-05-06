package test.mbean;

/** MBean for TestBean
 * according to Standard MBean conventions, the class name
 * of the MBean for TestBean should be TestBeanMBean
 * 
 * @author benbai123
 *
 */
public interface TestBeanMBean {
    // operations, all methods that are not starts with get/set
    public void displayTime ();
    public String insertMsg (int idx, String msg);
    public void start ();
    public void stop ();

    // attributes
    public long getDelay () ;
    public void setDelay (long delay) ;
}
