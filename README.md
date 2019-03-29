# netty-tomcat

使用netty简单实现一个tomcat服务器，通过浏览器访问http://localhost:8080?name=jack,服务器向页面返回jack.

*****学习过程总结：******

BIO（阻塞IO通信模型）

拿C/S来举例：当服务端没有使用while(true)循环接受客户端请求时，当第一个客户端连接进来时，第二个客户端必须等前一个客户端IO操作完毕，释放掉资源，

第二个才能连接上，这是一种阻塞式的IO模式。当然可以使用while(true)并且使用线程池，同时接受多个连接请求。但是本质上还是一个客户端对应一个服务器。

无法支持高并发。并且客户端使用时还有IO建立和释放的过程，耗时。

BIO是面向流的，在使用时C/S才开始建立通道。

NIO（非阻塞IO通信模型）

拿C/S来举例：NIO中引入了Channel和Buffer，NIO是先建立一个通道，再有数据时直接使用通道，避免了建立连接的过程。在服务端加入了Selector（多路复用）,

可以将多个channel注册到selector中，并注册事件类型，事件驱动机制：所以当事件触发时，selector通知handler处理消息。这样可以使用单线程同时处理多

个客户端的请求，极大的提高了并发性能。（1：N个客户端）

NIO是面向缓冲区的。客户端和服务端都是通过缓冲区进行信息交互的，并不阻塞，不向BIO那样，C/S之间交互通过流（阻塞），读或者写完才能继续进行操作。

1、学习NIO基本知识

channel

seletor（反应堆Reactor）

之前注册的事件到达，通过selector分发取执行任务

buffer(capcacity|limit|position|mark|flip|clear)相关概念

buffer种类：buffer有七大基本类型的封装（没有boolean）

创建buffer
1、ByteBuffer.allocate(int length) //静态分配空间

2、ByteBuffer.wrap(...)  //使用自定义的数据类型包装成缓冲区

3、ByteBuffer.allocateDirect() // 直接缓冲区：零拷贝  跳出JVM，底层直接使用C语言，效率更高

ByteBuffer.allocate与ByteBuffer.allocateDirect的比较？https://younglibin.iteye.com/blog/1978055
为什么要提供两种方式呢？这与Java的内存使用机制有关。第一种分配方式产生的内存开销是在JVM中的，而第二种的分配方式产生的开销在JVM之外，
以就是系统级的内存分配。当Java程序接收到外部传来的数据时，首先是被系统内存所获取，然后在由系统内存复制拷贝到JVM内存中供Java程序使用。
所以在第二种分配方式中，可以省去复制这一步操作，效率上会有所提高。但是系统级内存的分配比起JVM内存的分配要耗时得多，所以并不是任何时候
allocateDirect的操作效率都是最高的。

创建子缓冲区
buffer.position(3)

buffer.limit(7)

ByteBuffer slice = buffer.slice();

创建只读缓冲区

buffer内部原理：

buffer初始化时：position=0 mark=-1 limit=capcacity=设定的长度

buffer进行写入时：position一直增加

当读取buffer时，使用flip方法进行读写转换：position=0 将limit设置为原来position的值。

当读取完毕时，调用可以调用clear方法将buffer还原为初始化状态。

0<=position<=limit<=capacity

在selector注册多个channel,可以实现使用单线程可以监听多个连接的accept\read\write等操作，提高并发性

NIO有一个Bug导致cpu100%

2、netty入门案例，了解netty基本操作，线程模型：单线程、多线程、主从模式

3、netty半包和沾包问题解决方案

	 （1）定长数据流 FixedLengthFrameDecoder（int frameLength）

   (2)  特殊字符分割 DelimiterBasedFrameDecoder
	 
   (3) 基于换行符分割 LineBasedFrameDecoder
   
   (4) 自定义的协议（推荐）
	 
   （5）传输序列化对象（压缩解压） marshalling序列化框架






      
