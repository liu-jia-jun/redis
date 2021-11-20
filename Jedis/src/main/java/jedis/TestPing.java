package jedis;

import redis.clients.jedis.Jedis;

/**
 * 测试通过java代码连接redis数据库，在连接之前需要确保redis开启了服务端
 *
 * 在java代码中先获取Jedis 实例对象，通过jedis对象来操作redis数据库
 *
 * jedis对于redis中的操作的方法和redis中的命令一一对应
 */
public class TestPing {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping());
        System.out.println(jedis.flushDB());
        System.out.println(jedis.select(1));
        




        jedis.close();

    }
}
