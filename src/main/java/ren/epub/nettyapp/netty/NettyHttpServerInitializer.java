package ren.epub.nettyapp.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.ssl.SslContext;
import ren.epub.nettyapp.handler.HttpHandler;

public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslContext;

    public NettyHttpServerInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslContext != null) {
            pipeline.addLast(sslContext.newHandler(ch.alloc()));
        }
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024*8));
        pipeline.addLast(new HttpServerExpectContinueHandler());
        pipeline.addLast(new HttpHandler());
    }

}
