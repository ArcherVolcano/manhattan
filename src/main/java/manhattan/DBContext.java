package manhattan;

import manhattan.service.AdminService;
import manhattan.service.EventService;
import manhattan.service.impl.AdminServiceImpl;
import manhattan.service.impl.EventServiceImpl;

public class DBContext {
	
	private static DBContext instance;
	private final AdminService adminService;
	private final EventService eventService;
	
	private DBContext() {
		this.adminService = new AdminServiceImpl();
		this.eventService = new EventServiceImpl();
	}
	
	public static DBContext getInstance() {
		if (instance == null) {
			synchronized (DBContext.class) {
				if (instance == null) {
					instance = new DBContext();
				}
			}
		}
		return instance;
	}
	
	public AdminService adminService() {
		return adminService;
	}
	
	public EventService eventService() {
		return eventService;
	}
	
}
