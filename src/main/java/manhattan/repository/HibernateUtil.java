package manhattan.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 */
public final class HibernateUtil {

	/**
	 * Don't let anyone instantiate this class.
	 */
	private HibernateUtil() {
	}

	private static SessionFactory SESSION_FACTORY;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistry);
	}

	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}

}
