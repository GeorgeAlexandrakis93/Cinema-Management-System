import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Menu {

	public static void main(String[] args) throws SQLException, ParseException {

		// Database connection
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "village cinemas";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to database.");
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		// Variable declarations
		String menu = new String("\n[1] Insert a New Movie, Customer or Booking\n" + "[2] Playing Now\n"
				+ "[3] Full Screenings\n" + "[4] Available Screenings & Booking\n" + "[5] Booking Payment\n"
				+ "[6] Delete a Movie\n" + "[0] Exit\n");
		int option;
		String option2 = null;

		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);

		// Menu Selection
		System.out.println(menu);
		option = sc.nextInt();
		while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5) && (option != 6)
				&& (option != 7) && (option != 0)) {
			System.out.println("No such option, enter again:");
			option = sc.nextInt();
		}

		while (option != 0) {
			// i) Insert a new Movie, Customer or Booking
			if (option == 1) {
				System.out.println(
						"[a] Insert a New Movie\n" + "[b] Insert a New Customer\n" + "[c] Insert a New Booking\n");
				option2 = sc2.nextLine();
				while ((!option2.equals("a")) && (!option2.equals("b")) && (!option2.equals("c"))) {
					System.out.println("No such option, enter again:");
					option2 = sc2.nextLine();
				}

				// i)a) Insert a New Movie
				if (option2.equals("a")) {
					try {
						String[] Movie = new String[6];
						int i = 0, count = 0;
						boolean flag = false;

						System.out.println("\nEnter the Movie's Title:");
						Movie[0] = sc2.nextLine();
						PreparedStatement pst = conn.prepareStatement("SELECT COUNT(title) FROM movie");
						ResultSet res = pst.executeQuery();
						while (res.next()) {
							count = res.getInt("COUNT(title)");
						}

						String[] CheckTitle = new String[count];

						PreparedStatement pst2 = conn.prepareStatement("SELECT title FROM movie");
						ResultSet res2 = pst2.executeQuery();
						while (res2.next()) {
							CheckTitle[i] = res2.getString("title");
							i = i + 1;
						}
						for (i = 0; i < count; i++) {
							if (Movie[0].equalsIgnoreCase(CheckTitle[i])) {
								System.out.println("\nThis Movie already exists.");
								flag = true;
							}
						}
						if (flag == false) {
							System.out.println("Enter the Movie's Description:");
							Movie[1] = sc2.nextLine();
							System.out.println("Enter the Movie's Director:");
							Movie[2] = sc2.nextLine();
							System.out.println("Enter the Movie's Genre:");
							Movie[3] = sc2.nextLine();
							System.out.println("Enter the Movie's Actor:");
							Movie[4] = sc2.nextLine();
							System.out.println("Enter the Movie's Length:");
							Movie[5] = sc2.nextLine();
							PreparedStatement pst3 = conn.prepareStatement("INSERT INTO movie VALUES (?,?,?,?,?,?);");
							pst3.setString(1, Movie[0]);
							pst3.setString(2, Movie[1]);
							pst3.setString(3, Movie[2]);
							pst3.setString(4, Movie[3]);
							pst3.setString(5, Movie[4]);
							pst3.setString(6, Movie[5]);
							pst3.execute();
							System.out.println("\nMovie successfully added.");
						}
					} catch (SQLException s) {
						System.out.println("SQL statement is not executed !");
						System.out.println(s);
					}
					System.out.println(menu);
					option = sc.nextInt();
					while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5)
							&& (option != 6) && (option != 7) && (option != 0)) {
						System.out.println("No such option, enter again:");
						option = sc.nextInt();
					}
				}

				// i)b) Insert a New Customer
				else if (option2.equals("b")) {
					try {
						String[] Customer = new String[4];
						int count = 0, i = 0;
						boolean flag = false;

						System.out.println("Enter the Customer's First Name:");
						Customer[0] = sc2.nextLine();
						System.out.println("Enter the Customer's Last Name:");
						Customer[1] = sc2.nextLine();
						System.out.println(
								"Would you like to enter the Customer's e-mail address ?\nPress 'y' for yes, otherwise any other key:");
						option2 = sc2.nextLine();
						if (option2.equals("y")) {
							System.out.println("Enter the Customer's e-mail address:");
							Customer[2] = sc2.nextLine();
						}
						System.out.println("Enter the Customer's Phone Number:");
						Customer[3] = sc2.nextLine();

						PreparedStatement pst = conn.prepareStatement("SELECT COUNT(first_name) FROM customer");
						ResultSet res = pst.executeQuery();
						while (res.next()) {
							count = res.getInt("COUNT(first_name)");
						}

						String[] CheckFirst_Name = new String[count];
						String[] CheckLast_Name = new String[count];
						String[] CheckPhone_Number = new String[count];

						PreparedStatement pst2 = conn
								.prepareStatement("SELECT first_name, last_name, phone_number FROM customer");
						ResultSet res2 = pst2.executeQuery();
						while (res2.next()) {
							CheckFirst_Name[i] = res2.getString("first_name");
							CheckLast_Name[i] = res2.getString("last_name");
							CheckPhone_Number[i] = res2.getString("phone_number");
							i = i + 1;
						}

						for (i = 0; i < count; i++) {
							if ((Customer[0].equalsIgnoreCase(CheckFirst_Name[i]))
									&& (Customer[1].equalsIgnoreCase(CheckLast_Name[i]))
									&& (Customer[3].equals(CheckPhone_Number[i]))) {
								System.out.println("This Customer already exists.");
								flag = true;
							}
						}
						if (flag == false) {
							PreparedStatement pst3 = conn.prepareStatement("INSERT INTO customer VALUES (?,?,?,?);");
							pst3.setString(1, Customer[0]);
							pst3.setString(2, Customer[1]);
							pst3.setString(3, Customer[2]);
							pst3.setString(4, Customer[3]);
							pst3.execute();
							System.out.println("\nCustomer successfully added.");
						}
					} catch (SQLException s) {
						System.out.println("SQL statement is not executed !");
						System.out.println(s);
					}
					System.out.println(menu);
					option = sc.nextInt();
					while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5)
							&& (option != 6) && (option != 7) && (option != 0)) {
						System.out.println("No such option, enter again:");
						option = sc.nextInt();
					}
				}

				// i)c) Insert a New Booking
				else {
					try {
						int i = 1, count = 0, Seats = 0, Total_Tickets = 0, option3 = 0, j = 1, k = 0, option4 = 0,
								Tickets, Total_Price, Price = 0;
						String Start_Time, option10;
						Date Timestamp;
						boolean flag = false;

						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

						PreparedStatement pst = conn.prepareStatement("SELECT COUNT(location) FROM cinema");
						ResultSet res = pst.executeQuery();
						while (res.next()) {
							count = res.getInt("COUNT(location)");
						}
						String[] Cinema = new String[count + 1];
						PreparedStatement pst2 = conn.prepareStatement("SELECT location FROM cinema");
						ResultSet res2 = pst2.executeQuery();
						System.out.println("\nSelect the Cinema:\n");
						while (res2.next()) {
							Cinema[i] = res2.getString("location");
							System.out.println("[" + i + "] " + Cinema[i]);
							i = i + 1;
						}
						option = sc.nextInt();
						while (flag == false) {
							for (i = 1; i < count + 1; i++) {
								if (option == i) {
									flag = true;
								}
							}
							if (flag == false) {
								System.out.println("No such option, enter again:");
								option = sc.nextInt();
							}
						}
						PreparedStatement pst3 = conn.prepareStatement(
								"SELECT COUNT(DISTINCT(movie_title)) FROM screening WHERE hall_cinema_location = '"
										+ Cinema[option] + "'");
						ResultSet res3 = pst3.executeQuery();
						while (res3.next()) {
							count = res3.getInt("COUNT(DISTINCT(movie_title))");
						}
						String[] Movie = new String[count + 1];
						i = 1;
						PreparedStatement pst4 = conn.prepareStatement(
								"SELECT DISTINCT movie_title  FROM screening WHERE hall_cinema_location = '"
										+ Cinema[option] + "'");
						ResultSet res4 = pst4.executeQuery();
						System.out.println("\nSelect the Movie:\n");
						while (res4.next()) {
							Movie[i] = res4.getString("movie_title");
							System.out.println("[" + i + "] " + Movie[i]);
							i = i + 1;
						}
						option3 = sc.nextInt();
						flag = false;
						while (flag == false) {
							for (i = 1; i < count + 1; i++) {
								if (option3 == i) {
									flag = true;
								}
							}
							if (flag == false) {
								System.out.println("No such option, enter again:");
								option3 = sc.nextInt();
							}
						}
						System.out.println("\nEnter the Screening's Date and Time in -yyyy-MM-dd HH:mm- format:");
						Start_Time = sc2.nextLine();
						try {
							Timestamp = formatter.parse(Start_Time);
							Start_Time = formatter.format(Timestamp);

							PreparedStatement pst5 = conn.prepareStatement(
									"SELECT COUNT(hall_name) FROM screening WHERE hall_cinema_location='"
											+ Cinema[option] + "' AND movie_title ='" + Movie[option3]
											+ "' AND start_time ='" + Start_Time + "'");

							ResultSet res5 = pst5.executeQuery();
							while (res5.next()) {
								count = res5.getInt("COUNT(hall_name)");
							}
							String[] Hall = new String[count + 1];
							String[] Full_Hall = new String[count + 1];
							String[] Not_Full_Hall = new String[count + 1];
							int[] Tickets_Test = new int[count + 1];
							PreparedStatement pst6 = conn
									.prepareStatement("SELECT hall_name FROM screening WHERE hall_cinema_location='"
											+ Cinema[option] + "' AND movie_title ='" + Movie[option3]
											+ "' AND start_time ='" + Start_Time + "'");
							ResultSet res6 = pst6.executeQuery();
							i = 1;
							while (res6.next()) {
								Hall[i] = res6.getString("hall_name");
								i = i + 1;
							}
							if (count != 0) {
								for (i = 1; i < count + 1; i++) {
									PreparedStatement pst7 = conn
											.prepareStatement("SELECT total_seats FROM hall WHERE cinema_location='"
													+ Cinema[option] + "' AND name ='" + Hall[i] + "'");
									ResultSet res7 = pst7.executeQuery();
									while (res7.next()) {
										Seats = res7.getInt("total_seats");
									}
									PreparedStatement pst8 = conn.prepareStatement(
											"SELECT SUM(total_tickets) FROM booking WHERE screening_hall_cinema_location ='"
													+ Cinema[option] + "' AND screening_hall_name='" + Hall[i]
													+ "' AND screening_start_time='" + Start_Time
													+ "' AND screening_movie_title='" + Movie[option3] + "'");
									ResultSet res8 = pst8.executeQuery();
									while (res8.next()) {
										Total_Tickets = res8.getInt("SUM(total_tickets)");
									}
									if (Total_Tickets < Seats) {
										Not_Full_Hall[j] = Hall[i];
										Tickets_Test[j] = Seats - Total_Tickets;
										j = j + 1;
									} else {
										Full_Hall[k] = Hall[i];
										k = k + 1;
									}
									Total_Tickets = 0;
								}
								if (j > 1) {
									System.out.println(
											"\nThese are the Available Halls for your Screening, choose one:\n");
									for (i = 1; i < j; i++) {
										System.out.println("[" + i + "] " + Not_Full_Hall[i]);
									}
									option4 = sc.nextInt();
									flag = false;
									while (flag == false) {
										for (i = 1; i < j; i++) {
											if (option4 == i) {
												flag = true;
											}
										}
										if (flag == false) {
											System.out.println("No such option, enter again:");
											option4 = sc.nextInt();
										}
									}
									String[] Customer = new String[4];
									System.out.println("Enter the Customer's First Name:");
									Customer[0] = sc2.nextLine();
									System.out.println("Enter the Customer's Last Name:");
									Customer[1] = sc2.nextLine();
									System.out.println(
											"Would you like to enter the Customer's e-mail address ?\nPress 'y' for yes, otherwise any other key:");
									option2 = sc2.nextLine();
									if (option2.equals("y")) {
										System.out.println("Enter the Customer's e-mail address:");
										Customer[2] = sc2.nextLine();
									}
									System.out.println("Enter the Customer's Phone Number:");
									Customer[3] = sc2.nextLine();

									PreparedStatement pst9 = conn
											.prepareStatement("SELECT COUNT(first_name) FROM customer");
									ResultSet res9 = pst9.executeQuery();
									while (res9.next()) {
										count = res9.getInt("COUNT(first_name)");
									}

									String[] CheckFirst_Name = new String[count];
									String[] CheckLast_Name = new String[count];
									String[] CheckPhone_Number = new String[count];

									PreparedStatement pst10 = conn.prepareStatement(
											"SELECT first_name, last_name, phone_number FROM customer");
									ResultSet res10 = pst10.executeQuery();
									i = 0;
									while (res10.next()) {
										CheckFirst_Name[i] = res10.getString("first_name");
										CheckLast_Name[i] = res10.getString("last_name");
										CheckPhone_Number[i] = res10.getString("phone_number");
										i = i + 1;
									}
									flag = false;
									for (i = 0; i < count; i++) {
										if ((Customer[0].equals(CheckFirst_Name[i]))
												&& (Customer[1].equals(CheckLast_Name[i]))
												&& (Customer[3].equals(CheckPhone_Number[i]))) {
											System.out.println("\nThis Customer already exists.");
											flag = true;
										}
									}
									if (flag == false) {
										PreparedStatement pst11 = conn
												.prepareStatement("INSERT INTO customer VALUES (?,?,?,?);");
										pst11.setString(1, Customer[0]);
										pst11.setString(2, Customer[1]);
										pst11.setString(3, Customer[2]);
										pst11.setString(4, Customer[3]);
										pst11.execute();
										System.out.println("Customer successfully added.");
									}
									System.out.println("\nEnter the number of tickets you would to book:");
									Tickets = sc.nextInt();
									flag = false;
									while (flag == false) {
										if (Tickets <= Tickets_Test[option4]) {
											PreparedStatement pst20 = conn
													.prepareStatement("SELECT DISTINCT(price) FROM seat WHERE cinema_location='"
															+ Cinema[option] + "' AND name ='" + Hall[i] + "'");
											ResultSet res20 = pst20.executeQuery();
											while (res20.next()) {
												Price = res20.getInt("price");
											}
											Total_Price = (Price * Tickets);

											String Booking, FirstEightChars = "";

											UUID RandomUUID = UUID.randomUUID();
											String ID = RandomUUID.toString().replace("-", "");
											FirstEightChars = ID.substring(0, 8);
											Booking = FirstEightChars.toUpperCase();

											PreparedStatement pst12 = conn.prepareStatement(
													"INSERT INTO booking VALUES (?,?,?,?,?,?,?,?,?,?);");
											pst12.setString(1, Booking);
											pst12.setInt(2, Tickets);
											pst12.setInt(3, Total_Price);
											pst12.setString(4, Start_Time);
											pst12.setString(5, Not_Full_Hall[option4]);
											pst12.setString(6, Cinema[option]);
											pst12.setString(7, Movie[option3]);
											pst12.setString(8, Customer[0]);
											pst12.setString(9, Customer[1]);
											pst12.setString(10, Customer[3]);
											pst12.execute();
											System.out.println(
													"\nBooking successfully added. These are your Booking's info:\n\nCinema: "
															+ Cinema[option] + "\nMovie: " + Movie[option3]
															+ "\nStart Time: " + Start_Time + "\nHall: "
															+ Not_Full_Hall[option4] + "\nCustomer's info: "
															+ Customer[0] + " " + Customer[1] + " - " + Customer[3]
															+ "\nTotal Tickets: " + Tickets + "\nTotal Price: "
															+ Total_Price + "\nBooking ID: " + Booking);
											flag = true;
										} else {
											if (Tickets_Test[option4] != 1) {
												System.out.println("\nThere are only " + Tickets_Test[option4]
														+ " Tickets left for this Screening. Would you like to Book them ?\nPress 'y' for yes, otherwise any other key:");
											} else {
												System.out.println("\nThere is only " + Tickets_Test[option4]
														+ " Ticket left for this Screening. Would you like to Book it ?\nPress 'y' for yes, otherwise any other key:");
											}
											option10 = sc2.nextLine();
											if (option10.equals("y")) {
												Tickets = Tickets_Test[option4];
											} else {
												flag = true;
											}
										}
									}
								} else {
									System.out.println("\nUnfortunately, all Halls are unavailable right now.");
								}
							} else {
								System.out.println("\nThere are no Available Screenings at that time.");
							}

						} catch (ParseException e) {
							e.printStackTrace();
						}
					} catch (SQLException s) {
						System.out.println("SQL statement is not executed !");
						System.out.println(s);
					}
					System.out.println(menu);
					option = sc.nextInt();
					while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5)
							&& (option != 6) && (option != 7) && (option != 0)) {
						System.out.println("No such option, enter again:");
						option = sc.nextInt();
					}
				}
			}

			// ii) Playing Now
			if (option == 2) {
				try {
					int i = 0, count = 0;
					String Movie;

					PreparedStatement pst = conn
							.prepareStatement("SELECT COUNT(DISTINCT(hall_cinema_location)) FROM screening");
					ResultSet res = pst.executeQuery();
					while (res.next()) {
						count = res.getInt("COUNT(DISTINCT(hall_cinema_location))");
					}

					String[] Cinema = new String[count];

					PreparedStatement pst2 = conn
							.prepareStatement("SELECT DISTINCT hall_cinema_location FROM screening");
					ResultSet res2 = pst2.executeQuery();
					while (res2.next()) {
						Cinema[i] = res2.getString("hall_cinema_location");
						i = i + 1;
					}
					for (i = 0; i < count; i++) {
						PreparedStatement pst3 = conn.prepareStatement(
								"SELECT DISTINCT movie_title FROM screening WHERE hall_cinema_location='" + Cinema[i]
										+ "'");
						ResultSet res3 = pst3.executeQuery();
						System.out.println(
								"\nVillage Cinemas -" + Cinema[i] + "- plays the following Movies this period:\n");
						while (res3.next()) {
							Movie = res3.getString("movie_title");
							System.out.println(Movie);
						}
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					}
				} catch (SQLException s) {
					System.out.println("SQL statement is not executed !");
					System.out.println(s);
				}
				System.out.println(menu);
				option = sc.nextInt();
				while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5)
						&& (option != 6) && (option != 7) && (option != 0)) {
					System.out.println("No such option, enter again:");
					option = sc.nextInt();
				}
			}

			// iii) Full Screening's
			if (option == 3) {
				try {
					int Seats = 0, Total_Tickets = 0;
					int i = 1, count = 0, count2 = 0;
					boolean flag = false;

					PreparedStatement pst = conn
							.prepareStatement("SELECT COUNT(DISTINCT(hall_cinema_location)) FROM screening");
					ResultSet res = pst.executeQuery();
					while (res.next()) {
						count = res.getInt("COUNT(DISTINCT(hall_cinema_location))");
					}

					String[] Cinema = new String[count + 1];

					PreparedStatement pst2 = conn
							.prepareStatement("SELECT DISTINCT hall_cinema_location FROM screening");
					ResultSet res2 = pst2.executeQuery();
					System.out.println("\nSelect the Cinema you would like to check the Screening's availability:\n");
					while (res2.next()) {
						Cinema[i] = res2.getString("hall_cinema_location");
						System.out.println("[" + i + "] " + Cinema[i]);
						i = i + 1;
					}
					option = sc.nextInt();
					while (flag == false) {
						for (i = 1; i < count + 1; i++) {
							if (option == i) {
								flag = true;
							}
						}
						if (flag == false) {
							System.out.println("No such option, enter again:");
							option = sc.nextInt();
						}
					}
					PreparedStatement pst3 = conn.prepareStatement(
							"SELECT COUNT(DISTINCT screening_hall_name, screening_start_time, screening_movie_title) FROM booking WHERE screening_hall_cinema_location='"
									+ Cinema[option] + "'");
					ResultSet res3 = pst3.executeQuery();
					while (res3.next()) {
						count2 = res3.getInt(
								"COUNT(DISTINCT screening_hall_name, screening_start_time, screening_movie_title)");
					}
					String[] Hall = new String[count2];
					String[] Start_Time = new String[count2];
					String[] Movie = new String[count2];
					PreparedStatement pst4 = conn.prepareStatement(
							"SELECT DISTINCT screening_hall_name, screening_start_time, screening_movie_title FROM booking WHERE screening_hall_cinema_location='"
									+ Cinema[option] + "'");
					ResultSet res4 = pst4.executeQuery();
					i = 0;
					while (res4.next()) {
						Hall[i] = res4.getString("screening_hall_name");
						Start_Time[i] = res4.getString("screening_start_time");
						Movie[i] = res4.getString("screening_movie_title");
						i = i + 1;
					}
					flag = false;
					for (i = 0; i < count2; i++) {
						PreparedStatement pst5 = conn
								.prepareStatement("SELECT total_seats FROM hall WHERE cinema_location='"
										+ Cinema[option] + "' AND name ='" + Hall[i] + "'");
						ResultSet res5 = pst5.executeQuery();
						while (res5.next()) {
							Seats = res5.getInt("total_seats");
						}
						PreparedStatement pst6 = conn.prepareStatement(
								"SELECT SUM(total_tickets) FROM booking WHERE screening_hall_cinema_location ='"
										+ Cinema[option] + "' AND screening_hall_name='" + Hall[i]
										+ "' AND screening_start_time='" + Start_Time[i]
										+ "' AND screening_movie_title='" + Movie[i] + "'");
						ResultSet res6 = pst6.executeQuery();
						while (res6.next()) {
							Total_Tickets = res6.getInt("SUM(total_tickets)");
						}
						if (Total_Tickets >= Seats) {
							if (flag == false) {
								System.out.println(
										"\nThe following Screenings are full in -" + Cinema[option] + "- Cinema:");
							}
							System.out.println("\nMovie: " + Movie[i] + "\nStart Time: " + Start_Time[i] + "\nHall: "
									+ Hall[i] + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							flag = true;
						}
						Total_Tickets = 0;
					}
					if (flag == false) {
						System.out.println("\nAll Screenings are availabe.");
					}
				} catch (SQLException s) {
					System.out.println("SQL statement is not executed !");
					System.out.println(s);
				}
				System.out.println(menu);
				option = sc.nextInt();
				while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5)
						&& (option != 6) && (option != 7) && (option != 0)) {
					System.out.println("No such option, enter again:");
					option = sc.nextInt();
				}
			}

			// iv) Available Screenings & Booking
			if (option == 4) {
				try {
					int i = 1, count = 0, Seats = 0, Total_Tickets = 0, option3 = 0, j = 1, k = 0, option4 = 0, Tickets,
							Total_Price, Price = 0;
					String Start_Time, option5, option10;
					Date Timestamp;
					boolean flag = false;
					int test, Length = 0;

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

					PreparedStatement pst = conn.prepareStatement("SELECT COUNT(location) FROM cinema");
					ResultSet res = pst.executeQuery();
					while (res.next()) {
						count = res.getInt("COUNT(location)");
					}
					String[] CinemaCheck = new String[count + 1];
					PreparedStatement pst2 = conn.prepareStatement("SELECT location FROM cinema");
					ResultSet res2 = pst2.executeQuery();
					System.out.println("\nSelect the Cinema:\n");
					while (res2.next()) {
						CinemaCheck[i] = res2.getString("location");
						System.out.println("[" + i + "] " + CinemaCheck[i]);
						i = i + 1;
					}
					option = sc.nextInt();
					while (flag == false) {
						for (i = 1; i < count + 1; i++) {
							if (option == i) {
								flag = true;
							}
						}
						if (flag == false) {
							System.out.println("No such option, enter again:");
							option = sc.nextInt();
						}
					}
					PreparedStatement pst3 = conn.prepareStatement(
							"SELECT COUNT(DISTINCT(movie_title)) FROM screening WHERE hall_cinema_location = '"
									+ CinemaCheck[option] + "'");
					ResultSet res3 = pst3.executeQuery();
					while (res3.next()) {
						count = res3.getInt("COUNT(DISTINCT(movie_title))");
					}
					String[] MovieCheck = new String[count + 1];
					i = 1;
					PreparedStatement pst4 = conn.prepareStatement(
							"SELECT DISTINCT movie_title  FROM screening WHERE hall_cinema_location = '"
									+ CinemaCheck[option] + "'");
					ResultSet res4 = pst4.executeQuery();
					System.out.println("\nSelect the Movie:\n");
					while (res4.next()) {
						MovieCheck[i] = res4.getString("movie_title");
						System.out.println("[" + i + "] " + MovieCheck[i]);
						i = i + 1;
					}
					option3 = sc.nextInt();
					flag = false;
					while (flag == false) {
						for (i = 1; i < count + 1; i++) {
							if (option3 == i) {
								flag = true;
							}
						}
						if (flag == false) {
							System.out.println("No such option, enter again:");
							option3 = sc.nextInt();
						}
					}
					System.out.println(
							"\nWould you like to enter a desirable time period for the Screening ?\nPress 'y' for yes, otherwise any other key:");
					option2 = sc2.nextLine();
					flag = false;
					if (option2.equals("y")) {
						System.out.println("\nEnter the desirable time period in total minutes:");
						test = sc.nextInt();
						PreparedStatement pst20 = conn
								.prepareStatement("SELECT length FROM movie WHERE title = '" + MovieCheck[option3] + "'");
						ResultSet res20 = pst20.executeQuery();
						while (res20.next()) {
							Length = res20.getInt("length");
						}
						int Final = Length - test;
						if (Final >= 30) {
							flag = true;
							System.out.println("\nUnfortunately, this Movie lasts way longer.");
						} 
					}
					if (flag == false) {
						try {
							System.out.println("\nEnter the Screening's Date and Time in -yyyy-MM-dd HH:mm- format:");
							Start_Time = sc2.nextLine();
							Timestamp = formatter.parse(Start_Time);
							Start_Time = formatter.format(Timestamp);
							PreparedStatement pst5 = conn.prepareStatement(
									"SELECT COUNT(hall_name) FROM screening WHERE hall_cinema_location='"
											+ CinemaCheck[option] + "' AND movie_title ='" + MovieCheck[option3]
											+ "' AND start_time ='" + Start_Time + "'");
							ResultSet res5 = pst5.executeQuery();
							while (res5.next()) {
								count = res5.getInt("COUNT(hall_name)");
							}
							String[] Hall = new String[count + 1];
							String[] Full_Hall = new String[count + 1];
							String[] Not_Full_Hall = new String[count + 1];
							int[] Tickets_Test = new int[count + 1];
							PreparedStatement pst6 = conn
									.prepareStatement("SELECT hall_name FROM screening WHERE hall_cinema_location='"
											+ CinemaCheck[option] + "' AND movie_title ='" + MovieCheck[option3]
											+ "' AND start_time ='" + Start_Time + "'");
							ResultSet res6 = pst6.executeQuery();
							i = 1;
							while (res6.next()) {
								Hall[i] = res6.getString("hall_name");
								i = i + 1;
							}
							if (count != 0) {
								for (i = 1; i < count + 1; i++) {
									PreparedStatement pst7 = conn
											.prepareStatement("SELECT total_seats FROM hall WHERE cinema_location='"
													+ CinemaCheck[option] + "' AND name ='" + Hall[i] + "'");
									ResultSet res7 = pst7.executeQuery();
									while (res7.next()) {
										Seats = res7.getInt("total_seats");
									}
									PreparedStatement pst8 = conn.prepareStatement(
											"SELECT SUM(total_tickets) FROM booking WHERE screening_hall_cinema_location ='"
													+ CinemaCheck[option] + "' AND screening_hall_name='" + Hall[i]
													+ "' AND screening_start_time='" + Start_Time
													+ "' AND screening_movie_title='" + MovieCheck[option3] + "'");
									ResultSet res8 = pst8.executeQuery();
									while (res8.next()) {
										Total_Tickets = res8.getInt("SUM(total_tickets)");
									}
									if (Total_Tickets < Seats) {
										Not_Full_Hall[j] = Hall[i];
										Tickets_Test[j] = Seats - Total_Tickets;
										j = j + 1;
									} else {
										Full_Hall[k] = Hall[i];
										k = k + 1;
									}
									Total_Tickets = 0;
								}
								if (j > 1) {
									System.out.println(
											"\nThese are the Available Halls for your Screening, choose one:\n");
									for (i = 1; i < j; i++) {
										System.out.println("[" + i + "] " + Not_Full_Hall[i]);
									}
									option4 = sc.nextInt();
									flag = false;
									while (flag == false) {
										for (i = 1; i < j; i++) {
											if (option4 == i) {
												flag = true;
											}
										}
										if (flag == false) {
											System.out.println("No such option, enter again:");
											option4 = sc.nextInt();
										}
									}

									// v) Booking
									System.out.println(
											"Would you like to continue with this Booking ?\nPress 'y' or any other key to abort:");
									option5 = sc2.nextLine();
									if (option5.equals("y")) {
										String[] Customer = new String[4];
										System.out.println("Enter the Customer's First Name:");
										Customer[0] = sc2.nextLine();
										System.out.println("Enter the Customer's Last Name:");
										Customer[1] = sc2.nextLine();
										System.out.println(
												"Would you like to enter the Customer's e-mail address ?\nPress 'y' for yes, otherwise any other key:");
										option2 = sc2.nextLine();
										if (option2.equals("y")) {
											System.out.println("Enter the Customer's e-mail address:");
											Customer[2] = sc2.nextLine();
										}
										System.out.println("Enter the Customer's Phone Number:");
										Customer[3] = sc2.nextLine();

										PreparedStatement pst9 = conn
												.prepareStatement("SELECT COUNT(first_name) FROM customer");
										ResultSet res9 = pst9.executeQuery();
										while (res9.next()) {
											count = res9.getInt("COUNT(first_name)");
										}

										String[] CheckFirst_Name = new String[count];
										String[] CheckLast_Name = new String[count];
										String[] CheckPhone_Number = new String[count];

										PreparedStatement pst10 = conn.prepareStatement(
												"SELECT first_name, last_name, phone_number FROM customer");
										ResultSet res10 = pst10.executeQuery();
										i = 0;
										while (res10.next()) {
											CheckFirst_Name[i] = res10.getString("first_name");
											CheckLast_Name[i] = res10.getString("last_name");
											CheckPhone_Number[i] = res10.getString("phone_number");
											i = i + 1;
										}
										flag = false;
										for (i = 0; i < count; i++) {
											if ((Customer[0].equals(CheckFirst_Name[i]))
													&& (Customer[1].equals(CheckLast_Name[i]))
													&& (Customer[3].equals(CheckPhone_Number[i]))) {
												System.out.println("\nThis Customer already exists.");
												flag = true;
											}
										}
										if (flag == false) {
											PreparedStatement pst11 = conn
													.prepareStatement("INSERT INTO customer VALUES (?,?,?,?);");
											pst11.setString(1, Customer[0]);
											pst11.setString(2, Customer[1]);
											pst11.setString(3, Customer[2]);
											pst11.setString(4, Customer[3]);
											pst11.execute();
											System.out.println("Customer successfully added.");
										}
										System.out.println("\nEnter the number of tickets you would to book:");
										Tickets = sc.nextInt();
										flag = false;
										while (flag == false) {
											if (Tickets <= Tickets_Test[option4]) {
												PreparedStatement pst20 = conn
														.prepareStatement("SELECT DISTINCT(price) FROM seat WHERE cinema_location='"
																+ CinemaCheck[option] + "' AND name ='" + Hall[i] + "'");
												ResultSet res20 = pst20.executeQuery();
												while (res20.next()) {
													Price = res20.getInt("price");
												}
												Total_Price = (Price * Tickets);

												String Booking, FirstEightChars = "";

												UUID RandomUUID = UUID.randomUUID();
												String ID = RandomUUID.toString().replace("-", "");
												FirstEightChars = ID.substring(0, 8);
												Booking = FirstEightChars.toUpperCase();

												PreparedStatement pst12 = conn.prepareStatement(
														"INSERT INTO booking VALUES (?,?,?,?,?,?,?,?,?,?);");
												pst12.setString(1, Booking);
												pst12.setInt(2, Tickets);
												pst12.setInt(3, Total_Price);
												pst12.setString(4, Start_Time);
												pst12.setString(5, Not_Full_Hall[option4]);
												pst12.setString(6, CinemaCheck[option]);
												pst12.setString(7, MovieCheck[option3]);
												pst12.setString(8, Customer[0]);
												pst12.setString(9, Customer[1]);
												pst12.setString(10, Customer[3]);
												pst12.execute();
												System.out.println(
														"\nBooking successfully added. These are your Booking's info:\n\nCinema: "
																+ CinemaCheck[option] + "\nMovie: "
																+ MovieCheck[option3] + "\nStart Time: " + Start_Time
																+ "\nHall: " + Not_Full_Hall[option4]
																+ "\nCustomer's info: " + Customer[0] + " "
																+ Customer[1] + " - " + Customer[3]
																+ "\nTotal Tickets: " + Tickets + "\nTotal Price: "
																+ Total_Price + "\nBooking ID: " + Booking);
												flag = true;
											} else {
												if (Tickets_Test[option4] != 1) {
													System.out.println("\nThere are only " + Tickets_Test[option4]
															+ " Tickets left for this Screening. Would you like to Book them ?\nPress 'y' for yes, otherwise any other key:");
												} else {
													System.out.println("\nThere is only " + Tickets_Test[option4]
															+ " Ticket left for this Screening. Would you like to Book it ?\nPress 'y' for yes, otherwise any other key:");
												}
												option10 = sc2.nextLine();
												if (option10.equals("y")) {
													Tickets = Tickets_Test[option4];
												} else {
													flag = true;
												}
											}
										}
									}
								} else {
									System.out.println("\nUnfortunately, all Halls are unavailable right now.");
								}
							} else {
								System.out.println("\nThere are no Available Screenings at that time.");
							}

						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				} catch (SQLException s) {
					System.out.println("SQL statement is not executed !");
					System.out.println(s);
				}
				System.out.println(menu);
				option = sc.nextInt();
				while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5)
						&& (option != 6) && (option != 7) && (option != 0)) {
					System.out.println("No such option, enter again:");
					option = sc.nextInt();
				}
			}

			// vi) Booking Payment
			if (option == 5) {
				try {
					String[] BookingArray = new String[100];
					String Booking;
					String Paid;
					boolean flag = false;
					int i, j = 1;
					PreparedStatement pst = conn.prepareStatement("SELECT id FROM booking");
					ResultSet res = pst.executeQuery();
					while (res.next()) {
						BookingArray[j] = res.getString("id");
						j = j + 1;
					}
					System.out.println("\nEnter the Booking's ID:");
					Booking = sc2.nextLine();
					for (i = 1; i < j; i++) {
						if (Booking.equals(BookingArray[i])) {
							PreparedStatement pst2 = conn
									.prepareStatement("SELECT paid FROM booking WHERE id= '" + Booking + "'");
							ResultSet res2 = pst2.executeQuery();
							while (res2.next()) {
								Paid = res2.getString("paid");
								if (Paid.equals("true")) {
									System.out.println("\nBooking has already been paid.");
								} else {
									System.out.println("\nBooking has been successfully paid.");
								}
							}
							flag = true;
						}
					}
					if (flag == false) {
						System.out.println("\nThere is no Booking with such ID.");
					}
				} catch (SQLException s) {
					System.out.println("SQL statement is not executed !");
					System.out.println(s);
				}
				System.out.println(menu);
				option = sc.nextInt();
				while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5)
						&& (option != 6) && (option != 7) && (option != 0)) {
					System.out.println("No such option, enter again:");
					option = sc.nextInt();
				}
			}

			// vii) Delete a Movie
			if (option == 6) {
				try {
					int i = 1, count = 0;
					boolean flag = false;

					PreparedStatement pst = conn.prepareStatement("SELECT COUNT(title) FROM movie");
					ResultSet res = pst.executeQuery();
					while (res.next()) {
						count = res.getInt("COUNT(title)");
					}

					String[] Movie = new String[count + 1];

					PreparedStatement pst2 = conn.prepareStatement("SELECT title FROM movie");
					ResultSet res2 = pst2.executeQuery();
					System.out.println("\nSelect the Movie you would like to delete:\n");
					while (res2.next()) {
						Movie[i] = res2.getString("title");
						System.out.println("[" + i + "] " + Movie[i]);
						i = i + 1;
					}
					option = sc.nextInt();
					while (flag == false) {
						for (i = 1; i < count + 1; i++) {
							if (option == i) {
								flag = true;
							}
						}
						if (flag == false) {
							System.out.println("No such option, enter again:");
							option = sc.nextInt();
						}
					}
					PreparedStatement pst3 = conn
							.prepareStatement("DELETE FROM booking WHERE screening_movie_title = ?");
					pst3.setString(1, Movie[option]);
					pst3.executeUpdate();
					PreparedStatement pst4 = conn.prepareStatement("DELETE FROM screening WHERE movie_title = ?");
					pst4.setString(1, Movie[option]);
					pst4.executeUpdate();
					PreparedStatement pst5 = conn.prepareStatement("DELETE FROM movie WHERE title = ?");
					pst5.setString(1, Movie[option]);
					pst5.executeUpdate();
					System.out.println("\nMovie successfully deleted.");
				} catch (SQLException s) {
					System.out.println("SQL statement is not executed !");
					System.out.println(s);
				}
				System.out.println(menu);
				option = sc.nextInt();
				while ((option != 1) && (option != 2) && (option != 3) && (option != 4) && (option != 5)
						&& (option != 6) && (option != 7) && (option != 0)) {
					System.out.println("No such option, enter again:");
					option = sc.nextInt();
				}
			}

			// Termination
			if (option == 0) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("\nDisconnected from database.");
				sc.close();
				sc2.close();
				System.exit(0);
			}
		}
	}
}