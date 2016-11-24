try{
  ServerSocketChannel s = ServerSocketChannel.open();
  s.configureBlocking(false); //false-> servidor no se queda bloqueado cuando hay operaciones de entrada y salida
  setOption(StandarSocketOptions.SO_REUSEADOR,true);//modificar opcion de socket
  InetSocketAdrress l = new InetSocketAddress(1234); //puerto con el servicio asociado
  s.socket().bind(l);//metodo bind para asociar socket a puerto
  //s.bind(l); para este caso también es válido
  Selector sel = Select.open();
  s.register(sel, SelectionKey.OP_ACCEPT);//opción de interés para el selector
  for(;;){
    sel.select(); //avisa cuando llegue un cliente
    Iterador{SelectionKey}it = sel.selectedKeys().iterador(); //cuando haya operaciones de interés las regresa con un iterador
    while(it.hasNext()){//mientras tenga elementos el Iterador
      SelectionKey k = (SelectionKey)it.next();
      it.remove();//eliminamos el iterador para no acumular
      if(k.isAcceptable()){
        SocketChannel cl = s.accept();
        System.out.print("Cliente conectado desde: "cl.socket().getInetAddress().getHostAddress()+":"+ cl.socket().getPort());
        cl.configureBlocking(false);
        cl.register(sel, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        continue;
      }
      if(k.isReadable()){
        SocketChannel ch= (SocketChannel)k.channel();
        ByteBuffer b= ByteBuffer.allocate(2000);//buffer vacio para ser llenado
        b.clear(); //borrar cualquier cosa que llegue a tener
        ch.read(b);
        b.flip();//reajustar limite
        if(b.hasArray()){//validar si el contenido del buffer se puede extraer como un arreglo de bytes
          //String dato1 = new String(b.array());
          //int dato2=b.getInt();
          //float dato3=b.getFloat();
          //ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(b.array()));
          //Objeto o = (Objeto)ois.readObject();
        }
        //k.interestOps(SelectionKey.OP_WRITE); swicheando entre operaciones<----
        //ya no recibir mas lecturas
        continue;
      }else if(k.isWritable()){
        SocketChannel ch= (SocketChannel)k.channel();//cast debido a que tambien puede ser un DatagramChannel
        //String d1 = "Un mensaje";
        //ByteBuffer b = ByteBuffer.wrap(d1.getBytes());
        //ch.write(b);
        //int v2 = 45;
        //ByteBuffer b = ByteBuffer.allocate(4);
        //b.putInt(v1);
        //b.flip();
        //ch.write(b);
        //k.interestOps(SelectionKey.OP_READ);Solo interesan operaciones de lectura<----
        continue;
      }
    }
  }
}catch(Exception e){
  e.printStackTrace();
}
