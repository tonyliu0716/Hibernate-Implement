package test;

import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.tarena.entity.Emp;
import org.tarena.util.HibernateUtil;

//Test lazy load 
//two methods: session.load()  query.iterate()
public class TestLazyLoad {
	//如果控制台输出SQL，证明不是延迟加载的
	@Test
	public void test1() {
		Session session = HibernateUtil.getSession();
		//调用load方法时，会实例化Emp，然后设置ID值
		//只有使用除了ID外的其他属性时，才会触发查询
		Emp e = (Emp)session.load(Emp.class, 1);
		System.out.println(e.getEmpno());
		System.out.println("====SQL输出在上面还是下面，下面才是延迟加载====");
		System.out.println(e.getEname());
		session.close();
	}
	
	
	//验证query.iterate()是延迟加载的
	//效率很低
	@Test
	public void test2() {
		String hql = "from Emp";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		//下面这一句其实只执行了一句：查询ID值
		Iterator<Emp> it = query.iterate();
		System.out.println("==========只有迭代后才会进行查询==========");
		//通过上面查询的ID，进而查询每一个对象的详细信息
		while(it.hasNext()) {
			Emp e = it.next();
			System.out.println(e.getEname());
		}
	}
}
