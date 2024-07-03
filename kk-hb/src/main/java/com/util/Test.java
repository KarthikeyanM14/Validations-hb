package com.util;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Test {
	private static Validator validator;
	public static void main(String[] args) {
			ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
			validator = validatorFactory.getValidator();
			Example user = getUserInput();
			Set<ConstraintViolation<Example>> violations = validator.validate(user);
			if (violations.isEmpty()) {
				saveToDatabase(user);
				System.out.println("User data saved to database.");
			} else {
				System.out.println("Invalid user data found:");
				for (ConstraintViolation<Example> violation : violations) {
					System.out.println(violation.getMessage());
				}	
			}
		}
	private static Example getUserInput() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("User Id: ");
		long userId = scanner.nextLong();
		scanner.nextLine(); 
		System.out.print("User Name: ");
		String userName = scanner.nextLine();
		System.out.print("User Email ID: ");
		String userEmailId = scanner.nextLine();
		System.out.print("Age: ");
		int age = scanner.nextInt();
		scanner.nextLine();  
		System.out.print("Current Time of Writing Articles: ");
		String currentTimeOfWritingArticles = scanner.nextLine();
		System.out.print("Allowed For Article Reviewing: ");
		String allowedForArticleReviewing = scanner.nextLine();
		System.out.print("Rating: ");
		int rating = scanner.nextInt();
		scanner.nextLine();  
		System.out.print("Blocklisted: ");
		int blocklisted = scanner.nextInt();
		scanner.nextLine();  
		System.out.print("Future Date (yyyy-MM-dd): ");
		String futureDateStr = scanner.next();
		Date futureDate = parseDate(futureDateStr);
		System.out.print("Future Or Present Date (yyyy-MM-dd): ");
		String futureOrPresentDateStr = scanner.next();
		Date futureOrPresent = parseDate(futureOrPresentDateStr);
		System.out.print("Past Or Present Date (yyyy-MM-dd): ");
		String pastOrPresentDateStr = scanner.next();
		Date pastOrPresent = parseDate(pastOrPresentDateStr);
		System.out.print("Range Example: ");
		int rangeExample = scanner.nextInt();
		scanner.nextLine();  
		System.out.print("URL Example: ");
		String urlExample = scanner.nextLine();
		System.out.print("Credit Card Example: ");
		String creditCardExample = scanner.nextLine();
		return new Example(userId, userName, userEmailId, age, currentTimeOfWritingArticles, allowedForArticleReviewing, rating, blocklisted, futureDate, futureOrPresent, pastOrPresent, rangeExample, urlExample, creditCardExample);

    }
private static Date parseDate(String dateStr) {
	try {
		return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
	} catch (ParseException e) {
		throw new RuntimeException("Invalid date format. Please use yyyy-MM-dd.");
		}
	}

	private static void saveToDatabase(Example user) {
		Session session = Hbutil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {	
			transaction = session.beginTransaction();	
			session.merge(user);  
			transaction.commit();
			} catch (Exception e) {
					if (transaction != null) {
					transaction.rollback();
					}
					e.printStackTrace();
			} finally {
				session.close();
				}
			}
		}

       
