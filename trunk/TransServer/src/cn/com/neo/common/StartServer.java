/**
 * StartServer.java
 * TODO
 * neo 2011-4-10
 */
package cn.com.neo.common;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import cn.com.neo.config.ServerConfig;
import cn.com.neo.filter.codec.amf.AmfCodecFactory;
import cn.com.neo.handler.AbsTransServerHandler;

/**
 * @author neo
 * 
 */
public class StartServer {

	private SocketAcceptor acceptor = null;

	private int proccessCount = 10;

	private String server = null;

	private int port = 8080;
	
//	/**
//	 * @param args
//	 */
//	public void start() {
//
//		start(new AbsTransServerHandler());
//	}
	
	public void start(IoHandler handler) {

		acceptor = new NioSocketAcceptor(proccessCount);

		init();
		
		if (server == null || "".equals(server)) {
			acceptor.setDefaultLocalAddress(new InetSocketAddress(port));
		} else {
			acceptor.setDefaultLocalAddress(new InetSocketAddress(server, port));
		}
		
		try {
			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
	
//			MdcInjectionFilter mdcInjectionFilter = new MdcInjectionFilter();
//			chain.addLast("mdc", mdcInjectionFilter);
	
			// Add SSL filter if SSL is enabled.
			// if (USE_SSL) {
			// addSSLSupport(chain);
			// }
	
			chain.addLast("codec", new ProtocolCodecFilter(
					new AmfCodecFactory()));
		
			addLogger(chain);

			// Bind
			acceptor.setHandler(handler);
			acceptor.bind();
			System.out.println("Listening on port " + port);
		} catch (Exception e) {
			e.printStackTrace();
			
			if(null != acceptor) {
				acceptor.dispose();
				acceptor = null;
			}
		}
	}

	private static void addLogger(DefaultIoFilterChainBuilder chain)
			throws Exception {
		chain.addLast("logger", new LoggingFilter());
		System.out.println("Logging ON");
	}

	private void init() {
		
		server = ServerConfig.getServer();
		port = ServerConfig.getServerPort();
	}
	
}
