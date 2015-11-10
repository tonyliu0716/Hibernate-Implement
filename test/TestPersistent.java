package test;

import java.sql.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tarena.entity.Emp;
import org.tarena.util.HibernateUtil;

public class TestPersistent {
	// 验证持久态对象存在于一级缓存中
	// 先新增一个数据，则该数据时持久态的。然后再查询这个数据，看输出了几次SQL

	@Test
	public void test1() {
		// 模拟要新增的员工数据
		Emp e = new Emp();
		e.setEmpno(7);
		e.setEname("tarena");
		e.setSal(8000.00);
		e.setMgr(1200);
		e.setComm(2300.00);
		e.setDeptno(2);
		e.setHiredate(Date.valueOf("1989-03-04"));
		e.setJob("Software Developer");

		// get Session
		Session session = HibernateUtil.getSession();
		// 开启事务
		Transaction ts = session.beginTransaction();
		// 新增数据
		try {
			session.save(e);
			//查询刚刚新增的数据，看是否输出查询SQL
			Emp e1 = (Emp)session.get(Emp.class, 7);
			System.out.println(e1.getEname() + " " + e1.getJob());
			// 提交事务
			ts.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			// 回滚事务
			ts.rollback();
		} finally {
			session.close();
		}
	}
	
	//验证持久态对象可以自动更新到数据库
	@Test
	public void test2() {
		// 模拟要新增的员工数据
				Emp e = new Emp();
				e.setEmpno(8);
				e.setEname("WWW");
				e.setSal(8000.00);
				e.setMgr(1200);
				e.setComm(2300.00);
				e.setDeptno(2);
				e.setHiredate(Date.valueOf("1989-03-04"));
				e.setJob("Software Developer");

				// get Session
				Session session = HibernateUtil.getSession();
				// 开启事务
				Transaction ts = session.beginTransaction();
				// 新增数据
				try {
					session.save(e);
					//如果save以后，该对象是持久态的，那么commit的时候会flush到数据库中
					e.setEname("QQQ");
					// 提交事务
					ts.commit();
					
				} catch (HibernateException ex) {
					ex.printStackTrace();
					// 回滚事务
					ts.rollback();
				} finally {
					session.close();
				}
	}
	
	//验证自动更新的时机是session.flush()
	//先得到持久态对象，然后修改该对象，调用session.flush(),查看是否有更新的SQL
	//会执行更新，但是不会commit!!因为flush没有commit的能力
	@Test
	public void test3() {
		//通过查询得到持久态的对象
		Session session = HibernateUtil.getSession();
		Emp e = (Emp)session.get(Emp.class, 8);
		e.setEname("JJJ");
		session.flush();
		session.close();
	}
}
