package test;

import java.util.*;

/**
 * The message that will reply to client by
 * HelloClockService.sayHello
 */
public class HelloClockMessage {
	private String _msg;
	private Date _date;
	public HelloClockMessage () {
		
	}
	public HelloClockMessage (String name) {
		_msg = "Hello " + name;
		_date = new Date();
	}
	public HelloClockMessage (String msg, Date date) {
		_msg = msg;
		_date = date;
	}
	public String getMsg () {
		return _msg;
	}
	public Date getDate () {
		return _date;
	}
}