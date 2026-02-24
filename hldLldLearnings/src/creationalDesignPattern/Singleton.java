package creationalDesignPattern;

/*THEORY:
 * Singleton ensures a class has only one instance while providing
 * a global access point to this instance.
 * USE CASE: 
 * 1. Managing database connections
 * 2. Configuration settings
 * 3. Logging
 *
 * Why private variable and constructor?
 * - ensures there must be only one instance, and access should be controlled.
 * - Variable -> If it is "public", other classes can access class variable like: Singleton.instance = new Singleton();
 * - private constructor -> Ensures no other implementing class creates another instance.
 * 
 * Why static?
 * We want to access the "instance" of singleton class and we must be able to call getInstance() without creating an object first.
 * Constructor is private, so we cannot do this: Singleton object = new Singleton();
 * Also, static members exist once per class, loaded when class is loaded and accessible without object: Singleton object = Singleton.getInstance;
 * Hence we marked method as "static"
 * Marking method as "static" leads to mark variable as "static" too.
 */

// Case 1: Lazy singleton
// CONS: Not thread safe
public class Singleton {
	private static Singleton instance;
	
	private Singleton() {}
	
	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
}

// Case 2:Thread safe singleton:
// CONS: Every thread acquires lock
/*public*/ class ThreadSafeSingleton {
	private static ThreadSafeSingleton instance;
	
	private ThreadSafeSingleton() {}
	
	public static synchronized ThreadSafeSingleton getInstance() {
		if (instance == null) {
			instance = new ThreadSafeSingleton();
		}
		return instance;
	}
}

// Case 3: Double-checked locking
// CONS: complex to implement
/*public*/ class DoubleCheckedSingleton {
	// volatile prevents reordering of instructions in: instance = new Singleton()
	private static volatile DoubleCheckedSingleton instance;
	
	private DoubleCheckedSingleton() {}
	
	public static DoubleCheckedSingleton getInstance() {
		// check without locking
		if (instance == null) {
			// lock only when instance might need to be created
			synchronized (DoubleCheckedSingleton.class) {
				// second check prevents double creation
				if (instance == null) {
					instance = new DoubleCheckedSingleton();
				}
			}
		}
		return instance;
	}
}

// Case 4: Eager Initialization
// CONS: Waste resources if instance not used
/*public*/ class EagerSingleton {
	// final ensures single instance created at class load time
	private static final EagerSingleton instance = new EagerSingleton();
	
	private EagerSingleton() {}
	
	public static EagerSingleton getInstance() {
		// always return already created single instance
		return instance;
	}
}

// Case 5: Bill Pugh Singleton
/*
 * The JVM's class loading mechanism does the heavy lifting here. InnerSingleton is not loaded when Singleton is loaded. 
 * It is only loaded when getInstance() is called for the first time, which triggers the initialization of INSTANCE.
 * Class initialization in Java is guaranteed to be thread-safe by the JLS (Java Language Specification), 
 * so no explicit synchronization is needed.
 */
/*public*/ class BillPughSingleton {
	private BillPughSingleton() {}
	
	private static class InnerSingleton {
		private static final BillPughSingleton instance = new BillPughSingleton();
	}
	
	public static BillPughSingleton getInstance() {
		return InnerSingleton.instance;
	}
}

// Case 6: Enum Singleton
/*
 * The JVM provides four guarantees that no other approach offers:
 * Thread-safe initialization: Enum constants are initialized exactly once when the enum class is loaded, and class loading is thread-safe.
 * Serialization safety: Serializing and de-serializing an enum returns the same instance.
 * Reflection safety: The JVM prevents creating enum instances via reflection. Constructor.newInstance() throws an IllegalArgumentException.
 * Single instance guarantee: Enforced at the JVM level, not by your code.
 * The only limitation is that enums cannot extend other classes (they implicitly extend java.lang.Enum), so if your Singleton needs a base class, you cannot use this approach.
 */
enum EnumSingleton {
	INSTANCE;
}

// Case 7: Static block initialization
/*
 * The static block is executed when the class is loaded by the JVM.
 * If an exception occurs, it's wrapped in a RuntimeException.
 */
/*public*/ class StaticBlockSingleton {
	private static StaticBlockSingleton instance;
	
	private StaticBlockSingleton() { }
	
	static {
		try {
			instance = new StaticBlockSingleton();
		} catch (Exception exc) {
			throw new RuntimeException("Exception occurred while creating singleton instance.");
		}
	}
	
	public static StaticBlockSingleton getInstance() {
		return instance;
	}
}












