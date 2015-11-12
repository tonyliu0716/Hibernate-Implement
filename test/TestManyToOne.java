package test;


import org.hibernate.Session;
import org.junit.Test;
import org.tarena.entity.Account;
import org.tarena.entity.Service;
import org.tarena.util.HibernateUtil;

public class TestManyToOne {
	//演示多对一的关联查询
	@Test
	public void test1() {
		Session session = HibernateUtil.getSession();
		Service s = (Service)session.get(Service.class, 2002);
		System.out.println(s.getId() + " " + s.getOs_username());
		
		System.out.println("======= 多对一查询，延迟加载=========");
		
		Account a = s.getAccount();
		System.out.println(a.getId() + " " + a.getIdcard_no() + " " + a.getReal_name());
		session.close();
	}
}
