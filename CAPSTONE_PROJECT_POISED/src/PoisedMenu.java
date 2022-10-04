
import java.util.*;
import java.text.*;

public class PoisedMenu {
	// Projects array
	public static ArrayList<PoisedProject> projects = PoisedFile.getProjects();
	// Person Variables
	private static String name;
	private static String phoneNum;
	private static String email;
	private static String address;
	// Methods
	
	/** Outputs a Project Options Menu */
	public static void printMenu() {
		System.out.println("1 - Print Project\n"
				+ "2 - Change projects due date\n"
				+ "3 - Change total amount paid to date for project\n"
				+ "4 - Update projects contractors details\n"
				+ "5 - Finalise project\n"
				+ "0 - Exit\n");
	}
	
	/** Outputs Main Options Menu */
	public static void printMainMenu() {
		System.out.println("1 - Pick a Project\n"
				+ "2 - Create new Project\n"
				+ "3 - Print all Projects\n"
				+ "4 - Print all Unfinished Projects\n"
				+ "5 - Print all Overdue Projects\n"
				+ "0 - Exit Program");
	}
	
	/** Gets the users Menu Selection
	 *
	 * @return Number Option as an Integer that user Selected
	 */
	public static int selectedOption() {
		Scanner input = new Scanner(System.in);
	
		String choiceStr = input.nextLine();
		int choice = 42;
		try {
		// Convert choice to integer
		choice = Integer.parseInt(choiceStr);
			if(choice < 0) {
				throw new Exception();
			}
			return choice;
			} catch(Exception e) {
				System.out.println("That's not a valid option. Please enter a number from 1 to 6\n");
				return selectedOption();
			}
	}
	
	/** Displays all Projects by their Names only */
	public static void printAllProjectsNames() {
		for(int i = 0; i < projects.size();i++) {
			System.out.println((i+1) + " " + projects.get(i).projName);
		}
	}
	
	/** Displays all Projects */
	public static void printAllProjects() {
		for(int i = 0;i < projects.size();i++) {
			System.out.println("Project " + (i+1));
			projects.get(i).printProject();
		}
	}
	
	/** Displays selected Project
	 * 
	 * @param i Projects Number to be chosen
	 */
	public static void printSelectedProject(int i) {
		projects.get(i).printProject();
	}
	
	/** Displays Projects that are not marked as Finalised */
	public static void printUnfinishedProjects() {
		for(int i = 0;i < projects.size();i++) {
			if(projects.get(i).finalised.equals("No")) {
				projects.get(i).printProject();
			}
		}
	}
	
	/** Displays Overdue Projects */
	public static void printOverdueProjects() {
		for(int i = 0; i < projects.size();i++) {
			try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projects.get(i).deadline);
			Date now = new Date();
			if(projects.get(i).finalised.equals("No") && now.compareTo(date) > 0) {
				projects.get(i).printProject();
			}
			} catch(ParseException e) {
				System.out.println("\nProject " + (i +1) + " Date invalid format! Please change it");
			}
		}
	}
	
	/** Gets info and uses that to Create a new Project Object
	 *
	 * @return New Project Object
	 */
	public static PoisedProject createProject() {
		// Variables
		String projName = "";
		String projNum = "";
		String buildingType = "";
		String buildingAddress = "";
		String erfNum = "";
		String deadline = "";
		double totalFee = 0;
		double totalPaidToDate = 0;
		String finalised = "No";
		
		// Getting Project Details, checking not empty then assigning them
		Scanner input = new Scanner(System.in);
		
		do {
			System.out.print("Project Name: ");
			projName = input.nextLine();
			if(projName.equals("")) {
				System.out.println("Please enter Something!");
			}
		} while (projName.equals(""));
		
		do {
			System.out.print("Project Number: ");
			projNum = input.nextLine();
			if(projNum.equals("")) {
				System.out.println("Please enter Something!");
			}
		} while (projNum.equals(""));
		
		do {
			System.out.print("Projects Building type: ");
			buildingType = input.nextLine();
			if(buildingType.equals("")) {
				System.out.println("Please enter Something!");
			}
		} while (buildingType.equals(""));
		
		do {
			System.out.print("Projects Address: ");
			buildingAddress = input.nextLine();
			if(buildingAddress.equals("")) {
				System.out.println("Please enter Something!");
			}
		} while (buildingAddress.equals(""));
		
		do {
			System.out.print("Projects ERF Number: ");
			erfNum = input.nextLine();
			if(erfNum.equals("")) {
				System.out.println("Please enter Something!");
			}
		} while (erfNum.equals(""));
		
		while(true) {
			try {
				System.out.print("Project Deadline: ");
				String temp = input.nextLine();
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(temp);
				deadline = new SimpleDateFormat("yyyy-MM-dd").format(date);
				break;
			}
			catch(Exception e) {
				System.out.println("Please enter in valid date format! eg (2020-05-27)");
			}
		}
		
		while(true) {
			try {
				System.out.print("Projects Total Fee: ");
				String temp = input.nextLine();
				totalFee = Double.valueOf(temp);
				break;
			} catch(Exception e) {
				System.out.println("Please enter a valid number");
			}
		}
		
		while(true) {
			try {
				System.out.print("Project Total Paid to Date: ");
				String temp = input.nextLine();
				totalPaidToDate = Double.valueOf(temp);
				break;
			} catch(Exception e) {
				System.out.println("Please enter a valid number");
			}
		}
		
		// Creating Person Objects
		PoisedPerson architect = createPerson("Architect");
		PoisedPerson customer = createPerson("Customer");
		PoisedPerson contractor = createPerson("Contractor");
		
		if(projName.equalsIgnoreCase("")) {
			projName = customer.name;
		}
		
		// Creating Project Object
		PoisedProject project = new PoisedProject(projName,
									projNum,
									buildingType,
									buildingAddress,
									erfNum,
									deadline,
									totalFee,
									totalPaidToDate,
									architect,
									customer,
									contractor,
									finalised);
		//
		System.out.println("New Project Successfully Created");
		return project;
}
	
	/** Creates a Person Object
	 * @param role Persons role on the project
	 * @return Person Object
	 */
	public static PoisedPerson createPerson(String role) {
		Scanner input = new Scanner(System.in);
		
		// Getting info, checking it and using to Create a Person Object
		System.out.println("\nProjects " + role + "s" + " Details:");
		
		do {
			System.out.print("Name: ");
			name = input.nextLine();
			if(name.equals("")){
				System.out.println("Please Enter Something!");
			}
		} while (name.equals(""));
		
		do {
			System.out.print("Phone Number: ");
			phoneNum = input.nextLine();
			if(phoneNum.equals("")) {
				System.out.println("Please Enter Something!");
			}
		} while (phoneNum.equals(""));
		
		do {
			System.out.print("Email Address: ");
			email = input.nextLine();
			if(email.equals("")) {
				System.out.println("Please Enter Something!");
			}
		} while (email.equals(""));
		
		do {
			System.out.print("Physical Address: ");
			address = input.nextLine();
			if(address.equals("")) {
				System.out.println("Please Enter Something!");
			}
		} while (address.equals(""));
		
		// Creating Architect Object
		PoisedPerson person = new PoisedPerson(role, name, phoneNum, email,
							address);
		
		return person;
	}
}