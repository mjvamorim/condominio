package com.pegasus.condominio.tenant;

public class TenantContext {

	final public static String DEFAULT_TENANT = "db";

	private static String currentTenant = DEFAULT_TENANT;
	public static void setCurrentTenant(String tenant) {
		currentTenant= tenant;
	}

	public static String getCurrentTenant() {
		return currentTenant;
	}

	public static void clear() {
		currentTenant=DEFAULT_TENANT;
	}

//	private static ThreadLocal<String> currentTenant = new ThreadLocal<String>()
//	{
//		@Override
//		protected String initialValue() {
//			return DEFAULT_TENANT;
//		}
//	};
//	public static void setCurrentTenant(String tenant) {
//		currentTenant.set(tenant);
//	}
//
//	public static String getCurrentTenant() {
//		return currentTenant.get();
//	}
//
//	public static void clear() {
//		currentTenant.remove();
//	}

}
