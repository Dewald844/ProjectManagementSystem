
/*
    Poised Project Manager 
    Author : Dewald Haasbroek 
    Printing Invoices For Created projects
    Poised Project Manager 
    Creating , Editing Projects
    Using SQL database to store and update project data as well as Person data
*/

import java.util.Scanner;

public class Poised{
	public static void main(String[] args) {
		
		// Project Variables
		int choice = 0;
		Scanner input = new Scanner(System.in);
		while(true) {
			// Variables
			int projectSelection = 0;
			boolean projectSelected = false;
			// Prints Main Menu
			PoisedMenu.printMainMenu();
			// Handles Menu selection
			choice = PoisedMenu.selectedOption();
			
			// Picking a Project
			if(choice == 1) {
				try {
					System.out.println("Pick a Project from the list using it's Number");
					PoisedMenu.printAllProjectsNames();
					projectSelection = PoisedMenu.selectedOption() - 1;
					if(projectSelection < PoisedMenu.projects.size() && projectSelection >= 0) {
						projectSelected = true;
						PoisedMenu.printMenu();
					} else {
						System.out.print("There is no project at that number\n");
					}
				} catch(Exception e) {
					System.out.println("No projects exist yet");
				}
			}
			// Creating Project
			if(choice == 2) {
				// Creating Project Object
				PoisedProject project = PoisedMenu.createProject();
				PoisedMenu.projects.add(project);
				// Updating Projects File
				PoisedFile.addProjectsToDatabase(project);
			}
			// Displays all Projects
			if(choice == 3) {
				try {
					PoisedMenu.printAllProjects();
				} catch(Exception e) {
					System.out.println("No projects exist yet");
				}
			}
			// Displays Projects that are Unfinished
			if(choice == 4) {
				try {
					PoisedMenu.printUnfinishedProjects();
				} catch(Exception e) {
					System.out.println("No projects exist yet");
				}
			}
			// Displays Projects which are overdue
			if(choice == 5) {
				try {
					PoisedMenu.printOverdueProjects();
				} catch(Exception e) {
					System.out.println("No projects exist yet");
				}
			}
			// Exits Program
			if(choice == 0) {
				input.close();
				break;
			}
			while(projectSelected) {
				choice = PoisedMenu.selectedOption();
				
				if(choice == 1) {
					try {
						PoisedMenu.printSelectedProject(projectSelection);
					} catch(Exception e) {
						System.out.println("No Projects exist yet");
					}
				}
				// Changes a Projects Deadline
				if(choice == 2) {
					try {
						PoisedMenu.projects.get(projectSelection).changeDate();
						// Updating Project to Database
						PoisedFile.updateDatabase("Deadline",(projectSelection + 1));;
					} catch(Exception e) {
						System.out.println("No projects exist yet");
					}
				}
				// Change Total Paid to Date
				if(choice == 3) {
					try {
						PoisedMenu.projects.get(projectSelection).changeAmountPaid();
						// Updating Project to Database
						PoisedFile.updateDatabase("AmountPaid", (projectSelection + 1));;
					} catch(Exception e) {
						System.out.println("No projects exist yet");
					}
				}
				// Updates Contractor Details
				if(choice == 4) {
					try {
						PoisedMenu.projects.get(projectSelection).updateContractor();
						// Updating Project to Database
						PoisedFile.updateDatabase("Contractor", (projectSelection + 1));;
					} catch(Exception e) {
						System.out.println("No projects exist yet");
					}
				}
				// Finalize Project
				if(choice == 5) {
					try {
						// Creates Invoice
						String [] invoice = PoisedMenu.projects.get(projectSelection).finaliseProject(
								PoisedMenu.projects.get(projectSelection).totalFee, PoisedMenu.projects.get(projectSelection).totalPaidDate);
						// Updating Finalized Status in Projects List
						if(invoice[3] == "Finalised") {
							PoisedMenu.projects.get(projectSelection).finalised = "Yes";
						}
						// Outputs Invoice
						for(int i = 0;i < 5;i++) {
							System.out.println(invoice[i]);
						}
						// Updating Project to Database
						PoisedFile.updateDatabase("Finalised", (projectSelection + 1));
					} catch(Exception e) {
						e.printStackTrace();
						System.out.println("No projects exist yet");
					}
				}
				if(choice == 0) {
					break;
				}
				PoisedMenu.printMenu();
			}	
		}
	}
}