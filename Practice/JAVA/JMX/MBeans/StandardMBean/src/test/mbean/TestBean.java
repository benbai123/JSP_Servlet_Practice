package test.mbean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.NotificationBroadcasterSupport;

/** TestBean
 * a common Java Bean contains several operations and getter/setter
 * 
 * @author benbai123
 *
 */
public class TestBean extends NotificationBroadcasterSupport implements TestBeanMBean, Runnable {
    /** a list of messages to display */
    private List<String> _msgs = new ArrayList<String>();
    /** delay time between each message */
    private long _delay = 1000;
    /** whether run while loop */
    private boolean _run = false;
    /** date formatter */
    private SimpleDateFormat _sdf = new SimpleDateFormat("HH:mm:ss");
    /** Bean ID */
    private String _id;

    public TestBean (String id) {
        _id = id;
    }
    // runnable
    public void run () {
        // loop until _run=false
        while (_run) {
            // create another list to display to
            // prevent concurrent modification
            List<String> currentMsgs = new ArrayList<String>();
            currentMsgs.addAll(_msgs);
            for (String s : currentMsgs) {
                if (_run) {
                    System.out.print(_id + ": " + s + "\t");
                    try {
                        long remaining = _delay;
                        for (long i = 0; i < _delay/3000; i++) {
                            if (_run) { // running
                                // sleep 1 second
                                Thread.sleep(3000);
                                remaining -= 3000;
                            } else {
                                // stop
                                remaining = 0;
                                break;
                            }
                        }
                        Thread.sleep(remaining);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("\n" + _id + " stopped.");
    }
    
    // operations
    /** show current time to console
     */
    public void displayTime() {
        System.out.print(_id + ": " + _sdf.format(new Date()) + "\t");
    }

    /** add message to display
     * @param idx, the position to insert
     * @param msg, the message to display
     * @return String, the insert result
     */
    public String insertMsg (int idx, String msg) {
        synchronized (_msgs) {
            // out of bound, append
            if (idx >= _msgs.size()) {
                _msgs.add(msg);
                idx = _msgs.size()-1;
            } else {
            // at least 0
                if (idx < 0) {
                    idx = 0;
                }
                _msgs.add(idx, msg);
            }
        }
        return "insert " + msg + " to index " + idx;
    }

    // create and start a Thread to run self task (run () method)
    public synchronized void start () {
        if (!_run) { // start if is not running
            _run = true;
            new Thread(this).start();
            System.out.println(_id + " started.");
        }
    }
    public void stop () {
        // change _run to false
        _run = false;
        System.out.println("Stopping " + _id + "...");
    }
    // getter, setter
    public long getDelay () {
        return _delay;
    }

    public synchronized void setDelay (long delay) {
        if (delay != _delay) {
            _delay = delay;
            System.out.println("\n" + _id + ": delay now " + delay);
        }
    }
}
