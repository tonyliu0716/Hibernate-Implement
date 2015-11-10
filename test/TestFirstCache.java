package test;

import org.hibernate.Session;
import org.junit.Test;
import org.tarena.entity.Emp;
import org.tarena.util.HibernateUtil;

//一级缓存
//first level caching
public class TestFirstCache {
	
	//use the same session to search same data 2 times
	//if console do not show SQL query two times
	//that means the second time, data from first level caching
	@Test
	public void test1() {
		Session session = HibernateUtil.getSession();
		Emp e1 = (Emp)session.get(Emp.class, 1);
		System.out.println("first time: " + e1.getEname() );
		
		System.out.println("=========");
		
		Emp e2 = (Emp)session.get(Emp.class, 1);
		System.out.println("second time: " + e2.getEname());
		
		session.close();
	}
	
	//use two session to search same data 
	@Test
	public void test2() {
		Session session1 = HibernateUtil.getSession();
		Emp e1 = (Emp)session1.get(Emp.class, 1);
		System.out.println("first time: " + e1.getEname());
		
		System.out.println("==========");
		
		Session session2 = HibernateUtil.getSession();
		Emp e2 = (Emp)session2.get(Emp.class, 1);
		System.out.println("second time: " + e2.getEname());
		
		session1.close();
		session2.close();
	}
	
	//use the same session search two different data
	//console will show two query
	@Test
	public void test3() {
		Session session = HibernateUtil.getSession();
		Emp e1 = (Emp) session.get(Emp.class, 1);
		System.out.println("first time: " + e1.getEname());
		
		System.out.println("============");
		
		Emp e2 = (Emp)session.get(Emp.class, 2);
		System.out.println("second time: " + e2.getEname());
		
		session.close();
	}
}
