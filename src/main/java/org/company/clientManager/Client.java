package org.company.clientManager;
 
import com.rabbitmq.client.*;
import org.apache.commons.lang.SerializationUtils;
 
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
public class Client {
    private String response = null;
 
 
    public String sayHelloToServer(String mac, String username) throws IOException, InterruptedException {
 
        String exchangeName = "rpc_exchange2";   //交换器名称
        String queueName = "rpc_queue";     //队列名称
        String routingKey = mac;  //路由键
 
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("ip");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");     //创建链接
        
 
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();   
            //channel.exchangeDeclare(exchangeName, "topic", false, false, null);    //定义交换器
            //channel.confirmSelect();
            //channel.queueDeclare(queueName, true, false, false, null); //定义队列
            //channel.queueBind(queueName, exchangeName, routingKey, null); //绑定队列
            String callbackQueue = channel.queueDeclare().getQueue();   //获得匿名的 独立的 默认队列
            String correlationId = UUID.randomUUID().toString();    //产生一个 关联ID correlationID
            QueueingConsumer consumer = new QueueingConsumer(channel);  // 创建一个消费者对象
            channel.basicConsume(callbackQueue,false, consumer);      //消费消息
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()   //创建消息属性 
                    .correlationId(correlationId)   //携带唯一的 correlationID
                    .replyTo(callbackQueue).build();    //携带callback 回调的队列路由键
 
            channel.basicPublish(exchangeName, routingKey, true, basicProperties, SerializationUtils.serialize( username ));  //发布消息
            channel.addReturnListener(new ReturnListener() {
                public void handleReturn(int replyCode,
                                      String replyText,
                                      String exchange,
                                      String routingKey,
                                      AMQP.BasicProperties properties,
                                      byte[] body) throws IOException {
                         System.out.println("handleBasicReturn :" + routingKey );    
                    }
            });
            /*channel.addConfirmListener(new ConfirmListener() {    
    
                @Override    
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {    
                    System.out.println("[handleNack] :" + deliveryTag + "," + multiple);    
    
                }    
    
                @Override    
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {    
                    System.out.println("[handleAck] :" + deliveryTag + "," + multiple);    
                }    
            });  
            */
            //channel.waitForConfirms();
            /*final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
 
            class Consumer implements Runnable{ 

                public void run(){
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();   //循环获得消息
                    if(correlationId.equals(delivery.getProperties().getCorrelationId())){  
                        String res = (String) SerializationUtils.deserialize(delivery.getBody()); //获得处理结果
                        processResponse(res);
                        scheduledThreadPool.shutdown();
                    }
                }

            }
 
            scheduledThreadPool.scheduleAtFixedRate(new Consumer(), 0, 2, TimeUnit.SECONDS);
*/
            while(true){
                System.out.println("start while:" + response ); 
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();   //循环获得消息
                if(correlationId.equals(delivery.getProperties().getCorrelationId())){  //匹配correlationID是否与发出去的correlation的ID一直
                    response = (String) SerializationUtils.deserialize(delivery.getBody()); //获得处理结果
                    System.out.println("response:" + response ); 
                    break;
                }
            }

 
            channel.close();
 
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
 
 
        //关闭链接
 
        return this.response;
 
    }
 
    public static void main(String[] args) throws IOException, InterruptedException {
 
        Client client = new Client();
 
        String response = client.sayHelloToServer("", "TONY");
 
        System.out.println("server response : " + response);
 
    }
 
 
}
