package cn.com.neo.filter.codec.amf;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @description amf传输协议工厂，负责创廄1�7 <code>ProtocolEncoder</code>叄1�7 <code>ProtocolDecoder</code>
 * @author achou.lau
 * @created 2009-9-3 下午03:28:05
 */
public class AmfCodecFactory implements ProtocolCodecFactory {
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	
	public AmfCodecFactory(){
		encoder = new AmfEncoder();
		decoder = new AmfDecoder();
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

}
