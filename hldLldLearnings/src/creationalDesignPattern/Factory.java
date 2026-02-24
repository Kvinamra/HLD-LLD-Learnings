package creationalDesignPattern;

/*THEORY:
 * Factory design pattern defines an interface for creating objects but lets subclasses decide which object to instantiate.
 * It promotes loose coupling by delegating object creation to a method, making the system more flexible and extensible.
 * 
 * PROs:
 * Encapsulation of Object Creation: Clients don’t know how objects are created.
 * Loose Coupling: Reduces dependency between client and concrete classes.
 * Scalability: New product types can be introduced without altering client code.
 * Reusability: Common creation logic can be reused across factories.
 * Flexibility: Supports multiple product families with minimal changes.
 * Testability: Easy to use mock factories for unit testing
 */
public abstract class Factory {
	public abstract void printVehicle();
}

class TwoWheeler extends Factory {
	public void printVehicle()
	{
		System.out.println("I am two wheeler");
	}
}

class FourWheeler extends Factory {
	public void printVehicle()
	{
		System.out.println("I am four wheeler");
	}
}

/*Below approach is WRONG --> 
 *Tightly coupled -> client depends on product classes, 
 *Violation of SRP -> client handles both product creation and usage,
 *Hard to extend -> Adding new vehicle requires modification in client code as well.
 */
class Client {
	private Factory pVehicle;

	public Client(int type)
	{
		if (type == 1) {
			pVehicle = new TwoWheeler();
		}
		else if (type == 2) {
			pVehicle = new FourWheeler();
		}
		else {
			pVehicle = null;
		}
	}

	public void cleanup()
	{
		if (pVehicle != null) {
			pVehicle = null;
		}
	}

	public Factory getVehicle() { return pVehicle; }
}

//Driver Code
/*public*/ class DriverClass1 {
	public static void main(String[] args)
	{
		Client pClient = new Client(1);
		Factory pVehicle = pClient.getVehicle();
		if (pVehicle != null) {
			pVehicle.printVehicle();
		}
		pClient.cleanup();
	}
}

/* Correct Approach:
 * Define a Factory Interface: Create an interface, VehicleFactory, with a method to produce vehicles.
 * Create Specific Factories: Implement classes like TwoWheelerFactory and FourWheelerFactory that follow the VehicleFactory interface, providing methods for each vehicle type.
 * Client class to use a VehicleFactory instance instead of creating vehicles directly. This way, it can request vehicles without using conditional logic.
 * Enhance Flexibility: This structure allows for easy addition of new vehicle types by simply creating new factory classes, without needing to alter existing Client code.
 */
// USE LINE NUMBER 20 - 37 for rest code.
interface VehicleFactory {
	Factory createVehicle();
}

class TwoWheelerFactory implements VehicleFactory {
	public Factory createVehicle()
	{
		return new TwoWheeler();
	}
}

class FourWheelerFactory implements VehicleFactory {
	public Factory createVehicle()
	{
		return new FourWheeler();
	}
}

//Client class
class Client2 {
	private Factory pVehicle;

	public Client2(VehicleFactory factory)
	{
		pVehicle = factory.createVehicle();
	}

	public Factory getVehicle() { return pVehicle; }
}

//Driver class
/*public*/ class DriverClass2 {
	public static void main(String[] args)
	{
		VehicleFactory twoWheelerFactory = new TwoWheelerFactory();
		Client2 twoWheelerClient = new Client2(twoWheelerFactory);
		Factory twoWheeler = twoWheelerClient.getVehicle();
		twoWheeler.printVehicle();

		VehicleFactory fourWheelerFactory = new FourWheelerFactory();
		Client2 fourWheelerClient = new Client2(fourWheelerFactory);
		Factory fourWheeler = fourWheelerClient.getVehicle();
		fourWheeler.printVehicle();
	}
}
