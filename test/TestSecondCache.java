package test;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.tarena.entity.Emp;
import org.tarena.util.HibernateUtil;

public class TestSecondCache {
	//演示二级缓存
	//show how to use second cache
	//使用两个session访问同一条数据，如果只输出一次SQL，则说明数据在session中共享
	//那么就验证了二级缓存的特征
	@Test
	public void test1() {
		Configuration conf = new Configuration();
		//加载主配置文件
		conf.configure("/hibernate.cfg.xml");
		//创建SessionFactory
		SessionFactory sf = conf.buildSessionFactory();
		
		//第一个session
		Session session1 = sf.openSession();
		Emp e1 = (Emp)session1.get(Emp.class, 1);
		System.out.println(e1.getEname());
		
		//或者可以使用SessionFactory移除二级缓存中的Emp对象
		//sf.evict(Emp.class);
		System.out.println("=====================");
		
		Session session2 = sf.openSession();
		Emp e2 = (Emp)session2.get(Emp.class, 1);
		System.out.println(e2.getEname());
	}
	
	//演示查询缓存
	@Test
	public void test2() {
		//先执行一次查询
		String hql1 = "select count(*) from Emp";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql1);
		//开启本次查询缓存
		query.setCacheable(true);
		Object obj = query.uniqueResult();
		int row = Integer.valueOf(obj.toString());
		System.out.println(row);
		
		//移除查询缓存的数据
		HibernateUtil.getSessionFactory().evictQueries();
		
		System.out.println("=====================");
		//再执行一次相同的查询
		String hql2 = "select count(*) from Emp";
		String hql3 = "select count(*) from Emp where 1=1";
		Query query1 = session.createQuery(hql2);
		//开启本次查询缓存
		query1.setCacheable(true);
		System.out.println(query1.uniqueResult());
	}
}
