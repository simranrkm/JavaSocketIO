package com.sdk.jixee;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.json.JSONException;
import org.json.JSONObject;


public class SocketIOService {

	static {
	    // for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier() {
	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost")) {
	                return true;
	            }
	            return false;
	        }
	    });
	} 
	
	public static void main(String[] args){
		System.out.println( "Conecting to: ");
		
		SocketIOService sis = new SocketIOService();
		sis.connect();
	}
	
	public void connect(){
		/* Connect to websocket */
		SocketIO socket;
		try {
			System.out.println( "Conecting to: https://local.jixee.me");
			
			try {
				SocketIO.setDefaultSSLSocketFactory(SSLContext.getInstance("Default"));
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			socket = new SocketIO( "https://local.jixee.me:443" );
			
			//String cookie = CookieManager.getInstance().getCookie(GlobalVar.getInstance().getHttpHost());
			//socket.addHeader("Cookie", GlobalVar.getInstance().getCookieId()+"=s:0duXkphXSLzjQa-x86F7hm-0.HH/S1Ej24gxJmYdEJhJwtxokDxzf2/e6SAk2EywDJXw");
	        
			socket.connect(new IOCallback() {
	            @Override
	            public void onMessage(JSONObject json, IOAcknowledge ack) {
	                try {
	                   System.out.println("Server said:" + json.toString(2));
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            }
	
	            @Override
	            public void onMessage(String data, IOAcknowledge ack) {
	               System.out.println("Server said: " + data);
	            }
	
	            @Override
	            public void onError(SocketIOException socketIOException) {
	               System.out.println("an Error occured: " + socketIOException.getMessage());
	                socketIOException.printStackTrace();
	            }
	
	            @Override
	            public void onDisconnect() {
	               System.out.println("Connection terminated.");
	            }
	
	            @Override
	            public void onConnect() {
	               System.out.println("Connection established");
	            }
	
	            @Override
	            public void on(String event, IOAcknowledge ack, Object... args) {
	               System.out.println("Server triggered event '" + event + "'");
	            }
	        });
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
