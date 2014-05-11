package test;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import test.mbean.*;

/** Test class
 * 
 * @author benbai123
 *
 */
public class Test {
    private static MBeanServer _mbs;
    public static void main(String[] args) {
        try {
            // Get the Platform MBean Server
            _mbs = ManagementFactory.getPlatformMBeanServer();
            // create and register 3 Test Beans
            for (int i = 1; i <= 3; i++) {
                initMBean(i);
            }
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void initMBean (int i) throws Exception {
        // sub ID for TestBean
        String s = "TestBean_" + i;
        // Construct the ObjectName for the Test MBean we will register
        ObjectName mbeanName = new ObjectName("test.mbean:type="+s);
        // Create the Test MBean
        TestBean mbean = new TestBean(s);
        // start thread to do some task
        mbean.start();
        // Register the Test MBean
        _mbs.registerMBean(mbean, mbeanName);
        // Wait forever
        System.out.println("MBean registered.");
    }
}
