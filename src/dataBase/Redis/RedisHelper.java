package dataBase.Redis;

import redis.clients.jedis.*;

public class RedisHelper
{

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static final int MAX_ACTIVE = -1;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static final int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static final int MAX_WAIT = 10000;

	private static final int TIMEOUT = 100000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static final boolean TEST_ON_BORROW = false;

	private static JedisPool JedisPool;
	private static Jedis Dis;

	private static String RedisIP;
	private static int Port;
	private static String RedisPassWord;
	private static int RedisDb;

	private static Long TimeWait_YZ;

	public static void Init(String redisIP, int port, String redisPassWord, int redisDb)
	{
		RedisIP = redisIP;
		Port = port;
		RedisPassWord = redisPassWord;
		RedisDb = redisDb;
	}

	public static void Init(String redisIP, int port, String redisPassWord, int redisDb, Long timeWait_YZ)
	{
		RedisIP = redisIP;
		Port = port;
		RedisPassWord = redisPassWord;
		RedisDb = redisDb;
		TimeWait_YZ = timeWait_YZ;
		Conn_YZ(TimeWait_YZ);
	}

	@SuppressWarnings("unused")
	private static void Conn_YZ(final long timeWait)
	{
		Thread t = new Thread(new Runnable()
		{

			public void run()
			{

				while (true)
				{

					try
					{
						Thread.sleep(timeWait * 1000);
						GetJedis(true);
					}
					catch (Exception e)
					{

						System.out.println("Mysql验证连接错误：");
						e.printStackTrace();
					}

				}

			}
		});
		t.start();
	}

	/**
	 * 取List中数量。
	 * 
	 * @param collName
	 *            List名称
	 * @return 返回List数量（Long）
	 */
	public Long ListSize(String collName)
	{
		Jedis dis = GetJedis(false);
		return dis.llen(collName);

	}

	/**
	 * 得到List数据(先入先取),如果没有返回Null。
	 * 
	 * @param collName
	 *            List名称
	 * @return 返回字符串数据
	 */
	public String ListRpop(String collName)
	{
		Jedis dis = GetJedis(false);
		return dis.rpop(collName);

	}

	/**
	 * 得到List数据(后入先取),如果没有返回Null。
	 * 
	 * @param collName
	 *            List名称
	 * @return 返回字符串数据
	 */
	public String ListLpop(String collName)
	{
		Jedis dis = GetJedis(false);
		return dis.lpop(collName);

	}

	public synchronized static Jedis GetJedis(boolean isYZ)
	{

		if (isYZ)
		{
			try
			{
				Dis.ping();
				System.out.println("Redis验证连接 = YES");

			}
			catch (Exception e)
			{

				System.out.println("Redis验证连接 = NO：" + e.getMessage());
				Conn();
			}

		}
		else
		{
			if (Dis == null)
			{
				Conn();

			}

		}

		return Dis;

	}

	private static void Conn()
	{
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWait(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);

		JedisPool = new JedisPool(config, RedisIP, Port, TIMEOUT, RedisPassWord, RedisDb);
		Dis = JedisPool.getResource();
	}

}
