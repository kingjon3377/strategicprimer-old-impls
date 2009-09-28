package model.main;

/**
 * A class to keep track of UUIDs. At present we don't use java.util.UUID
 * because it's a lot of overhead for our simple use, but we may want to at some
 * time in the future.
 * 
 * This class should also have a Map<Long,Object> mapping Modules, etc., from
 * UUIDs to the objects that hold them. FIXME: Implement. And FIXME: There's no
 * way to release a UUID.
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class UuidManager {
	/**
	 * The singleton
	 */
	public static final UuidManager UUID_MANAGER = new UuidManager();
	/**
	 * The biggest UUID we've given out
	 */
	private long maxUuid;

	/**
	 * Singleton
	 */
	private UuidManager() {
		maxUuid = 2;
	}

	/**
	 * @return a new UUID
	 */
	public long getNewUuid() {
		synchronized (this) {
			maxUuid++;
			return maxUuid;
		}
	}
}
