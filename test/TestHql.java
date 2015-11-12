package test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Test;
import org.tarena.entity.Emp;
import org.tarena.entity.Service;
import org.tarena.util.HibernateUtil;

public class TestHql {
	
	//演示在hql查询时如何增加条件
	//@Test
	public void test1 () {
		String hql = "from Service where unix_host=?";
		Session session = HibernateUtil.getSession();
		
		Query query = session.createQuery(hql);
		//query.setParameter(0,"...")可以不区分类型
		query.setString(0, "192.168.0.20");
		List<Service> list = query.list();
		for(Service s : list) {
			System.out.println(s.getId() + " " + s.getOs_username() + " " + s.getUnix_host());
		}
		session.close();
	}
	
	//演示查询一部分字段
	@Test
	public void test2() {
		String hql = "select id, os_username, unix_host" + 
				" from Service where unix_host=?";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, "192.168.0.20");
		//这里的list返回不再是个具体类型的对象，而是Object数组
		List<Object[]> list = query.list();
		for(Object[] o : list) {
			System.out.println(o[0] + " " + o[1] + " " + o[2]);
		}
		session.close();
	}
	
	//演示分页查询
	@Test
	public void test3() {
		//假设现在是第二页，页面显示3条数据
		int page = 2;
		int pageSize = 3;
		String hql = "from Emp";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		//设置每页的起始行 (page - 1) * pageSize
		query.setFirstResult((page - 1) * pageSize);
		//设置每页显示行数
		query.setMaxResults(pageSize);
		List<Emp> emps = query.list();
		for(Emp e : emps) {
			System.out.println(e.getEmpno() + " " + e.getEname());
		}
		session.close();
	}
	
	//演示查询总行数
	//可以直接在hql中使用聚合函数：count, sum, average, max, min
	@Test
	public void test4() {
		int pageSize = 3;
		
		String hql = "select count(*) from Emp";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		//uniqueResult会返回一个Object
		Object obj = query.uniqueResult();
		//实际这个obj就是一个数字，返回的是总行数
		int rows = Integer.valueOf(obj.toString());
		//计算总页数
		if(rows % pageSize == 0) {
			System.out.println(rows);
		}else {
			System.out.println(rows / pageSize + 1);
		}
	}
	
	//演示多表联合查询,联合查询时，是通过对象的属性作为桥梁进行查询
	@Test
	public void test5() {
		String hql = "select s.id, s.os_username, s.unix_host"
				+ ", a.id, a.real_name, a.idcard_no "
				+ "from Account a, Service s where a.id=s.account.id"
				+ " and s.unix_host=?";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, "192.168.0.20");
		List<Object[]> list = query.list();
		for(Object[] obj: list) {
			System.out.println(obj[0] + " " + obj[1] + " " + obj[2] + " " + obj[3]);
		}
	}
	
	//演示join，join的时候不能join对象，而是join自身的属性!而且不能写on字句
	//Account join 到 Service
	@Test
	public void test6() {
		String hql = "select s.id, s.os_username, s.unix_host,"
				+ "a.id, a.real_name, a.idcard_no "
				+ "from Account a inner join a.services s where s.unix_host=?";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, "192.168.0.20");
		List<Object[]> list = query.list();
		for(Object[] obj: list) {
			System.out.println(obj[0] + " " + obj[1] + " " + obj[2] + " " + obj[3]);
		}
	}
	
	//join
	//Service join 到 Account
	@Test
	public void test7() {
		String hql = "select s.id, s.os_username, s.unix_host,"
				+ "a.id, a.real_name, a.idcard_no "
				+ "from Service s join s.account a where s.unix_host=?";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, "192.168.0.20");
		List<Object[]> list = query.list();
		for(Object[] obj : list) {
			System.out.println(obj[0] + " " + obj[1] + " " + obj[2] + " " + obj[3]);
		}
	}
	
	
	//最简洁形式的多表联合查询,Service中有一个属性Account account
	@Test
	public void test8() {
		String hql = "select s.id, s.os_username, s.unix_host,"
				+ "s.account.id, s.account.real_name, s.account.idcard_no "
				+ "from Service s where s.unix_host=?";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, "192.168.0.20");
		List<Object[]> list = query.list();
		for(Object[] obj : list) {
			System.out.println(obj[0] + " " + obj[1] + " " + obj[2] + " " + obj[3]);
		}
	}
	
	//直接使用SQL查询
	@Test
	public void test9() {
		String sql = "select * from nerdluv.Service where unix_host=?";
		Session session = HibernateUtil.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, "192.168.0.20");
		List<Object[]> list = query.list();
		for(Object[] obj: list) {
			System.out.println(obj[0] + " " + obj[1] + " " + obj[2] + " " + obj[3]);
		}
	}
	
	//直接使用SQL查询
	//需要返回的集合中封装的是实体对象
	@Test
	public void test10() {
		String sql = "select * from nerdluv.Service where unix_host=?";
		Session session = HibernateUtil.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, "192.168.0.20");
		//指定集合中封装的类型
		query.addEntity(Service.class);
		List<Service> services = query.list();
		for(Service s : services) {
			System.out.println(s.getOs_username() + " " + s.getLogin_passwd());
		}
	}
}
