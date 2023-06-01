package ren.epub.nettyapp;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ren.epub.nettyapp.netty.NettyServerStart;

@SpringBootApplication
public class NettyApplication implements ApplicationRunner {

	private final NettyServerStart nettyServerStart;

	public NettyApplication(NettyServerStart nettyServerStart) {
		this.nettyServerStart = nettyServerStart;
		nettyServerStart.start();
	}

	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Runtime.getRuntime().addShutdownHook(new Thread(nettyServerStart::stop));
	}
}
