package util;

public class MyString
{

	/**
	 * 16进制转换成为string类型编码字符串
	 * @param s 字符串类型Byte
	 * @return 字符编码字符串。
	 */
	public static String Gets(String s)
	{
		if (s == null || s.equals(""))
		{
			return null;
		}
		s = s.replace(" ", "");
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++)
		{
			try
			{
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			s = new String(baKeyword, "UTF-8");
			new String();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return s;
	}

}
