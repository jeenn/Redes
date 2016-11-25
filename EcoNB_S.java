//servidor
import java.nio.channels.*;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.net.*;

public class S_EcoNB{
  public static void main(String[] args) {
    try{
      SocketChannel s = ServerSocketChannel.open();
      int pto = 7000;
      s.configureBlocking(false);
      s.socket().bind(new InetSocketAdrress(pto));
      System.out.println("Servicio iniciado, esperando clientes");
      Selector sel = Selector.open();
      s.register(sel,SelectionKey.OP_ACCEPT);
      while(true){
        sel.select();
        Iterator{SelectionKey}it = sel.selectedKeys().iterator();
        while(it.hasNext()){
          SelectionKey k = (SelectionKey)it.next();
          it.remove();
          if(k.isAcceptable()){
            SocketChannel cl = s.accept();
            System.out.println("Cliente conectado desde: " +cl.socket().getInetAddress()+ ":" +cl.socket().getPort());
            cl.configureBlocking(false);
            continue;
          }
          if(k.isReadable()){
            SocketChannel ch = (SocketChannel)k.channel();
            String msj = " ", eco " ";
            ByteBuffer b=ByteBuffer allocate(2000);
            b.clear();
            int n;
            n = ch.read(b);
            if(n>0){
	             b.flip();
	             msj=new String(b,0,n);
	             System.out.prinln("Mensaje recibido: " +msj);
	             if(msj.equalsIgnoreCase("SALIR")){
		               System.out.prinln("Termina el cliente");
		               ch.close();
		               continue;
	             }else{
		               eco="Eco " +msj;
		               ByteBuffer b2=ByteBuffer wrap(eco.getBytes());
		               ch.write(b);
		               continue;
		           }
             }
          }
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
