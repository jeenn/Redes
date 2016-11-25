//cliente
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class C_EcoNB{
  public static void main(String[] args) {
    try{
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String host = "127.0.0.1";
      int pto = 7000;
      SocketChannel cl = SocketChannel.open();
      cl.configureBlocking(false);
      cl.connect(new InetSocketAdrress(host,pto));
      Selector set = Selector.open();
      cl.register(sel,SelectionKey.OP_CONNECT);
      while(true){
        sel.select();
        Iterator{SelectionKey} it = sel.selectedKeys().iterator();
        while(it.hasNext()){
          SelectionKey k = (SelectionKey)it.next();
          it.remove();
          if(k.isConnectable()){
            SocketChannel ch = (SocketChannel)k.channel();
            if(ch.isConectionPending()){
              System.out.println("Estableciendo conexion con el servidor");
              try{
                ch.finishConnect();
              }catch(Exception e){
                e.printStackTrace();
              }
              System.out.println("Conexion  establecida");
              ch.register(sel,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
              continue;
            }
            if(k.isReadable()){
              SocketChannel ch = (SocketChannel)k.channel();
              ByreBuffer b = ByteBuffer.allocate(2000);
              b.clear();
              int n = ch.read(b);
              String eco = new String(b,0,n);
              System.out.prinln("Eco recibido: " +eco);
              k.interestOps(SelectionKey.OP_WRITE);
              continue;
            }else if(k.isWritable()){
              SocketChannel ch = (SocketChannel)k.channel();
              System.out.println("Escriba un mensaje, presione ENTER para enviar, SALIR para terminar");
              String msj = br.readLine();
              ByteBuffer b = ByteBuffer.wrap(msj);
              ch.write(b);
              k.interestOps(SelectionKey.OP_READ);
              continue;
            }
          }
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
