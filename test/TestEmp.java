package test;

import java.sql.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.tarena.entity.Emp;
import org.tarena.util.HibernateUtil;



public class TestEmp {
	
	//select one row from database
	@Test
	public void test1() {
		Session session = HibernateUtil.getSession();
		Emp e = (Emp)session.get(Emp.class, 1);
		System.out.println(e.getEname() + " " + e.getJob());
		session.close(); 
	}
	
	//insert one row 
	@Test
	public void test2() {
		//模拟要新增的员工数据
		Emp e = new Emp();
		e.setEmpno(6);
		e.setEname("tarena");
		e.setSal(8000.00);
		e.setMgr(1200);
		e.setComm(2300.00);
		e.setDeptno(2);
		e.setHiredate(Date.valueOf("1989-03-04"));
		e.setJob("Software Developer");
		
		//get Session
		Session session = HibernateUtil.getSession();
		//开启事务
		Transaction ts = session.beginTransaction();
		//新增数据
		try {
			session.save(e);
			//提交事务
			ts.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			//回滚事务
			ts.rollback();
		}finally{
			session.close();
		}
	}
	
	//modify one data from database
	@Test
	public void test3() {
		Session session = HibernateUtil.getSession();
		//查询要修改的数据
		Emp e = (Emp)session.get(Emp.class, 6);
		//模拟修改数据
		e.setEname("Luke");
		e.setSal(8500.00);
		e.setComm(8000.00);
		//开启事务
		Transaction ts = session.beginTransaction();
		try {
			session.update(e);
			ts.commit();
		} catch (HibernateException e1) {
			e1.printStackTrace();
			ts.rollback();
		}
		session.close();
	}
	
	//delete by id
	@Test
	public void test4() {
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		Emp e = new Emp();
		e.setEmpno(6);
		try {
			session.delete(e);
			ts.commit();
		} catch (HibernateException e1) {
			e1.printStackTrace();
			ts.rollback();
		}
		session.close();
	}
}
