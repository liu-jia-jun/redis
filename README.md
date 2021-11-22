# Redis

## Redis概述

> + Redis（Remote Dictionary Server）远程字典服务，是一个开源的使用C语言编写的可持久化的key-value类型的数据库
> + Redis 是运行在内存中的一种数据库,redis数据库是一种NoSql数据库，一种非关系型数据库，在该数据库中数据以key-value 的形式存在。redis数据库中总共16个数据库从0开始到15。
> + Redis 是单线程的
>
> > redis是将所有的数据都存放在内存中，所以说使用单线成去操作效率就是最高的，多线程操作时会涉及到CPU的上下文切换，单线程操作时多次的读写都在一个CPU中，不涉及上下文切换
## Redis中关于key和数据库的操作

| key的操作    | 作用                                                         |
| ------------ | ------------------------------------------------------------ |
| keys *       | 查看当前数据库中的所有的key值                                |
| keys *n      | 查看指定的某个数据库中的所有key值                            |
| exists key   | 判断某个key是否存在                                          |
| type key     | 查看改key值是什么类型的key值                                 |
| del key      | 删除指定的key （直接删除）                                   |
| unlink key   | 根据 value 选择非阻塞删除（仅将key 从keyspace元数据中删除，真正的删除会在之后的异步操作中执行） |
| expire key n | 为该key设置过期时间 n为秒数                                  |
| ttl key      | 查看该key还有多少秒过期，-1表述永不过期，-2 为已过期         |


---


| 数据库的操作 | 作用                          |
| ------------ | ----------------------------- |
| select n     | 切换数据库，n为数据库下表0~15 |
| dbsize       | 查看当前数据库的key的数量     |
| flushdb      | 清空当前数据库                |
| flushall     | 清空所有的数据库              |

## redis中的数据类型
### String类型
> + String是Redis中最基本的类型，一个key对应一个value
> + String类型是二进制安全的，意味着Redis的String可以包含任何数据，例如图片或者序列化对象等
> + String类型是Redis最基本的数据类型，一个Redis中字符串value最多可以是512MB

#### 常用命令

| String类型key的操作            | 作用                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| set key value                  | 插入一个key 和 value 到数据库中，当key值重复时是进行一个更新value的操作 |
| setex key n value              | 设置键值对的同时设置过期时间单位为秒                         |
| mset key1 value1 key2 value2   | 同时设置一个或多个key-value键值对                            |
| msetnx key1 value1 key2 value2 | 同时设置一个或者多个键值对，只有给定的所有的key都不存在时才能同时设置成功，一个失败则都失败（该操作具有原子性） |
| setrange key begin value       | 用value去从key对应的值从begin位置开始去覆盖后面的值          |
| get key                        | 查询对应key的value值                                         |
| getset key value               | 以新换旧，设置新值的同时获取旧值                             |
| mget key1 key2                 | 同时获取一个或者多个value                                    |
| getrange key begin end         | 获取值的范围 类似于java中的subString，一个字符串的截取 包含前后两个值 |
| append key value               | 将value 追加到key所对应的value后面，如果key不存在，相当于set key value操作 |
| strlen key                     | 获取key对应value值的长度                                     |
| setnx key value                | 只有key值不存在时才能设置key的value值，不能进行覆盖操作      |
| incr key                       | 将key中存储的数字值加1                                       |
| decr key                       | 将key中存储的数字值减1                                       |
| incrby / decrby key n          | 将key中存储的数字值进行增减，n为步长，即一次增减多少         |
| 注意                           | 在String类型中的增减操作只能对数字值进行操作，如果为空，则新增值为-1 |

### List类型
> + 单键多值，当一个list中的value值一个都没有那么这个list就 不存在了
> + redis中的list列表是简单的字符串列表，按照插入顺序排列，可以在列表的头和尾去追加或取得元素
> + list 的底层是一个双向的链表，具有高效的增删操作，较为低效的查寻操作
> + list的数据结构为快速链表quicklist 但是如果列表元素较少时会是一个ziplist即压缩列表，如果数据量较多时就会改成quicklist

#### 常用命令
| List类型的常用操作                      | 作用                                                         |
| --------------------------------------- | ------------------------------------------------------------ |
| lpush / rpush key value1 value2 value3  | 从左边或右边插入一个或者多个值                               |
| lpop / rpop key                         | 从左边或者右边弹出一个值，（如果该list中的值被弹尽，那么该列表也会被清楚） |
| lrange key start stop                   | 按照索引下表获得元素从左到右                                 |
| lrange key 0 -1                         | 取值 0为第一个，-1为最后一个 0-1为取出所有的元素             |
| lindex key index                        | 按照索引下标获取元素，从左到右                               |
| llen key                                | 获取列表的长度                                               |
| linsert key before/after value newvalue | 在value的前面或者后面插入新值                                |
| lrem key n value                        | 从左边删除n个value（从左到右）                               |
| lset key index value                    | 将列表key下表为index的值替换成value                          |

### set类型

> + Redis set 对外提供的功能和list类似是一个列表的功能，特殊之处在于set可以自动去重，
> + 当你需要存储一个列表数据，又不希望出现重复数据时，set是一个很好的选择
> + Redis中的set是String类型的无序集合，它的底层其实是一个value为null 的hash 表所有添加删除查找的复杂度都是o(1)
> + Set数据结构是dict字典，通过哈希表实现

#### 常用命令
| Set类型的常用操作      | 作用                                                         |
| ---------------------- | ------------------------------------------------------------ |
| sadd key value1 value2 | 将一个或者多个元素添加到该set集合中，会自动去重，不会有重复的元素且为无序集合 |
| smembers key           | 取出该set集合中的所有的值spop                                |
| sismember key value    | 判断该集合中是否含有value值，返回结果 1为存在，0为不存在     |
| srem key value1 value2 | 删除集合中的某个元素                                         |
| spop key               | 从集合中随机弹出（取出并删除）一个值                         |
| srandmember key n      | 随机从集合中取出n个值，并不会从集合中删除                    |
| smove key1 key2 value  | 将key1中的value值移动到key2中                                |
| sinter key1 key2       | 返回key1和key2两个集合的交集元素                             |
| sunion key1 key2       | 返回key1和key2两个集合的并集元素                             |
| sdiff key1 key2        | 返回两个集合的差集元素（key1集合中所特有的即不包含key2中的） |

### Redis 哈希（Hash）

> + Redis hash 是一个键值对集合，key表示一个hash数据类型的变量名，value以一个对象的形式存在，对象中是一组一组的键值对
> + Redis hash 是一个String 类型的field 和value的映射表，hash 特别适合用于存储对象类似于Java中的**Map<String,Object>**
> + Hash 数据类型对应的时压缩列表和哈希列表 当给定域的数量较多时使用ziplist压缩列表，如果给定域的数量较多长度较大时使用会转换成hashtable

#### 常用命令
| Hash类型的常规操作                    | 作用                                                         |
| ------------------------------------- | ------------------------------------------------------------ |
| hset key filed value                  | field键赋值value                                             |
| hget key filed                        | key这个hash中找出field所对应的值                             |
| hmset key field1 value1 field2 value2 | 批量的设置hash这个数据类型的值                               |
| hexists key filed                     | 查看隶属于key的哈希表中给定的filed（键，这里称之给定域） 是否存在 |
| hkeys key                             | 列出该hash集合的所有field                                    |
| hvals key                             | 列出该hash集合中所有field对应的值                            |
| hsetnx key field value                | 给key这个哈希表新增field这个给定域并设置为value当且仅当这个field给定域不存在时才能添加成功 |

### Zset类型(有序集合)
> + redis Zset 有序集合和无序集合set 非常相似，是一个没有重复元素的字符串集合，
> + 有序集合的每个成员都关联了一个评分，Zset中根据这个评分进行排序，在有序集合中元素不可重复，但是评分的值可以重复

#### 常用命令
| Zset类型的常用操作                                           | 作用                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| zadd key score1 value1 score2 value2                         | 将一个或者多个value和对应的score 加入到有序集合key中         |
| zrange kye start stop [WithSCORES]                           | 返回有序集合key中下标在start到stop之间的元素，带有WITHSCORES |
| zrangebyscore key min max [withscores] [limit offset count]  | 返回有序集合key中，所有score值位于min和max中的所有元素（根据score从小到大排序） |
| zrevrangebyscore key max min [withscores] [limit offset count] | 从大到小排序                                                 |
| zincrby key increment value                                  | 为元素的score加上增量                                        |
| zrem key value                                               | 删除该集合下指定的元素                                       |
| zcount key min max                                           | 统计该集合score在min和max区间内的元素个数                    |
| zrank key value                                              | 返回该值在集合中的排名，从0开始                              |



## 事务
> + Redis 事务的本质:一组命令的集合，一个事务中所有命令都会被序列化，在事务执行过程中，会按照顺序执行**一次性，顺序性，排他性**执行一系列的命令
> + redis事务没有隔离级别得到概念，所有的命令在事务中，并没有直接执行，只有发起执行命令的时候才会执行，Exec
> + Redis单条命令保证原子性，但是事务是不保证原子性的

### 事务执行步骤
1. 开启事务
2. 命令入队
3. 执行事务 | 中止事务

```redis
multi # 开启事务

set k1 v1 # 命令入队

set k2 v2 # 命令入队

exec # 执行事务

# 开启事务之后，输入命令，此时命令入队但是命令并没有执行，exec执行事务之后，入队的命令开始顺序的执行
# exec 执行事务之后默认本次事务结束，如果还需要事务需要重新使用multi开启事务
```

```redis
multi # 开启事务

set k1 v1 # 命令入队

set k2 v2 # 命令入队

discard # 中止命令

# 中止命令之后，入队的命令不会执行
```
## Redis配置文件

> 网络
```config
bind 127.0.0.1 # 绑定访问的ip地址
protected-mode yes # 设置保护模式
port 6379 # 端口设置
```
> 通用设置 GENERAL
```config
daemonize yes # 以守护进程的方式运行，默认值为no，需要手动开启

pidfile /var/run/redis_6379.pid # 如果以后台方式运行，那我们就需要指定一个pid文件

# 日志
loglevel notice # 指定日志的级别

logfile "" # 指定日志的文件位置名

database 16 # 设置数据库的数量，redis 默认数据库的个数为16个

always-show-logo yes # 设置是否总是显示LOGO（在服务端运行时是否显示日志）
```

> 快照
>
> > redis 是内存数据库，断电之后数据丢失，我们需要将重要数据持久化到电脑磁盘中
```config
# 如果900s内，如果有至少一个key的值进行了修改，那么就会进行持久化
save 900 1 
# 如果300s内，如果有至少十个key的值进行了修改，那么就会进行持久化
save 300 10 
# 如果60s内，如果有至少一万个个key的值进行了修改，那么就会进行持久化
save 60 10000 


stop-writes-on-bgsave-error yes # 设置redis在持久化时如果出错是否还继续工作

rdbcompression yes # 设置是否保存rdb文件，开启的话需要消耗一定的CPU资源

rdbchecksum yes # 保存rdb文件的时候，是否进行错误的检查校验

dir ./ # 设置持久化时的rdb文件保存的目录
```

> SECURITY 安全

```
# 在redis中默认是没有密码的 可以通过更改设置来设置密码

requirepass "密码"

# 也可以通过指令来设置redis的密码

config set requirepass "密码" # 设置密码

config get requirepass # 获取密码

auth "密码" # 通过密码登录

```
> 限制 CLIENTS
```
maxclients 10000 # 设置能连接redis的最大的客户端的数量

maxmemory <bytes> # 设置redis 配置的最大内存容量

maxmemory-policy noeviction # 设置内存达到上限之后的处理策略
 1. volatile-lru:只对设置了过期时间的key进行lRU
 2. allkeys-lru: 删除lru算法的key
 3. volatile-random : 随机删除即将过期的key
 4. allkeys-random : 随机删除key
 5. volatile-ttl : 删除即将过期的
 6. noeviction : 永不过期，返回错误
```

## Redis持久化

### RDB

> + 在指定的时间间隔内将内存中的数据集一次性的写入到磁盘中，也就是快照，恢复时是将快照文件直接读到内存中
> + Redis 会单独的创建一个子进程（fork）来进行持久化，会先将数据写到一个临时文件中，等持久化的过程结束时，再用这个临时文件替换上次持久化好的文件，整个过程中，主进程是不进行任何IO操作的，这确保了极高的性能，如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感的（在redis服务宕机的最后一次修改数据是如果没有触发快照机制，那么本次的数据会丢失），RDB方式较于AOF方式更加高效，RDB的确定就是最后一次持久化后的数据可能会丢失，我们默认的就是RDB一般情况下不需要修改这个给配置
> + RDB保存的临时文件是 dump.rdb ,可以在配置文件中进行设置

> 如何通过rdb文恢复之前的数据
>
> >1. 只需要将redis产生的rdb文件放在启动目录下就可以了，redis启动时会自动的加载rdb文件中的数据读取到内存中，即可恢复之前的数据
> >
> >2. 查看redis 启动目录
> >
> >  ```bash
> >  config get dir
> >  1)"dir"
> >  2)"/usr/local/bin" # 如果将dump.rdb文件放在该目录下，redis启动时就会自动的加载该文件并读取到内存中
> >  ```
> >
> >
> >
> >

 



**优点**

1. 适合大规模的数据恢复
2. 对数据的完整性要求不高



**缺点**

1. 持久化的进程操作需要一定的时间间隔，如果redis服务停止了那么最后一次修改的数据就无法持久化到本地
2. fork进程运行的时候会占用一定的内存空间



### AOF

> 以日志的形式来记录每一个写操作，将Redis执行过的所有的增删改数据的指令记录下来（读操作不记录），只许追加文件但是不可以改写文件，redis 启动之初会读取该文件重新构建数据，即redis重启之后会根据日志文件的内容将指令从前到后执行一次，以此完成对数据的恢复
>
> > + AOF 保存的文件是appendonly.aof文件
> > + Redis 默认开启的持久化策略是RDB，如果想要开启AOF，那么需要手动进行配置
> > + ```appendonly yes```    将appendonly配置项修改成为yes 即开启AOF
> >
> > 

**修复appendonly.aof文件**

当appendonly.aof文件中的内容发生错位或者是错误，这个时候 redis是无法启动的，我们需要通过某种方式去修复该文件

```redis-check-aof --fix``` : 该工具可以帮助我们修复appendonly.aof文件

**优点**

+ 每一次的修改都能同步，文件的完整性相较于RDB更好
+ 每秒同步一次，只会丢失一秒的数据

**缺点**

+ 相较于数据文件大小来说，AOF文件远大于RDB所产生的文件，修复的速度也比RDB慢
+ AOF运行效率比RDB慢，所以Redis的默认持久化配置是RDB持久化策略



### 总结RDB和AOF

1. RDB持久化方式能够在指定的时间间隔内对你的数据进行快照存储
2. AOF持久化方式是记录每次对Redis服务器的写操作，当服务器重启时会重新执行这些命令来恢复原始数据，AOF命令以Redis协议追加保存每次的写操作到文件末尾，Redis还能对AOF文件进行后台重写，使得AOF文件的体积不至于太大
3. **当我们只需要在redis服务器上做缓存，我们也可以不使用任何的持久化操作**
4. 当我们同时开启两种持久化方式 
   + 在这种情况下，当Redis重启时会优先加载AOF文件来恢复原始的数据，因为一般情况下AOF文件保存的数据集要比RDB文件保存的数据集完整
   + RDB的数据不是实时的，同时使用两者时服务器也只会找AOF文件，**建议使用RDB**
5. 关于性能
   + 因为RDB文件只用作后备用途，建议只保留```save 900 1```这一条RDB的持久化规则

## Redis发布与订阅

**原理**

> + Redis 通过publish , subscribe ,psubscribe 等命令来实现发布和订阅的功
> + 通过subscribe命令订阅某频道，redis-server会在底层维护一个字典，字典中的键就是一个个的频道，而字典中的值则是一个链表，链表中保存了所有订阅这个频道的客户端，subscribe命令是将该客户端添加到给定的频道中
> + 通过publish命令向订阅者发送消息，redis-server会使用给定的频道作为键，在它所维护的频道的字典中查找订阅的所有的客户端，遍历这个链表并将消息发布给所有的订阅者
> + 这一功能主要用于即时聊天，群聊等

| 发布与订阅的命令           | 作用                             |
| -------------------------- | :------------------------------- |
| psubscribe pattern ...     | 订阅一个或者多个给定的频道       |
| pubsub subcommand argument | 查看订阅与发布系统状态           |
| publish channel message    | 将消息发送给指定的频道           |
| punsubscribe pattern ...   | 退订所有给定模式的频道           |
| subscribe channel ...      | 订阅给定的一个或者多个频道的信息 |
| unsubscribe channle ...    | 退订给定的所有频道               |







## 主从复制

> + 主从复制，是指将一台Redis服务器的数据，复制到其他的Redis服务器，前者称为主节点，后者称为从节点，
> + **在主从复制中的数据是单向的，只能从主节点到从节点**，Master（主节点）以写为主，Slave（从节点）以读为主
> + 默认情况下，每台Redis服务器都是主节点，且一个主节点可以有多个从节点，但是一个从节点只能有一个主节点

**特点**

1. 数据冗余：主从复制实现了数据的热备份，是持久化之外的一种数据冗余方式
2. 故障恢复：当主节点出现了问题时，可以由从节点提供服务，实现快速的故障恢复，实际上是一种服务的冗余
3. 负载均衡：在主从复制的基础上，配合读写分离，可以由主节点提供写服务，由从节点提供读服务（即写入redis数据的请求连接主节点，读取redis数据的请求连接从节点），分担服务器负载，尤其是在写少读多的场景下，可以通过配置多个从节点来分担读负载，以此提高Redis服务器的并发量
4. 高可用基石：主从复制是哨兵机制和集群能够实施的基础

> **注意**
>
> + 当主机断开连接时，从机依旧可以作为从机，但是没有了写入的操作，如果主机重新连接时从机依旧可以连接到主机并且可以获取主机写入数据的信息
> + 如果是使用命令来配置主从复制的主机和从机，如果在某个时间重启了服务器，所有的redis服务器都会变回主机这个时候没有从机，需要重新配置主机和从机（可以直接改写配置文件来解决这个问题）

**原理**

1. 从机启动成功连接到主机后会发送一个sync同步命令

2. Master 接收到命令之后会启动后台的存盘进程，同时收集所有接收到的用于修改数据集的命令，在后台进程执行完毕之后，master将传送整个数据文件到从机中，并完成一次完全同步
3. **全量复制** ：从机第一次连接主机时，会接收到来自主机的数据库文件数据，将其存盘并加载到内存中
4. **增量复制** ：主机继续将新的所有收集到的修改命令一次传给从机，完成数据的同步
5. 只要重新连接主机时，就会触发一次完全同步（即全量复制），以此来同步数据





## 缓存击穿和雪崩

### 缓存击穿(访问量大，缓存过期)

> 概述
>
> > + 一个频繁被访问的key到了过期时间，而此时redis中是没有这个key的所以会走MySQL数据库，但是如果此时的访问请求过大，就会造成缓存击穿
> >
> > + 缓存击穿是指一个key访问非常频繁，当这个key到了过期时间的时候，这个key失效的瞬间，持续的大量的并发访问就会直接请求数据库，导致数据库访问压力过大

**解决办法**

1. 在缓存的时候给**过期时间加上一个随机值**，这样就会大幅度的减少缓存在同一时间过期。

2. 对于“Redis挂掉了，请求全部走数据库”这种情况，我们可以有以下的思路：
   事发前：实现Redis的高可用(**主从架构+Sentinel（哨兵） 或者Redis Cluster（集群）)，尽量避免Redis挂掉这种情况发生**。
   事发中：万一Redis真的挂了，我们可以设置**本地缓存**(ehcache)+**限流**(hystrix)，尽量避免我们的数据库被干掉(起码能保证我们的服务还是能正常工作的)
   事发后：**redis持久化，重启后自动从磁盘上加载数据，快速恢复缓存数据**。



### 缓存雪崩 （缓存中没有）

> 概述
>
> > + 缓存雪崩是指在某个时间段，缓存机中过期失效，Redis宕机
> >
> > + 当由大量的访问在redis中并没有匹配到对应的key，那么就会去请求底层的数据库（MySQL）而大量的数据请求Mysql无法顶得住就会造成缓存雪崩

**解决办法**

1. 由于请求的参数是不合法的(每次都请求不存在的参数)，于是我们可以使用**布隆过滤器(BloomFilter)或者压缩filter提前拦截**，不合法就**不让这个请求到数据库层**！
2. 当我们从数据库找不到的时候，我们也将这个**空对象设置到缓存**里边去。下次再请求的时候，就可以从缓存里边获取了。
   **这种情况我们一般会将空对象设置一个较短的过期时间**。

## SpringBoot 集成 Redis

> + SpringBoot 集成 Redis 是通过springData 来操作的，在SpringBoot项目中通过lettuce来实现redis的操作的
> + jedis: 采用直接连接redis数据库，如果是多个线程来操作的话，会造成线程不安全
> + lettuce: 采用的是netty ,一个实例可以在多个线程中进行共享，不存在线程不安全的情况，因为多个线程操作的始终都是同一个来连接对象

### SpringBoot集成Redis实现步骤
1. 导入依赖
2. 配置连接
3. 测试




```
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>2.4.5</version>
</dependency>

 
```


```
#Redis连接地址，127.0.0.1 为本机地址
spring.redis.host=127.0.0.1
#Redis服务器连接端口
spring.redis.port=6379
#Redis服务器连接密码（默认为空）
spring.redis.password=
#连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=8
#连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1
#连接池中的最大空闲连接
spring.redis.pool.max-idle=8
#连接池中的最小空闲连接
spring.redis.pool.min-idle=0
#连接超时时间（毫秒）
spring.redis.timeout=30000
```

```java
package com.redis_springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisSpringbootApplicationTests {

    /**
     * redisTemplate 操作不同的数据类型，api与redis数据中的指令相同
     *
     * opsForValue  操作字符串，即String 类型
     * opsForList   操作List
     * opsForSet    操作Set
     * opsForHash   操作Hash
     * opsForZSet   操作ZSet,即有序set集合
     * opsForGeo    操作GEO
     * opsForHyperloglog
     *
     * redisTemplate 先指定需要操作的数据类型然后再接上需要进行的操作
     *
     * eg:  redisTemplate.opsForValue().set("name","刘佳俊");
     *
     */
    @Autowired
    RedisTemplate redisTemplate;


    @Test
    void redisTest(){

        redisTemplate.opsForValue().set("name","刘佳俊");
        System.out.println(redisTemplate.opsForValue().get("name"));



//        获取redis的连接对
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        对redis数据库连接对象进行操作
        connection.flushDb();
    }

}
```
#### 自定义redisTemplate

```java

package com.redis_springboot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;

@Configurable
public class RedisConfig {

    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

//        Json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

//        String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

//        key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
//        hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
//        value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
//        hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


}
```

#### 关于redis的javaUtils工具类
```java

package com.redis_springboot.util;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public Set<String> keys(String keys){
        try {
            return redisTemplate.keys(keys);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 普通缓存放入, 不存在放入，存在返回
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setnx(String key, Object value) {
        try {
            redisTemplate.opsForValue().setIfAbsent(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间,不存在放入，存在返回
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean setnx(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }
    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }
    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }
    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }
    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // ===============================list=================================
    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存
     *
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
```