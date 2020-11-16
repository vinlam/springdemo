package com;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestThread implements Runnable {
	/**
	 * 主机IP
	 */
	private String ip;

	/**
	 * 通讯端口
	 */
	private int port;

	/**
	 * 发送报文
	 */
	private byte[] buf;

	/**
	 * 发送次数
	 */
	private int sendNum;

	public TestThread(String ip, int port, byte[] buf, int sendNum) {
		this.ip = ip;
		this.port = port;
		this.buf = buf;
		this.sendNum = sendNum;
	}

	public void run() {
		Socket socket = null;
		BufferedInputStream in = null;
		try {
			for (int i = 0; i < sendNum; i++) {
				socket = new Socket(ip, port);
				System.out.println("open output ok! :" + new String(buf));
				OutputStream output = socket.getOutputStream();
				// 发送报文
				output.write(buf);
			    System.out.println("send package ok!");
				// 接收报文
				in = new BufferedInputStream(socket.getInputStream());
				byte[] buffer = new byte[1024];
				int len = 0;
				StringBuffer strbuf = new StringBuffer();
				strbuf.append("receive package:");
				while ((len = in.read(buffer)) != -1) {
					strbuf.append(new String(buffer, 0, len));
				}
				System.out.println(strbuf);
				socket.close();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
