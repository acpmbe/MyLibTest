package dataBase;

import org.bson.Document;
import com.mongodb.Block;

import dataBase.Mongo.MongoHelper;
import dataBase.Mysql.MysqlHelper;
import dataBase.Redis.RedisHelper;
import interFace.IRun;
import java.sql.*;
import java.util.*;

public class Run implements IRun
{

	@Override
	public void RunS()
	{
		Redis_Conn_YZ();
	}

	/**
	 * 查询总数，并带数据重连功能。
	 */
	@SuppressWarnings("unused")
	private void Mysql_Add_YZ()
	{

		try
		{
			String mysqlUrl = "jdbc:mysql://10.120.0.37:3306/tendency?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedStatements=true";
			String mysqlUser = "root";
			String mysqlPassWord = "123456";

			MysqlHelper.Init(mysqlUrl, mysqlUser, mysqlPassWord, 20L);

			MysqlHelper mysqlHelper = new MysqlHelper();

			String sql = "SELECT * FROM Test_3";

			for (int i = 0; i < 100; i++)
			{
				Thread.sleep(3 * 1000);
				int count = mysqlHelper.getCount(sql);
				System.out.println("记录总数：" + count);
			}

			mysqlHelper.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void Mysql_Count()
	{
		String mysqlUrl = "jdbc:mysql://10.120.0.37:3306/tendency?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedStatements=true";
		String mysqlUser = "root";
		String mysqlPassWord = "123456";
		MysqlHelper.Init(mysqlUrl, mysqlUser, mysqlPassWord);

		MysqlHelper mysqlHelper = new MysqlHelper();

		String sql = "SELECT * FROM Test_3";

		try
		{
			int count = mysqlHelper.getCount(sql);
			System.out.println("记录总数：" + count);
			mysqlHelper.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private void Mysql_Delete()
	{
		String mysqlUrl = "jdbc:mysql://10.120.0.37:3306/tendency?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedStatements=true";
		String mysqlUser = "root";
		String mysqlPassWord = "123456";
		MysqlHelper.Init(mysqlUrl, mysqlUser, mysqlPassWord);

		MysqlHelper mysqlHelper = new MysqlHelper();

		String sql = "DELETE FROM Test_3 WHERE LID=555";

		try
		{
			if (mysqlHelper.executeNonquery(sql))
			{
				System.out.println("删除成功！！");
			}
			mysqlHelper.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private void Mysql_Insert()
	{
		String mysqlUrl = "jdbc:mysql://10.120.0.37:3306/tendency?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedStatements=true";
		String mysqlUser = "root";
		String mysqlPassWord = "123456";
		MysqlHelper.Init(mysqlUrl, mysqlUser, mysqlPassWord);

		MysqlHelper mysqlHelper = new MysqlHelper();

		String sql = "INSERT INTO Test_3 (BID,LID)VALUES(333,555)";

		try
		{
			boolean IsOK = mysqlHelper.executeNonquery(sql);
			mysqlHelper.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private void Mysql_Select()
	{
		String mysqlUrl = "jdbc:mysql://10.120.0.37:3306/tendency?useUnicode=true&amp;characterEncoding=utf-8&amp;rewriteBatchedStatements=true";
		String mysqlUser = "root";
		String mysqlPassWord = "123456";
		MysqlHelper.Init(mysqlUrl, mysqlUser, mysqlPassWord);

		MysqlHelper mysqlHelper = new MysqlHelper();

		String sql = "SELECT * FROM Test_3";

		try
		{
			ResultSet retsult = mysqlHelper.Select(sql);
			while (retsult.next())
			{

				System.out.println("BID：" + retsult.getString("BID") + "  LID：" + retsult.getLong("LID"));

			}

			retsult.close();
			mysqlHelper.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 连接有效性测试。
	 */
	@SuppressWarnings("unused")
	private void Redis_Conn_YZ()
	{
		String url = "10.120.0.37";
		int port = 6380;
		String passWord = "tendency123456";
		int Db = 1;

		RedisHelper.Init(url, port, passWord, Db, 10L);

		String ListName = "Redis_Test";

		RedisHelper h = new RedisHelper();

		while (true)
		{

			try
			{
				Thread.sleep(3 * 1000);
				Long sLong = h.ListSize(ListName);

				System.out.println("List数量：" + sLong);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * 取List数据，后入先取。
	 */
	@SuppressWarnings("unused")
	private void Redis_Lpop()
	{
		String url = "10.120.0.37";
		int port = 6380;
		String passWord = "tendency123456";
		int Db = 1;

		RedisHelper.Init(url, port, passWord, Db);

		String ListName = "Redis_Test";

		RedisHelper h = new RedisHelper();
		String content = h.ListLpop(ListName);

		System.out.println("List数量：" + content);

	}

	/**
	 * 取List数据，先入先取。
	 */
	@SuppressWarnings("unused")
	private void Redis_Rpop()
	{
		String url = "10.120.0.37";
		int port = 6380;
		String passWord = "tendency123456";
		int Db = 1;

		RedisHelper.Init(url, port, passWord, Db);

		String ListName = "Redis_Test";

		RedisHelper h = new RedisHelper();
		String content = h.ListRpop(ListName);

		System.out.println("List数量：" + content);

	}

	@SuppressWarnings("unused")
	private void Redis_Len()
	{
		String url = "10.120.0.37";
		int port = 6380;
		String passWord = "tendency123456";
		int Db = 1;

		RedisHelper.Init(url, port, passWord, Db);

		String ListName = "Redis_Test";

		RedisHelper h = new RedisHelper();
		Long sLong = h.ListSize(ListName);

		System.out.println("List数量：" + sLong);

	}

	/**
	 * 验证数据有效性测试。
	 */
	@SuppressWarnings("unused")
	private void Mongo_Conn_YZ()
	{

		String url = "10.120.0.37:2222";
		String dataBaseName = "tendency";
		String CollName = "Test_1";

		MongoHelper.Init(url, dataBaseName, 5); // 验证数据有效性超时时间。

		MongoHelper mongoHelper = new MongoHelper();

		while (true)
		{
			try
			{
				Thread.sleep(3 * 1000);
				Long count = mongoHelper.Count(CollName);
				System.out.println("数量：" + count);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unused")
	private void Mongo_Count()
	{

		String url = "192.168.135.25:27017";
		String dataBaseName = "tendency";
		String CollName = "tendencydata_New";

		MongoHelper.Init(url, dataBaseName, 5);

		MongoHelper mongoHelper = new MongoHelper();

		Long count = mongoHelper.Count(CollName);
		System.out.println("数量：" + count);

	}

	@SuppressWarnings("unused")
	private void Mongo_Update()
	{

		String url = "192.168.135.25:27017";
		String dataBaseName = "tendency";
		String CollName = "tendencydata_New";

		MongoHelper.Init(url, dataBaseName, 5);

		MongoHelper mongoHelper = new MongoHelper();

		Document find = new Document("A", 5);
		Document set = new Document("$set", new Document("C", 35));

		mongoHelper.Update(CollName, find, set);

	}

	@SuppressWarnings("unused")
	private void Mongo_Insert()
	{

		String url = "192.168.135.25:27017";
		String dataBaseName = "tendency";
		String CollName = "tendencydata_New";

		MongoHelper.Init(url, dataBaseName, 5);

		MongoHelper mongoHelper = new MongoHelper();

		List<Document> list = new ArrayList<Document>();
		Document document = null;

		for (int i = 5; i < 13; i++)
		{
			document = new Document();
			document.append("A", i);
			document.append("B", i);
			document.append("C", i);
			list.add(document);

		}

		mongoHelper.Insert(CollName, list);

	}

	@SuppressWarnings("unused")
	private void Mongo_Select()
	{
		String url = "192.168.135.25:27017";
		String dataBaseName = "tendency";

		MongoHelper.Init(url, dataBaseName, 5);

		MongoHelper mongoHelper = new MongoHelper();
		Document find = new Document("A", 1);

		Block<Document> block = new Block<Document>()
		{

			@Override
			public void apply(Document tc)
			{

				int A = tc.getInteger("A");
				int B = tc.getInteger("B");
				int C = tc.getInteger("C");

				System.out.println("A：" + A + "  B：" + B + "  C：" + C);

			}

		};

		mongoHelper.Select("tendencydata_New", find, block);

	}

}
