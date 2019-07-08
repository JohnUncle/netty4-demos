package com.waylau.netty.demo.protocol;
 

/**
 * 说明：协议消息头
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月4日 
 */
public class ProtocolHeader{

	//    private byte magic; 	// 魔数
	private int len;		// 长度
	private byte msgType;	// 消息类型
	private String sn;		// 序列号 消息唯一标识
	private byte msgFormatType;	// 消息类型
	private byte msgCount;	// 消息总数
	private byte msgIndex;	// 消息序号

	public byte getMsgFormatType() {
		return msgFormatType;
	}

	public void setMsgFormatType(byte msgFormatType) {
		this.msgFormatType = msgFormatType;
	}

	public long getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public byte getMsgType() {
		return msgType;
	}

	public void setMsgType(byte msgType) {
		this.msgType = msgType;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public byte getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(byte msgCount) {
		this.msgCount = msgCount;
	}

	public byte getMsgIndex() {
		return msgIndex;
	}

	public void setMsgIndex(byte msgIndex) {
		this.msgIndex = msgIndex;
	}

	public ProtocolHeader() {
	}
	/**
	 * 
	 */
	public ProtocolHeader(int len, byte msgType, String sn,byte msgFormatType, byte msgCount, byte msgIndex) {
		this.len = len;
		this.msgType = msgType;
		this.sn = sn;
		this.msgFormatType = msgFormatType;
		this.msgCount = msgCount;
		this.msgIndex = msgIndex;
	}
}
