package cn.com.neo.filter.codec.amf;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import cn.com.neo.acceptor.Message;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;

public class AmfDecoder extends ProtocolDecoderAdapter {

	private static final Log LOG = LogFactory.getLog(AmfDecoder.class);

	private static final int MAX_DATASIZE = 6 * 1024 * 1024;
//	private static final int MAX_DATASIZE = Integer.MAX_VALUE;
	private static final String REMAIN_BUF_KEY = "remain_buf_key";
	private static final String AMF3INPUT_KEY = "amf3input_key";
	private static CharsetDecoder DECODER = Charset.forName("UTF-8").newDecoder();
	private static String xml = "<cross-domain-policy> "
								+ "<allow-access-from domain=\"*\" to-ports=\"8843-9999\"/>"
								+ "</cross-domain-policy>\0";

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		System.out.println("decode.....");
		try {
			byte[] remain_buf = (byte[]) session
					.removeAttribute(REMAIN_BUF_KEY);
			IoBuffer tmp_buf = null;
			if (null != remain_buf) {

				byte[] bytes1 = remain_buf;
				byte[] bytes2 = new byte[in.remaining()];
				in.get(bytes2);
				byte[] bytes3 = new byte[bytes1.length + bytes2.length];
				System.arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
				System.arraycopy(bytes2, 0, bytes3, bytes1.length,
						bytes2.length);

				tmp_buf = IoBuffer.wrap(bytes3);
			} else {
				tmp_buf = in;
			}

			Amf3Input amf3In = (Amf3Input) session.getAttribute(AMF3INPUT_KEY);
			if (null == amf3In) {
				amf3In = new Amf3Input(new SerializationContext());
//				new Amf3Input(new SerializationContext())
				session.setAttribute(AMF3INPUT_KEY, amf3In);
			}
			while (tmp_buf.prefixedDataAvailable(4, MAX_DATASIZE)) {

				// get original bytes
				int length = tmp_buf.getInt();
				byte[] bytes = new byte[length];
				tmp_buf.get(bytes);

				// decode by amf protocol
				amf3In.reset();
				amf3In.setInputStream(new ByteArrayInputStream(bytes));
				//String cmd=amf3In.readUTF();
				//Object o = amf3In.readObject();
				Message msg=new Message();
				msg.setCmd(amf3In.readUTF());
				msg.setData(amf3In.readObject());

				out.write(msg);
				
				if (tmp_buf.hasRemaining()) {
					byte[] tmp = new byte[tmp_buf.remaining()];
					tmp_buf.get(tmp, 0, tmp.length);
					tmp_buf = IoBuffer.wrap(tmp);
				} else {
					break;
				}

			}

			// put rest bytes into session
			if (tmp_buf.hasRemaining()) {
				byte[] bytes = new byte[tmp_buf.remaining()];
				tmp_buf.get(bytes);
				session.setAttribute(REMAIN_BUF_KEY, bytes);

			}

			in.position(in.limit());
			
		} catch (Exception e) {
			e.printStackTrace();
			if (!policy(session, in.buf())) {
				throw e;
			}

		}
	}

	private boolean policy(IoSession session, ByteBuffer buffer)
			throws Exception {
		boolean policy = false;

		buffer.rewind();
		try {

			String str = DECODER.decode(buffer).toString();
			if (str.indexOf("policy-file-request") >= 0) {
				session.setAttribute("policy-file-request","policy-file-request");
				session.write(xml);
				if(LOG.isDebugEnabled()){
					LOG.debug("'" + getClass().getName()
							+ "' send policy-file to ["
							+ session.getRemoteAddress() + "].");
				}
			}
			policy = true;
		} catch (Exception e) {
			LOG.error(e);
		}

		return policy;
	}
}
