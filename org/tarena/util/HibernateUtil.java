package org.tarena.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//用于获取Session的工具类
public class HibernateUtil {
	private static SessionFactory sf;
	
	static {
		Configuration conf = new Configuration();
		//加载主配置文件
		conf.configure("/hibernate.cfg.xml");
		//创建SessionFactory
		sf = conf.buildSessionFactory();
	}
	
	public static Session getSession() {
		return sf.openSession();
	}
}
