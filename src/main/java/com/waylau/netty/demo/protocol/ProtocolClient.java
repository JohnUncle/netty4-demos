package com.waylau.netty.demo.protocol;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 说明：自定义协议客户端
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日
 */
public class ProtocolClient {

	private String host;
	private int port;

	private static final int MAX_FRAME_LENGTH = 1024;
	private static final int LENGTH_FIELD_LENGTH = 4;
	private static final int LENGTH_FIELD_OFFSET = 0;
	private static final int LENGTH_ADJUSTMENT = 36;
	private static final int INITIAL_BYTES_TO_STRIP = 0;

	int i =0;

	/**
	 * 
	 */
	public ProtocolClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws InterruptedException {

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(
							"decoder",
							new ProtocolDecoder(MAX_FRAME_LENGTH,
									LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
									LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));
					ch.pipeline().addLast("encoder", new ProtocolEncoder());
					ch.pipeline().addLast(new ProtocolClientHandler());

				}
			});

			// 启动客户端
			ChannelFuture f = b.connect(host, port).sync(); // (5)

//			while (true) {

				// 发送消息给服务器
				ProtocolMsg msg = new ProtocolMsg();
				ProtocolHeader protocolHeader = new ProtocolHeader();
				protocolHeader.setMsgCount((byte) 0x01);
				protocolHeader.setMsgType((byte) 0x00);
				protocolHeader.setMsgIndex((byte) 0x00);

//				long[] id = IdUtil.service().batchGenId(1);
			String uuidStr = UUID.randomUUID().toString().replace("-", "");

//			System.out.println("生成ID " + id[0]);
				protocolHeader.setSn(uuidStr);
				protocolHeader.setMsgFormatType((byte) 0x00);

				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("msgCode","robotCurrentPostion");
				jsonObject.put("msgCode","electric");
				JSONObject data = new JSONObject();
				float x = (float) 65.5555;
				float y = (float) 20.5555;
				float yaw = (float) 30.5555;
				data.put("x",x);
				data.put("y",y);
				data.put("yaw",yaw);
				data.put("pngSrc",yaw);


			data.put("elecQuantity",new Float(23.888888));

//							jsonObject.put("msgCode","scrolling");
//				JSONObject data = new JSONObject();
				data.put("logDate",new Date());
				data.put("content","前进"+i+"米");
			jsonObject.put("data",data);
				i++;
//				jsonObject.put("data",data);

				String body = jsonObject.toJSONString();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < 1; i++) {
					sb.append(body);
				}

				byte[] bodyBytes = sb.toString().getBytes(
						Charset.forName("utf-8"));
				int bodySize = bodyBytes.length;
				protocolHeader.setLen(bodySize);

				msg.setProtocolHeader(protocolHeader);
				msg.setBody(sb.toString());

				f.channel().writeAndFlush(msg);
				Thread.sleep(2000);
//			}
			// 等待连接关闭
			// f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		ProtocolClient client = new ProtocolClient("localhost", 8090);
		while (1==1){
			Thread.sleep(5000);
			client.run();
		}
	}

}
