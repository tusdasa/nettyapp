package ren.epub.nettyapp.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class NettyServerStart implements DisposableBean {
    private static final Logger Log = LoggerFactory.getLogger(NettyServerStart.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private Channel ch;

    @Value("${netty.port:8443}")
    private int port;

    @Value("${netty.ssl:true}")
    private boolean ssl;

    @Value("${netty.threads:1}")
    private int threads;

    @Value("${netty.sslkey}")
    private String sslkey;

    @Value("${netty.sslcert}")
    private String sslcert;


    public NettyServerStart() {
    }

    public void start() {
        SslContext sslCtx = null;
        if (ssl) {
            ClassPathResource serverpem = new ClassPathResource("server.pem");
            ClassPathResource serverkey = new ClassPathResource("key.pem");

            try {
//                SelfSignedCertificate ssc = new SelfSignedCertificate();
//                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
                sslCtx = SslContextBuilder.forServer(serverpem.getInputStream(), serverkey.getInputStream()).build();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }

        }

        this.bossGroup = new NioEventLoopGroup(threads);
        this.workerGroup = new NioEventLoopGroup(threads);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyHttpServerInitializer(sslCtx));
            ch = b.bind(port).sync().channel();
            Log.info("Now Server is runing " +
                    (ssl ? "https" : "http") + "://127.0.0.1:" + port + '/');
            ch.closeFuture().sync();
        } catch (Exception e) {
            Log.error(e.getMessage());
        }finally {
            ch.close();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        Log.info("now shutdown " +
                (ssl ? "https" : "http") + "://127.0.0.1:" + port + '/');
        if (ch!=null){
            ch.close();
        }
        if (bossGroup!=null && !bossGroup.isShutdown()){
            bossGroup.shutdownGracefully();
        }
        if (workerGroup!=null && !workerGroup.isShutdown()){
            workerGroup.shutdownGracefully();
        }
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getSslkey() {
        return sslkey;
    }

    public void setSslkey(String sslkey) {
        this.sslkey = sslkey;
    }

    public String getSslcert() {
        return sslcert;
    }

    public void setSslcert(String sslcert) {
        this.sslcert = sslcert;
    }

    @Override
    public void destroy() throws Exception {
        this.stop();
    }
}
