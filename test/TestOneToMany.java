package test;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tarena.entity.Account;
import org.tarena.entity.Service;
import org.tarena.util.HibernateUtil;

public class TestOneToMany {
	
	@Test
	public void test1() {
		Session session = HibernateUtil.getSession();
		Account a = (Account)session.get(Account.class, 1011);
		System.out.println(a.getId() + " " + a.getIdcard_no() + " " + a.getReal_name());
		
		System.out.println("========= 一对多关系，属性延迟加载 ========");
		
		Set<Service> services = a.getServices();
		for(Service s : services) {
			System.out.println(s.getId() + " " + s.getOs_username());
		}
	}
	
	//测试级联添加，即添加Account的同时，添加Service
	@Test
	public void test2() {
		//模拟一个Account，从页面传过来
		Account a = new Account();
		a.setLogin_name("tangseng");
		a.setLogin_passwd("123");
		a.setStatus("0");
		a.setCreate_date(Date.valueOf("2015-05-09"));
		a.setReal_name("ts");
		a.setIdcard_no("123456198607081234");
		a.setBirthdate(Date.valueOf("1986-07-08"));
		a.setTelephone("10101123");
		
		
		//模拟要新增的业务账号
		Service s1 = new Service();
		s1.setAccount(a);
		s1.setUnix_host("192.168.0.1");
		s1.setOs_username("t1");
		s1.setLogin_passwd("123");
		s1.setStatus("0");
		s1.setCreate_date(Date.valueOf("2015-05-09"));
		s1.setCost_id(1);
		
		Service s2 = new Service();
		s2.setAccount(a);
		s2.setUnix_host("192.168.1.3");
		s2.setOs_username("t2");
		s2.setLogin_passwd("123");
		s2.setStatus("0");
		s2.setCreate_date(Date.valueOf("2015-05-09"));
		s2.setCost_id(1);
		
		//将Account的services属性关联上模拟的s1,s2
		Set<Service> set = new HashSet<Service>();
		a.setServices(set);
		a.getServices().add(s1);
		a.getServices().add(s2);
		
		//执行插入操作
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		try {
			session.save(a);
			ts.commit();
		} catch (HibernateException e) {
			ts.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
}
