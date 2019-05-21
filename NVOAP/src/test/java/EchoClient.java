import java.net.InetSocketAddress;

import com.aliyun.openservices.shade.io.netty.bootstrap.Bootstrap;
import com.aliyun.openservices.shade.io.netty.channel.ChannelFuture;
import com.aliyun.openservices.shade.io.netty.channel.ChannelInitializer;
import com.aliyun.openservices.shade.io.netty.channel.EventLoopGroup;
import com.aliyun.openservices.shade.io.netty.channel.nio.NioEventLoopGroup;
import com.aliyun.openservices.shade.io.netty.channel.socket.SocketChannel;
import com.aliyun.openservices.shade.io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	private final String host;
	private final int port;
	public EchoClient(String host,int port) {
		this.host=host;
		this.port=port;
	}
	public void start() throws Exception {
		EventLoopGroup group =new NioEventLoopGroup();
		try{
			Bootstrap b=new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.remoteAddress(new InetSocketAddress(host,port))
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception{
					ch.pipeline().addLast(new EchoClientHandler());
				}
				
			});
			ChannelFuture f=b.connect().sync();
			f.channel().closeFuture().sync();			
		}finally {
			group.shutdownGracefully().sync();
		}
	}
	public static void main(String[] args)throws Exception{
		if(args.length!=2) {
			System.out.println("usage:"+EchoClient.class.getSimpleName()+"<host><port>");
			return;
		}
		String host=args[0];
		int port=Integer.parseInt(args[1]);
		new EchoClient(host,port).start();
		
		
	}

}
