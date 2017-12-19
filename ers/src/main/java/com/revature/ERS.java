package com.revature;

import java.util.List;
import java.util.Scanner;

import com.revature.beans.Reimbursement;
import com.revature.dao.ERSDAO;

public class ERS {
	
	static ERSDAO dao = new ERSDAO();
	static int currentEmployeeID = -1;
	static boolean isManager = false;
	static String managerPassword = "NCC-1701";

	public static void main(String[] args) {
		boolean notYetExited = true;
		System.out.println("Enter a number to perform a task.");
		while (notYetExited)
			notYetExited = mainMenu();
	}
	
	public static boolean mainMenu() {
		if (currentEmployeeID == -1 && isManager == false) {
			System.out.println("0 -- exit");
			System.out.println("1 -- display employee ID's of employees with a name of your choice");
			System.out.println("2 -- log in to a particular employee's account");
			System.out.println("3 -- log in as the manager");
			
			switch(scanInt()) {
			case 0:
				System.out.println("Goodbye!");
				return false;
			case 1:
				displayID();
				break;
			case 2:
				employeeLogIn();
				break;
			case 3:
				managerLogIn();
				break;
			}

		}
		
		if (currentEmployeeID != -1) {
			System.out.println("0 -- exit");
			System.out.println("1 -- log out");
			System.out.println("2 -- request a reimbursement");
			System.out.println("3 -- view all of the reimbursements you've requested");
			System.out.println("4 -- withdraw a reimbursement you've requested");
			
			switch(scanInt()) {
			case 0:
				System.out.println("Goodbye!");
				return false;
			case 1:
				logOut();
				break;
			case 2:
				submitReimbursement();
				break;
			case 3:
				employeeViewAll();
				break;
			case 4:
				employeeDelete();
				break;
			/*case 1337:
				retrieveAmountFromID();
				break;*/
			}
		}
		
		if (isManager == true) {
			System.out.println("0 -- exit");
			System.out.println("1 -- log out");
			System.out.println("2 -- view all pending reimbursements");
			System.out.println("3 -- view all approved reimbursements");
			System.out.println("4 -- view all denied reimbursements");
			System.out.println("5 -- approve a reimbursement");
			System.out.println("6 -- deny a reimbursement");
			System.out.println("7 -- delete a reimbursement");
			System.out.println("8 -- display name of employee with an ID of your choice");
			
			switch(scanInt()) {
			case 0:
				System.out.println("Goodbye!");
				return false;
			case 1:
				logOut();
				break;
			case 2:
				viewAllPending();
				break;
			case 3:
				viewAllApproved();
				break;
			case 4:
				viewAllDenied();
				break;
			case 5:
				approve();
				break;
			case 6:
				deny();
				break;
			case 7:
				managerDelete();
				break;
			case 8:
				displayName();
				break;
			}
		}
		
		return true;
	}
	
	public static int scanInt() {
		return new Scanner(System.in).nextInt();
	}
	
	public static String scanString() {
		return new Scanner(System.in).nextLine();
	}
	
	public static void displayID() {
		String firstName = "";
		String lastName = "";
		System.out.println("Enter employee's first name:");
		firstName = scanString();
		System.out.println("Enter employee's last name:");
		lastName = scanString();
		System.out.println("The following employee ID's are associated with the name " + firstName + " " + lastName + ":");
		System.out.println(dao.getIDFromName(firstName, lastName));
	}
	
	public static void displayName() {
		System.out.println("Enter employee's ID:");
		int nameThisID = scanInt();
		String nameResult = dao.getNameFromID(nameThisID);
		if (!nameResult.equals("There is no employee with that ID"))
			System.out.println("The employee's name is: " + nameResult);
		else
			System.out.println(nameResult);
	}
	
	public static void employeeLogIn() {
		boolean success;
		System.out.println("Enter your employee ID:");
		int loginID = scanInt();
		System.out.println("Enter your password:");
		String password = scanString();
		success = dao.employeeLogIn(loginID, password);
		if (success) {
			System.out.println("Welcome!");
			currentEmployeeID = loginID;
			isManager = false;
		}
		else
			System.out.println("Incorrect ID or password.");
	}
	
	public static void logOut() {
		if (currentEmployeeID != -1)
		{
			currentEmployeeID = -1;
			isManager = false;
			System.out.println("Successfully logged out.");
		}
		else if (isManager) {
			isManager = false;
			System.out.println("Successfully logged out.");
		}
		else
			System.out.println("You are already logged out.");
	}
	
	public static void submitReimbursement() {
		System.out.println("How large of a reimbursement do you want, in dollars? (No decimals.)");
		int dollars = scanInt();
		System.out.println("Enter a description of what you're being reimbursed for:");
		String reason = scanString();
		dao.submitReimbursement(dollars, reason, currentEmployeeID);
	}
	
	public static void retrieveAmountFromID() {
		int rid = scanInt();
		System.out.println(dao.retrieveAmountFromID(rid));
	}
	
	public static void employeeViewAll() {
		System.out.println("Format: (Reimbursement ID) (Employee ID) Status Amount-Requested \"Description\"");
		List<Reimbursement> allRequestedReimbursements = dao.employeeViewAll(currentEmployeeID);
		for (Reimbursement r : allRequestedReimbursements)
			System.out.println(r);
	}
	
	public static void managerLogIn() {
		System.out.println("Enter your password:");
		if (scanString().equals(managerPassword)) {
			currentEmployeeID = -1;
			isManager = true;
			System.out.println("Hello, Manager.");
		}
		else
			System.out.println("Incorrect password.");
	}
	
	public static void viewAllPending() {
		System.out.println("Format: (Reimbursement ID) (Employee ID) Status Amount-Requested \"Description\"");
		List<Reimbursement> allPendingReimbursements = dao.viewAllOfOneStatus(2);
		for (Reimbursement r : allPendingReimbursements)
			System.out.println(r);
	}
	
	public static void viewAllApproved() {
		System.out.println("Format: (Reimbursement ID) (Employee ID) Status Amount-Requested \"Description\"");
		List<Reimbursement> allApprovedReimbursements = dao.viewAllOfOneStatus(1);
		for (Reimbursement r : allApprovedReimbursements)
			System.out.println(r);
	}
	
	public static void viewAllDenied() {
		System.out.println("Format: (Reimbursement ID) (Employee ID) Status Amount-Requested \"Description\"");
		List<Reimbursement> allDeniedReimbursements = dao.viewAllOfOneStatus(0);
		for (Reimbursement r : allDeniedReimbursements)
			System.out.println(r);
	}
	
	public static void approve() {
		System.out.println("Enter the desired reimbursement ID:");
		int approveID = scanInt();
		dao.approve(approveID);
		System.out.println("Reimbursement approved.");
	}
	
	public static void deny() {
		System.out.println("Enter the desired reimbursement ID:");
		int denyID = scanInt();
		dao.deny(denyID);
		System.out.println("Reimbursement denied.");
	}
	
	public static void managerDelete() {
		System.out.println("Enter the ID of the reimbursement you wish to delete:");
		int deleteID = scanInt();
		dao.managerDelete(deleteID);
		System.out.println("Reimbursement deleted.");
	}
	
	public static void employeeDelete() {
		System.out.println("Enter the ID of the reimbursement you wish to delete:");
		int deleteID = scanInt();
		dao.employeeDelete(deleteID, currentEmployeeID);
		System.out.println("If you were the one who requested this reimbursement, it has been deleted.");
	}

}
