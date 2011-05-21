package cn.com.neo.filter.codec.amf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Output;

public class AmfEncoder extends ProtocolEncoderAdapter {

   @Override
   public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {

      if (session.containsAttribute("policy-file-request")) {

         byte[] bytes = ((String) message).getBytes();
         IoBuffer buffer = IoBuffer.allocate(bytes.length);
         buffer.put(bytes);
         buffer.flip();

         out.write(buffer);

      } else {
         Amf3Output amf3Out = new Amf3Output(new SerializationContext());
         ByteArrayOutputStream bos = new ByteArrayOutputStream();

         amf3Out.reset();
         amf3Out.setOutputStream(bos);
         try {
            amf3Out.writeObject(message);
            amf3Out.writeObjectEnd();
            amf3Out.flush();
            //session.write(bos);
         } catch (IOException e) {
            throw new RuntimeException(e);
         }

         //ByteArrayOutputStream bos = (ByteArrayOutputStream) message;
         // create output bytes
         byte[] bytes1 = bos.toByteArray();
         int capacity = bytes1.length + 4;
         IoBuffer buffer = IoBuffer.allocate(capacity, false);
         buffer.setAutoExpand(true);
         buffer.putInt(bytes1.length).put(bytes1);
         buffer.flip();

         // write out
         out.write(buffer);
      }
   }

//   public static void main(String[] args) {
//      Message cc = new Message("test");
//      PMessage cmd = new PMessage(cc);
//      //cmd.setProxy(ServerConfig.getProxy());
//      Map m=new IdentityHashMap();
//      m.put(new Integer(1),"sdfsd");
//      m.put(new Integer(2),"sdfsdddddddd");
//      cmd.setData(m);
//      
//      Amf3Output amf3Out = new Amf3Output(new SerializationContext());
//      ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//      amf3Out.reset();
//      amf3Out.setOutputStream(bos);
//      try {
//         amf3Out.writeObject(cmd);
//         amf3Out.writeObjectEnd();
//         amf3Out.flush();
//         
//         //session.write(bos);
//      } catch (IOException e) {
//         throw new RuntimeException(e);
//      }
//
//      //ByteArrayOutputStream bos = (ByteArrayOutputStream) message;
//      // create output bytes
//      byte[] bytes = bos.toByteArray();
//      
//         Amf3Input amf3In = new Amf3Input(new SerializationContext());
//         
//          amf3In.setInputStream(new ByteArrayInputStream(bytes));
//          try {
//            Object o = amf3In.readObject();
//            System.out.println(o);
//         } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//         } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//         }
//
//   }
}
