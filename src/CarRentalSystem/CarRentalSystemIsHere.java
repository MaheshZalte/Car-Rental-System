package CarRentalSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
	private String CarId;
	private String brand;
	private String model;
	private double basePriceperDay;
	private boolean isAvailable;

	public Car(String CarId, String brand, String model, double basePriceperDay) {
		this.CarId = CarId;
		this.basePriceperDay = basePriceperDay;
		this.brand = brand;
		this.model = model;
		this.isAvailable = true;
	}

	public String getCarid() {
		return CarId;
	}

	public String getBrand() {
		return brand;
	}

	public String getmodel() {
		return model;
	}

	public double calculatePrice(int rantelDays) {
		return basePriceperDay * rantelDays;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void rent() {
		isAvailable = false;
	}

	public void returnCar() {
		isAvailable = true;
	}
}

class Customer {
	private String customerId;
	private String Name;

	public Customer(String customerId, String Name) {
		this.customerId = customerId;
		this.Name = Name;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getName() {
		return Name;
	}
}

class Rental {
	private Car car;
	private Customer customer;
	private int Days;

	public Rental(Car car, Customer customer, int Days) {
		this.car = car;
		this.customer = customer;
		this.Days = Days;
	}

	public Car getCar() {
		return car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getDays() {
		return Days;
	}
}

class CarRentalSystem {
	private List<Car> Cars;
	private List<Customer> Customers;
	private List<Rental> Rentals;

	public CarRentalSystem() {
		Cars = new ArrayList<>();
		Customers = new ArrayList<>();
		Rentals = new ArrayList<>();
	}

	public void AddCar(Car car) {
		Cars.add(car);
	}

	public void AddCustomer(Customer customer) {
		Customers.add(customer);
	}

	public void rentCar(Car car, Customer customer, int Days) {
		if (car.isAvailable()) {
			car.rent();
			Rentals.add(new Rental(car, customer, Days));
			
		} else {
			System.out.println("Car is not Available for rent.");
		}
	}

	public void returnCar(Car car) {
		car.returnCar();
		Rental rentalToRemove = null;
		for (Rental rental : Rentals) {
			if (rental.getCar() == car) {
				rentalToRemove = rental;
				break;
			}
		}
		if (rentalToRemove != null) {
			Rentals.remove(rentalToRemove);
		} else {
			System.out.println("Car was not rental.");
		}
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("======= Car Rental System =======");
			System.out.println("1. Rent a Car");
			System.out.println("2. Return a Car");
			System.out.println("3. Exit");
			System.out.println("Enter Your Choice:");
			
			int Choice = sc.nextInt();
			sc.nextLine();

			if (Choice == 1) {
				System.out.println("\n === Rent a Car === \n");
				System.out.print("Enter Your Name :");
				String CustomerName = sc.nextLine();
				
				System.out.println("\n Available Cars :\n");
				for (Car car : Cars) {
					if (car.isAvailable()) {
						System.out.println(car.getCarid() + " - " + car.getBrand() + "   " + car.getmodel());
					}
				}

				System.out.println("\nEnter the Car ID which you want to rent :");
				String CarId = sc.nextLine();
				
				System.out.println("Enter the number of Days for Rental :");
				int rentalDays = sc.nextInt();
				sc.nextLine();
				Customer newCustomer = new Customer("CUS" + (Customers.size() + 1), CustomerName);
				AddCustomer(newCustomer);
				
				Car SelectedCar = null;
				for (Car car1 : Cars) {
					if (car1.getCarid().equals(CarId) && car1.isAvailable()) {
						SelectedCar = car1;
						break;
					}
				}

				if (SelectedCar != null) {
					double totalPrice = SelectedCar.calculatePrice(rentalDays);
					System.out.println("\n== Rental Information ==\n");
					System.out.println("Customer ID: " + newCustomer.getCustomerId());
					System.out.println("Customer Name: " + newCustomer.getName());
					System.out.println("Car: " + SelectedCar.getBrand() + " " + SelectedCar.getmodel());
					System.out.println("Rental Days: " + rentalDays);
					System.out.printf("Total Price: $%.2f%n", totalPrice);

					System.out.print("\nConfirm rental (Y/N): ");
					String confirm = sc.nextLine();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(SelectedCar, newCustomer, rentalDays);
						System.out.println("\nCar rented successfully.");
					} else {
						System.out.println("\nRental canceled.");
					}
				} else {
						System.out.println("\nInvalid car selection or car not available for rent.");
					}
			} else if (Choice == 2) {
				System.out.println("\n== Return a Car ==\n");
				System.out.println("Enter the Car ID You want to return :");
				String Carid = sc.nextLine();
				
				Car carToReturn = null;
				for (Car car : Cars) {
					if (car.getCarid().equals(Carid) && !car.isAvailable()) {
						carToReturn = car;
						break;
					}
				}
				
				if (carToReturn != null) {
					Customer customer = null;
					for (Rental rental : Rentals) {
						if (rental.getCar() == carToReturn) {
							customer = rental.getCustomer();
							break;
						}
					}
					if (customer != null) {
						returnCar(carToReturn);
						System.out.println("Car Return Successfully by " + customer.getName());
					} else {
						System.out.println("Invalid Car ID or Car is not rented.");
					}
					
			} else if (Choice == 3) {
				break;
				} else {
					System.out.println("You Entered Invalid Choice plz enter Valid Option.");
				}
			}
			
			System.out.println("Thank You For Visiting Car rental Site.");

		}
	}
}
	

class CarRentalSystemIsHere {
	public static void main(String[] args) {
		CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60.0); // Different base price per day for each car
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        rentalSystem.AddCar(car1);
        rentalSystem.AddCar(car2);
        rentalSystem.AddCar(car3);

        rentalSystem.menu();
	}
}
