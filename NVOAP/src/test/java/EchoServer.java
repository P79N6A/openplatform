import java.net.InetSocketAddress;

import com.aliyun.openservices.shade.io.netty.bootstrap.ServerBootstrap;
import com.aliyun.openservices.shade.io.netty.channel.ChannelInitializer;
import com.aliyun.openservices.shade.io.netty.channel.EventLoopGroup;
import com.aliyun.openservices.shade.io.netty.channel.nio.NioEventLoopGroup;
import com.aliyun.openservices.shade.io.netty.channel.socket.SocketChannel;
import com.aliyun.openservices.shade.io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	
	private final int port;
	public EchoServer(int port) {
		this.port=port;
	}
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println(EchoServer.class.getSimpleName());
		}
		int port=Integer.parseInt(args[0]);
		try {
			new EchoServer(port).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void start() throws Exception {
		final EchoServerHandler serverHandler=new EchoServerHandler();
		EventLoopGroup group =new NioEventLoopGroup();
		try {
			ServerBootstrap b=new ServerBootstrap();
			b.group(group).channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(port))
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(serverHandler);
					
				}
				
			});
	
			
		}catch(Exception e) {
			
		}
		
	}
	
	
	
	
	

}
