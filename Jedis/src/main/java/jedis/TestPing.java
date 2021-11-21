package jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 测试通过java代码连接redis数据库，在连接之前需要确保redis开启了服务端
 * <p>
 * 在java代码中先获取Jedis 实例对象，通过jedis对象来操作redis数据库
 * <p>
 * jedis对于redis中的操作的方法和redis中的命令一一对应
 */
public class TestPing {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping());

        System.out.println(jedis.select(0));

        String k1 = "name";
        String v1 = "刘佳俊";
        String k2 = "age";

        String newk1 = "newname";


        System.out.println("清空数据库:" + jedis.flushDB());
        System.out.println("判断某个键是否存在:" + jedis.exists(k1));
        System.out.println("新增k1,v1键值对:" + jedis.set(k1, v1));
        System.out.println("新增k2,v2键值对:" + jedis.get(k1));
        System.out.println("系统中所有的键如下:");
        Set<String> keys = jedis.keys("*");

        System.out.println(keys);
        System.out.println("删除键k2:" + jedis.del(k2));
        System.out.println("判断键k2是否存在:" + jedis.exists(k2));
        System.out.println("查看键 k1 所存储的数据类型:" + jedis.type(k1));
        System.out.println("随机返回 key 空间的一个:" + jedis.randomKey());
        System.out.println("重命名 key :" + jedis.rename(k1, newk1));
        System.out.println("取出改后的 newk1 :" + jedis.get(newk1));
        System.out.println("按索引查询:" + jedis.select(0));
        System.out.println("删除当前选择数据库中的所有键:" + jedis.flushDB());
        System.out.println("返回当前数据库中 key 的数量:" + jedis.dbSize());
        System.out.println("删除所有数据库中的所有 key :" + jedis.flushAll());
        jedis.close();

    }
}
