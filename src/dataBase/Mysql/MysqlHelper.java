package dataBase.Mysql;

import java.sql.*;

/**
 * Mysql帮助类
 * 
 * @author ACP
 *
 */
public class MysqlHelper
{

	private static final String DriverName = "com.mysql.jdbc.Driver"; // 程序驱动

	private static java.sql.Connection dataConnect;

	private static String MysqlUrl;
	private static String MysqlUser;
	private static String MysqlPassWord;

	public PreparedStatement pst = null;

	/**
	 * 连接初始化
	 * 
	 * @param mysqlUrl
	 *            连接数据库地址。
	 * @param mysqlUser
	 *            连接数据库用户名。
	 * @param mysqlPassWord
	 *            连接数据库密码。
	 */
	public static void Init(String mysqlUrl, String mysqlUser, String mysqlPassWord)
	{
		MysqlUrl = mysqlUrl;
		MysqlUser = mysqlUser;
		MysqlPassWord = mysqlPassWord;

	}

	/**
	 * 连接初始化(带数据库重连功能)
	 * 
	 * @param mysqlUrl
	 *            连接数据库地址。
	 * @param mysqlUser
	 *            连接数据库用户名。
	 * @param mysqlPassWord
	 *            连接数据库密码。
	 * @param timeWait_YZ
	 *            数据库重连机制等待时间（秒）
	 */
	public static void Init(String mysqlUrl, String mysqlUser, String mysqlPassWord, Long timeWait_YZ)
	{
		MysqlUrl = mysqlUrl;
		MysqlUser = mysqlUser;
		MysqlPassWord = mysqlPassWord;
		Conn_YZ(timeWait_YZ);
	}

	/**
	 * 关闭数据库连接
	 */
	public void close()
	{
		try
		{

			this.pst.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void ConnData()
	{
		try
		{
			Class.forName(DriverName);
			dataConnect = DriverManager.getConnection(MysqlUrl, MysqlUser, MysqlPassWord);
		}
		catch (Exception e)
		{
			System.out.println("连接Mysql数据库错误：");
			e.printStackTrace();
		}

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
						GetConnect(true);
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
	 * 得到数据库连接，
	 * 
	 * @param isYZ
	 *            是否验证数据库连接有效性
	 * @return 返回数据库连接
	 * @throws Exception
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private synchronized static Connection GetConnect(boolean isYZ) throws Exception
	{

		if (isYZ)
		{
			if (dataConnect == null)
			{
				ConnData();
			}
			else
			{

				if (!dataConnect.isValid(5))
				{
					System.out.println("Mysql验证连接 = NO");
					ConnData();
					System.out.println("Mysql断开后重新连接成功");

				}
				else
				{
					System.out.println("Mysql验证连接 = YES");
				}

			}

		}
		else
		{
			if (dataConnect == null)
			{
				ConnData();
			}
		}

		return dataConnect;
	}
	
	
	/*
	 * 得到数据库连接（不建议使用此连接，使用直接查询方式。）
	 */
	public static Connection GetConnect() throws Exception
	{
		return GetConnect(false);
	}

	/**
	 * 插入、修改、删除等操作
	 * 
	 * @param sql
	 *            Sql语句
	 * @return 是否正确处理
	 */
	public boolean executeNonquery(String sql)
	{
		boolean flag = false;
		try
		{

			Connection conn = GetConnect(false);
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			flag = true;

		}
		catch (Exception e)
		{
			System.out.println("Mysql数据库插更删时出现错误！！");
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查询返回结果。
	 * 
	 * @param sql
	 *            Sql语句。
	 * @return 返回ResultSet结果。
	 * @throws Exception
	 */
	public ResultSet Select(String sql)
	{

		ResultSet retsult = null;
		try
		{
			Connection conn = GetConnect(false);
			pst = conn.prepareStatement(sql);
			retsult = pst.executeQuery();

		}
		catch (Exception e)
		{

			System.out.println("Mysql查询错误：");
			e.printStackTrace();

		}

		return retsult;

	}

	/**
	 * 获取表记录数
	 * 
	 * @param sql
	 *            Sql语句
	 * @return Int 记录数
	 */
	public int getCount(String sql)
	{
		int count = 0;
		try
		{
			Connection conn = GetConnect(false);
			pst = conn.prepareStatement(sql);
			ResultSet resultSet = pst.executeQuery();

			resultSet.last();
			count = resultSet.getRow();
			resultSet.close();

		}
		catch (Exception e)
		{
			System.out.println("查询总记录数错误！");
			e.printStackTrace();
		}
		return count;
	}

}
