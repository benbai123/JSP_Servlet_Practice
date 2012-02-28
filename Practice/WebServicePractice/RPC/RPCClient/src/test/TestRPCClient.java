package test;

import java.util.*;

import redstone.xmlrpc.XmlRpcCallback;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcStruct;
/**
 * test RPC call
 */
public class TestRPCClient {
	// the callback for async RPC
	private static XmlRpcCallback xrc = new XmlRpcCallback() {
		public void onException(XmlRpcException exception) {
			exception.printStackTrace();
		}
		public void onFault(int faultCode, java.lang.String faultMessage) {}
		public void onResult(java.lang.Object result) {
			System.out.println("########## Async RPC Callback ##########");
			// get the HelloClockMessage object by the response
			HelloClockMessage hcm = XmlToHelloClockMessage((XmlRpcStruct)result);

			// Show the value of created HelloClockMessage
			System.out.println(hcm.getMsg());
			System.out.println(hcm.getDate().getHours() + ":" + hcm.getDate().getMinutes());
		}
	};
	/**
	 * convert XmlRpcStruct to HelloClockMessage
	 * @param resp the XmlRpcStruct response
	 * @return HelloClockMessage object
	 */
	public static HelloClockMessage XmlToHelloClockMessage (XmlRpcStruct resp) {
		HelloClockMessage hcm =
			new HelloClockMessage((String)resp.get("msg"), (Date)resp.get("date"));
		return hcm;
	}
	public static void main( String[] args ) throws Exception {
		// create client
		XmlRpcClient client =
			new XmlRpcClient( "http://localhost:8080/RPCServer/xml-rpc", true);
		// invoke RPC method with param "Ben"
		// get the response
		XmlRpcStruct resp =
			(XmlRpcStruct)client
				.invoke( "HelloClockService.sayHello", new Object[] {"Ben"});
		// get the HelloClockMessage object by the response
		HelloClockMessage hcm = XmlToHelloClockMessage(resp);

		// show the Class of response parameter
		System.out.println(resp.get("date").getClass());
		System.out.println(resp.get("msg").getClass());

		// Show the value of created HelloClockMessage
		System.out.println(hcm.getMsg());
		System.out.println(hcm.getDate().getHours() + ":" + hcm.getDate().getMinutes());

		System.out.println();

		// invoke RPC Asynchronously
		client.invokeAsynchronously("HelloClockService.sayHello",
				new Object[] {"Benbai"}, xrc);
	}
}