import com.aliyun.openservices.shade.io.netty.buffer.ByteBuf;
import com.aliyun.openservices.shade.io.netty.buffer.Unpooled;
import com.aliyun.openservices.shade.io.netty.channel.ChannelFutureListener;
import com.aliyun.openservices.shade.io.netty.channel.ChannelHandlerContext;
import com.aliyun.openservices.shade.io.netty.channel.ChannelInboundHandlerAdapter;
import com.aliyun.openservices.shade.io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx ,Object msg) {
		ByteBuf in=(ByteBuf)msg;
		System.out.println("server received:"+in.toString(CharsetUtil.UTF_8));
		ctx.write(in);	
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	
	
	
	
	

}
