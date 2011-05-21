package cn.com.neo.filter.codec.amf;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @description amf浼犺緭鍗忚宸ュ巶锛岃礋璐ｅ垱寤� <code>ProtocolEncoder</code>鍙� <code>ProtocolDecoder</code>
 * @author achou.lau
 * @created 2009-9-3 涓嬪崍03:28:05
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
