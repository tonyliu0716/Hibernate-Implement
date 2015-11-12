package org.tarena.util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//用于获取Session的工具类
public class HibernateUtilByTreadLocal {
	
	private static SessionFactory sf;
	
	//使用TreadLocal来管理Session，它是线程相关的工具类，可以管理对象
	//它可以给一个线程绑定一个唯一的对象，其本质是根据map来存储对象，
	//而map的key是线程名。每次发送的请求都是一个新线程！ThreadLocal是线程安全的
	private static ThreadLocal<Session> tl = new ThreadLocal<Session>();
	
	static {
		Configuration conf = new Configuration();
		//加载主配置文件
		conf.configure("/hibernate.cfg.xml");
		//创建SessionFactory
		sf = conf.buildSessionFactory();
	}
	
	//使用TreadLocal管理Session，将其绑定到线程
	public static Session getSession() {
		
		//contThreadLocal中，自动以线程名取session
		Session session = tl.get();
		//第一次为空，则创建session
		if(session == null) {
			session = sf.openSession();
			tl.set(session);
		}
		return session;
	}
	
	public static void close() {
		//取得当前线程绑定的session
		Session session = tl.get();
		if(session != null) {
			session.close();
			//关闭session后，将其从TreadLocal中移除
			tl.remove();
		}
	}
}