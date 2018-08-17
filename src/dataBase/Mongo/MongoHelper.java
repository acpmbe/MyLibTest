package dataBase.Mongo;

import java.util.List;

import org.bson.Document;
import com.mongodb.*;
import com.mongodb.client.*;

public class MongoHelper
{

	private static String ConnStr = null;
	private static String DataBaseName = null;
	private static int ServerSelectionTimeout;

	private static MongoClientOptions.Builder buide;
	private static MongoClientOptions myOptions;

	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static Object object = new Object();

	/**
	 * 初始化数据库
	 * 
	 * @param connStr
	 *            连接字符串（举例：192.168.135.25:2222）
	 * @param dataBaseName
	 *            数据库名称
	 * @param serverSelectionTimeout
	 *            数据库异常重连超时（秒）
	 * 
	 */
	public static void Init(String connStr, String dataBaseName, int serverSelectionTimeout)
	{
		ConnStr = connStr;
		DataBaseName = dataBaseName;
		ServerSelectionTimeout = serverSelectionTimeout;

	}

	
	/**
	 * 得到集合内行数
	 * @param collName  集合名称
	 * @return 返回集合内行数
	 */ 
	public Long Count(String collName)
	{

		MongoCollection<Document> collection = GetColl(collName);
		return collection.count();

	}

	/**
	 * 批量插入
	 * 
	 * @param collName
	 *            集合名称
	 * @param list
	 *            文档列表
	 */
	public void Insert(String collName, List<Document> list)
	{

		MongoCollection<Document> collection = GetColl(collName);
		collection.insertMany(list);

	}

	/**
	 * 批量更新
	 * 
	 * @param collName
	 *            集合名称
	 * @param find
	 *            查找文档
	 * @param set
	 *            更新文档
	 */
	public void Update(String collName, Document find, Document set)
	{

		MongoCollection<Document> collection = GetColl(collName);
		collection.updateMany(find, set);

	}

	/**
	 * 查询并执行委托
	 * 
	 * @param collName
	 *            集合名称
	 * @param find
	 *            查找文档
	 * @param block
	 *            委托
	 */
	public void Select(String collName, Document find, Block<Document> block)
	{

		MongoCollection<Document> collection = GetColl(collName);
		FindIterable<Document> iter = collection.find(find);
		iter.forEach(block);

	}

	/**
	 * 得到集合
	 * 
	 * @param collName
	 *            集合名称
	 * @return 返回集合
	 */
	private MongoCollection<Document> GetColl(String collName)
	{
		MongoDatabase database = GetConnect();
		return database.getCollection(collName);
	}

	private static MongoDatabase GetConnect()
	{

		if (database == null)
		{

			synchronized (object)
			{

				if (database == null)
				{

					ConnServer();

				}

			}

		}
		return database;

	}

	private static void ConnServer()
	{
		buide = new MongoClientOptions.Builder();
		buide.connectionsPerHost(100);// 与目标数据库可以建立的最大链接数

		buide.connectTimeout(2 * 60 * 1000);// 与数据库建立链接的超时时间
		buide.maxWaitTime(2 * 60 * 1000);// 一个线程成功获取到一个可用数据库之前的最大等待时间

		buide.serverSelectionTimeout(ServerSelectionTimeout * 1000);

		buide.threadsAllowedToBlockForConnectionMultiplier(100);
		// buide.socketTimeout(0);
		buide.socketKeepAlive(true);
		myOptions = buide.build();
		mongoClient = new MongoClient(ConnStr, myOptions);
		database = mongoClient.getDatabase(DataBaseName);

	}

}
