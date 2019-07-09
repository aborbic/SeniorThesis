import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/* To Do:
 * List the organizations by the highest recurring one down to the lowest
 * List the information.
 * Org list is here and delimited by a - to search the string
 * Create method for finding a friend
 * Create method for searching for similar Organizations friends are in
 * Create method for recommended the Highest results to the user
 */

// Up above I declared the classes that i'm going to use for the program.
// Scanner is used to receive input and read files
// I need the file class to import files
// PrintWriter is used to make my own files so the data can be called again

public class OrganizationMachine extends Organizations {

	public OrganizationMachine(String orgName, String orgCode, String orgType, String orgStatus,
			ArrayList<String> orgMembers) {
		super(orgName, orgCode, orgType, orgStatus, orgMembers);
		// TODO Auto-generated constructor stub
	}

	// Exception is thrown for if the files cannot be found
	public static void main(String[] args) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			e.printStackTrace();
			return;
		}

		System.out.println("Initialization of MySQL DB Connection.");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thesis", "root", "");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("Connection Made.");
		} else {
			System.out.println("Failed to make connection!");
		}
		// Making all the organizations into objects.
		int orgAmount = 722;
		// Holds the organization titles
		String[] orgs = new String[orgAmount];
		// Holds the codes
		String[] orgCodes = new String[orgAmount];
		// Holds the types
		String[] orgType = new String[orgAmount];
		// Holds the current members
		String[] orgCurrentMembers = new String[orgAmount];
		// Holds the org Status
		String[] orgStatus = new String[orgAmount];

		BufferedReader objReader = null;
		// Using a try catch statement to grab errors
		try {
			// Reads in the file, creates an array, adds elements to the array
			String strCurrentLine;
			objReader = new BufferedReader(
					new FileReader("C:\\Users\\Christian's PC\\Desktop\\Senior Thesis Project\\ListOfOrgs.txt"));

			for (int i = 0; i < orgs.length; i++) {
				strCurrentLine = objReader.readLine();
				orgs[i] = strCurrentLine;
			}

			// Catch IO exception
		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			// Will try and check if objreader still has strings to be read in
			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			// Reads in the file, creates an array, adds elements to the array
			String strCurrentLine;
			objReader = new BufferedReader(
					new FileReader("C:\\Users\\Christian's PC\\Desktop\\Senior Thesis Project\\ListOfOrgsCodes.txt"));

			for (int i = 0; i < orgCodes.length; i++) {
				strCurrentLine = objReader.readLine();
				orgCodes[i] = strCurrentLine;
			}

			// Catch IO exception
		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			// Will try and check if objreader still has strings to be read in
			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			// Reads in the file, creates an array, adds elements to the array
			String strCurrentLine;
			objReader = new BufferedReader(
					new FileReader("C:\\Users\\Christian's PC\\Desktop\\Senior Thesis Project\\ListOfOrgsType.txt"));

			for (int i = 0; i < orgType.length; i++) {
				strCurrentLine = objReader.readLine();
				orgType[i] = strCurrentLine;
			}

			// Catch IO exception
		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			// Will try and check if objreader still has strings to be read in
			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			// Reads in the file, creates an array, adds elements to the array
			String strCurrentLine;
			objReader = new BufferedReader(
					new FileReader("C:\\Users\\Christian's PC\\Desktop\\Senior Thesis Project\\CurrentMembers.txt"));

			for (int i = 0; i < orgCurrentMembers.length; i++) {
				strCurrentLine = objReader.readLine();
				orgCurrentMembers[i] = strCurrentLine;
			}

			// Catch IO exception
		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			// Will try and check if objreader still has strings to be read in
			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			// Reads in the file, creates an array, adds elements to the array
			String strCurrentLine;
			objReader = new BufferedReader(
					new FileReader("C:\\Users\\Christian's PC\\Desktop\\Senior Thesis Project\\ListOfOrgsStatus.txt"));

			for (int i = 0; i < orgStatus.length; i++) {
				strCurrentLine = objReader.readLine();
				orgStatus[i] = strCurrentLine;
			}

			// Catch IO exception
		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			// Will try and check if objreader still has strings to be read in
			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		// Setting some of the incorrect values it picked up from the text file.
		// First org is Alpha Kappa Delta
		orgs[0] = "Alpha Kappa Delta";
		// Fixing the first code for said organization
		orgCodes[0] = "160673";
		// Giving it the correct amount of current members
		orgCurrentMembers[0] = "12";
		// Giving it the correct status
		orgStatus[0] = "Active";
		// Created an array for the objects and their values to be stored
		List<Organizations> studentOrgs = new ArrayList<>();
		// Initialized an String array of members as input to populate the object for
		// initial construction
		ArrayList<String> members = new ArrayList<String>();
		// Adding at least a blank member slate to not return a null value
		members.add("none");
		// Created a loop to register all of the objects and have them ready to edit
		for (int i = 0; i < orgAmount; i++) {
			studentOrgs.add(new Organizations(orgs[i], orgCodes[i], orgCurrentMembers[i], orgStatus[i], members));
		}
		// Loops the main screen and options
		int optionInput = 0;
		while (optionInput == 0) {
			System.out.println("Welcome to the Organization Engine. What would you like to do?");
			System.out.println("Press the number to indicate the selection and enter to finalize it.");
			System.out.println("1. Look up an organization.");
			System.out.println("2. Enter your name and an organization you belong to.");
			System.out.println("3. Find your recommendations.");
			Scanner numberOp = new Scanner(System.in);
			optionInput = numberOp.nextInt();
			if (optionInput < 0 || optionInput > 3) {
				System.out.println("Please enter a valid option");
				optionInput = 0;
			}
		}
		// Give them the specifications of the organization =
		while (optionInput == 1) {
			orgLookup();
			System.out.println("\nWould you like to lookup another organization?\n" + "'y' for yes and 'n' for no. "
					+ "Proceed choices by pressing return/enter.");
			Scanner scan = new Scanner(System.in);
			String choice = scan.next();
			if (choice.equalsIgnoreCase("y") == true) {
				optionInput = 1;
				orgLookup();
			} else if (choice.equalsIgnoreCase("n") == true) {
				optionInput = 2;
			}
		}
		// Finds your organization and your name
		// If you already belong to the organization it will tell you
		// Names will be populated one by one until it hits 5
		// If a name is in the slot, then the next name slot will be populated
		while (optionInput == 2) {
			memberAssign();
			System.out.println("\nWould you like to enter another organization?\n" + "'y' for yes and 'n' for no. "
					+ "Proceed choices by pressing return/enter.");
			Scanner scan = new Scanner(System.in);
			String choice = scan.next();
			if (choice.equalsIgnoreCase("y") == true) {
				optionInput = 2;
				memberAssign();
			} else if (choice.equalsIgnoreCase("n") == true) {
				optionInput = 3;
			}
		}
		while (optionInput == 3) {
			orgRecommendation();
		}
	}

	public static void orgLookup() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			e.printStackTrace();
			return;
		}
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thesis", "root", "");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		System.out.println("Enter an Organization Name for Lookup");
		Scanner orgInput = new Scanner(System.in);
		String orgName = orgInput.nextLine();
		// Created connection to the database
		Statement st = connection.createStatement();
		PreparedStatement ps = connection
				.prepareStatement("select * from organizationrepository where OrganizationName = ?");
		ps.setString(1, orgName);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String name = rs.getString("OrganizationName");
			String code = rs.getString("OrganizationCode");
			String type = rs.getString("OrganizationType");
			String status = rs.getString("OrganizationStatus");
			int memberNums = rs.getInt("OrganizationMemberNums");

			System.out.format(
					"Organization Name: %s\nOrganization Code: %s\n"
							+ "OrganizationType: %s\nOrganizationStatus: %s\nAmount of People: %s\n",
					name, code, type, status, memberNums);
		} else {
			System.out.println("Could not find entry.");
		}
	}

	public static void memberAssign() throws Exception {
		// Threw an exception for invalid sql statements
		// Established a connection to the database again
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			e.printStackTrace();
			return;
		}
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thesis", "root", "");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		}
		System.out.println("Enter your name and Organization.\n"
				+ "If this has already been input it will tell you and quit the program");
		System.out.println("Enter your name");
		System.out.println("Enter your organization name");
		Scanner nameInput = new Scanner(System.in);
		Scanner orgInput = new Scanner(System.in);
		String name = nameInput.nextLine();
		String org = orgInput.nextLine();
		Statement st = connection.createStatement();
		String[] userNames = new String[5];
		PreparedStatement ps = connection
				.prepareStatement("select * from organizationrepository where OrganizationName = ?");
		ps.setString(1, org);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String mem1 = rs.getString("Member1");
			String mem2 = rs.getString("Member2");
			String mem3 = rs.getString("Member3");
			String mem4 = rs.getString("Member4");
			String mem5 = rs.getString("Member5");
			if (mem1.isEmpty()) {
				mem1 = "none";
			}
			if (mem2.isEmpty()) {
				mem2 = "none";
			}
			if (mem3.isEmpty()) {
				mem3 = "none";
			}
			if (mem4.isEmpty()) {
				mem4 = "none";
			}
			if (mem5.isEmpty()) {
				mem5 = "none";
			}
			userNames[0] = mem1;
			userNames[1] = mem2;
			userNames[2] = mem3;
			userNames[3] = mem4;
			userNames[4] = mem5;
			int changesMadeCounter = 0;
			for (int i = 0; i < userNames.length; i++) {
				if (userNames[i].equals(name)) {
					System.out.println(name + " is already a registered member of " + org);
					break;
				}
			}
			for (int i = 0; i < userNames.length; i++) {
				if (userNames[i].equals("none") & i + 1 == 1) {
					PreparedStatement insertps = connection.prepareStatement(
							"update organizationrepository set Member1 = ? where OrganizationName = ?");
					insertps.setString(1, name);
					insertps.setString(2, org);
					insertps.executeUpdate();
					System.out.println("Member 1: " + name + " was regiserted into " + org);
					changesMadeCounter++;
					if (changesMadeCounter == 1) {
						break;
					}
				} else if (userNames[i].equals("none") & i + 1 == 2 & !userNames[2 - 1].equals(name)
						& !userNames[2 - 2].equals(name)) {
					PreparedStatement insertps = connection.prepareStatement(
							"update organizationrepository set Member2 = ? where OrganizationName = ?");
					insertps.setString(1, name);
					insertps.setString(2, org);
					insertps.executeUpdate();
					System.out.println("Member 2: " + name + " was regiserted into " + org);
					changesMadeCounter++;
					if (changesMadeCounter == 1) {
						break;
					}
				} else if (userNames[i].equals("none") & i + 1 == 3 & !userNames[3 - 1].equals(name)
						& !userNames[3 - 2].equals(name) & !userNames[3 - 3].equals(name)) {
					PreparedStatement insertps = connection.prepareStatement(
							"update organizationrepository set Member3 = ? where OrganizationName = ?");
					insertps.setString(1, name);
					insertps.setString(2, org);
					insertps.executeUpdate();
					System.out.println("Member 3: " + name + " was regiserted into " + org);
					changesMadeCounter++;
					if (changesMadeCounter == 1) {
						break;
					}
				} else if (userNames[i].equals("none") & i + 1 == 4 & !userNames[4 - 1].equals(name)
						& !userNames[4 - 2].equals(name) & !userNames[4 - 3].equals(name)
						& !userNames[4 - 4].equals(name)) {
					PreparedStatement insertps = connection.prepareStatement(
							"update organizationrepository set Member4 = ? where OrganizationName = ?");
					insertps.setString(1, name);
					insertps.setString(2, org);
					insertps.executeUpdate();
					System.out.println("Member 4: " + name + " was regiserted into " + org);
					changesMadeCounter++;
					if (changesMadeCounter == 1) {
						break;
					}
				} else if (userNames[i].equals("none") & i + 1 == 5 & !userNames[5 - 1].equals(name)
						& !userNames[5 - 2].equals(name) & !userNames[5 - 3].equals(name)
						& !userNames[5 - 4].equals(name) & !userNames[5 - 5].equals(name)) {
					PreparedStatement insertps = connection.prepareStatement(
							"update organizationrepository set Member5 = ? where OrganizationName = ?");
					insertps.setString(1, name);
					insertps.setString(2, org);
					insertps.executeUpdate();
					System.out.println("Member 5: " + name + " was regiserted into " + org);
					changesMadeCounter++;
					if (changesMadeCounter == 1) {
						break;
					}
				}
			}
		} else {
			System.out.println("Could not find. Make sure the name is correctly spelled.");
		}
		/*
		 * if (!rs.next()) { PreparedStatement insertps = connection
		 * .prepareStatement("update organizationrepository set Member1 = ? where OrganizationName = ?"
		 * ); insertps.setString(1, name); insertps.setString(2, org);
		 * insertps.executeUpdate(); }
		 */
	}

	public static void orgRecommendation() throws Exception {
		// Threw an sql to handle invalid sql statements
		// Established connection to database again
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			e.printStackTrace();
			return;
		}
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thesis", "root", "");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		}
		System.out.println(
				"The system will make you top choices for student organizations.\nEnter your name for the recommendations");
		Scanner nameInput = new Scanner(System.in);
		String name = nameInput.nextLine();
		Statement search1 = connection.createStatement();
		// Find all instances of them name
		// Concatenate them into one string
		// Printed out to see it works
		PreparedStatement ps = connection
				.prepareStatement("select * from organizationrepository where Member1 = ? or Member2 = ? or Member3 = ?"
						+ "or Member4 = ? or Member5 = ?");
		ps.setString(1, name);
		ps.setString(2, name);
		ps.setString(3, name);
		ps.setString(4, name);
		ps.setString(5, name);
		ResultSet rs = ps.executeQuery();
		String orgNames = "";
		String allOrgNames = "";
		while (rs.next()) {
			orgNames = rs.getString("OrganizationName");
			allOrgNames = allOrgNames + orgNames + "-";
		}
		String[] orgList = allOrgNames.split("-");
		// System.out.println("Here are the organizations that you are in");
		for (int i = 0; i < orgList.length; i++) {
			// System.out.println(orgList[i]);
		}
		// Organizations are then put through a loop to find all their available
		// members.
		// Created a string to hold all the names and then delimit them again with a
		// "-".
		String currentOrgMembers = "";
		String allMembers = "";
		for (int i = 0; i < orgList.length; i++) {
			PreparedStatement ps1 = connection
					.prepareStatement("select * from organizationrepository where OrganizationName = ?");
			ps1.setString(1, orgList[i]);
			ResultSet findMembers = ps1.executeQuery();
			while (findMembers.next()) {
				currentOrgMembers = findMembers.getString("Member1") + "-";
				currentOrgMembers = currentOrgMembers + findMembers.getString("Member2") + "-";
				currentOrgMembers = currentOrgMembers + findMembers.getString("Member3") + "-";
				currentOrgMembers = currentOrgMembers + findMembers.getString("Member4") + "-";
				currentOrgMembers = currentOrgMembers + findMembers.getString("Member5") + "-";
				allMembers = allMembers + currentOrgMembers;
				// System.out.println(allMembers);
			}
		}
		// Print the findings of the 2nd search/people search
		// System.out.println("Here are all the friends we found");
		String[] allFoundMembers = allMembers.split("-");
		for (int i = 0; i < allFoundMembers.length; i++) {
			// System.out.println(allFoundMembers[i]);
		}
		// Loop through yet again to find all the organizations that the people are
		// associated with
		// Will want to create a new array in which we count the org occurences
		// Again delimiting with a "-" to split the names later
		String currentOrgsFound = "";
		String allOrgsFound = "";
		for (int i = 0; i < allFoundMembers.length; i++) {
			PreparedStatement ps2 = connection
					.prepareStatement("select * from organizationrepository where Member1 = ? "
							+ "or Member2 = ? or Member3 = ? or Member4 = ? or Member5 = ?");
			ps2.setString(1, allFoundMembers[i]);
			ps2.setString(2, allFoundMembers[i]);
			ps2.setString(3, allFoundMembers[i]);
			ps2.setString(4, allFoundMembers[i]);
			ps2.setString(5, allFoundMembers[i]);
			ResultSet findOrgs = ps2.executeQuery();
			while (findOrgs.next()) {
				currentOrgsFound = findOrgs.getString("OrganizationName") + "-";
				allOrgsFound = allOrgsFound + currentOrgsFound;
			}
		}
		// print out the list of orgs that were found including duplicates.
		// duplicates will be handles later by making a list and removing duplicate
		// values.
		// System.out.println("Here's all the organizations your friends are in.");
		String[] allOrgsResult = allOrgsFound.split("-");
		for (int i = 0; i < allOrgsResult.length; i++) {
			// System.out.println(allOrgsResult[i]);
		}
		// duplicates will be handles later by making a list and removing duplicate
		// values.
		int end = allOrgsResult.length;
		ArrayList<String> orgArrayList = new ArrayList<String>();
		for (int i = 0; i < allOrgsResult.length; i++) {
			orgArrayList.add(allOrgsResult[i]);
		}
		// All unique orgs are put into a new array
		// Another array is created from the amount of orgs to store their
		// votes/matches/highest ratings
		List<String> uniqueOrgs = orgArrayList.stream().distinct().collect(Collectors.toList());
		// System.out.println(uniqueOrgs);
		// System.out.println("Printing all unique orgs found to make sure we do not have duplicates.");
		String[] uniqueOrgArray = new String[uniqueOrgs.size()];
		for (int i = 0; i < uniqueOrgArray.length; i++) {
			uniqueOrgArray[i] = uniqueOrgs.get(i);
			// System.out.println(uniqueOrgArray[i]);
		}
		int[] orgCount = new int[uniqueOrgs.size()];
		// Since we have stored already the org amounts we can recall the allOrgsResult
		// to loop through
		// Outer loop will count the votes for the org in that index
		// Inner loop will loop through orgs that match the name
		// Inner loop will also increment same index to store the votes it gets
		for (int i = 0; i < uniqueOrgs.size(); i++) {
			for (int j = 0; j < allOrgsResult.length; j++) {
				if (allOrgsResult[j].equals(uniqueOrgs.get(i))) {
					orgCount[i]++;
				}
			}
		}
		for (int i = 0; i < orgCount.length; i++) {
			// System.out.println(orgCount[i]);
		}
		// To finish, we have a simple binary sorting algorithm to place the organizations with their votes
		// Whenever the votes switch place, so ill the organization. 
		for (int i = 0; i < orgCount.length - 1; i++) {
			if (orgCount[i] < orgCount[i+1]) {
				int temp = 0;
				String orgTemp = "";
				temp = orgCount[i];
				orgCount[i] = orgCount[i+1];
				orgCount[i+1] = temp;
				orgTemp = uniqueOrgArray[i];
				uniqueOrgArray[i] = uniqueOrgArray[i+1];
				uniqueOrgArray[i+1] = orgTemp;
			}
		}
		// Next and finally we print the results and tell them what orgs fit the best.
		System.out.println("Here are the organizations that would best fit you.");
		for (int i = 0; i < orgCount.length; i++) {
			System.out.println("In spot number " + (i+1) + ": " + uniqueOrgArray[i] + " with " + orgCount[i] + " votes.");
		}
	}
}
