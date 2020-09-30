//Kate Blunt
//COP2552
//September 15th, 2020
//Project 2

//To do:
//Open the two files, CurrentStatusIn and Ship0916
//Read through each of the files
//Write necessary text to the output files CurrentStatusOut and Order
//Open the two files, CurrentStatusIn and Ship0916
//Read through each file in opposite direction (switch the inner and outer loop)
//Write necessary text to the output file Error (do not have this file created if there are no errors)
//close all the files and exit the application
//Close scanner not the file **** 

//Needed for files
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Need for date and time
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//Scanner for the file
import java.util.Scanner;

//used for testing purposes
import javax.swing.JOptionPane;


public class BookStoreFile {

	
	public void driver()
	{
				//open first file(CurrentStatus)
				File fileOne = openFileOne();
				
				//open second file (Ship0916)
				File fileTwo = openFileTwo();
				
				//open first output file to write to (CurrentStatusOut)
				PrintWriter outputCurrentStatus = openOutputFileOne();
				
				//open second output file to write to (Order0916)
				PrintWriter outputOrder = openOutputFileTwo();
				
				
				//openOutputFileThree();
				systemDate(outputCurrentStatus);
				
				//read files, including input and output
				readFiles(fileOne, fileTwo, outputCurrentStatus, outputOrder);
				
				//errorText
				int errors = errorText(fileTwo, fileOne);
				
				//write the error
				writeError(fileTwo, fileOne, errors);
				
				
				//close the output files
				closeOutputFiles(outputCurrentStatus, outputOrder);		
				
	}
	

	//******************************************Methods and Functions************************************
	
	
	//*****************************************Input Files***********************************************/
	//open the first file
	 public File openFileOne()
	{
		//open first file 
				File statusIn = new File("CurrentStatusIn.txt");
				

				return statusIn;		
	}


	 public File openFileTwo()
	{
		//open the second file
				File shipIn = new File("Ship0916.txt");
				
				return shipIn;
		
		
	}
	 
	//****************************************************Output files***************************************//
	 public PrintWriter openOutputFileOne()
	 {
		
				//open file you want to write to
				PrintWriter outputCurrentStatus = null;
				try {
					
					//one slash represents the beginning of an escape sequence, so you must do two slashes
					outputCurrentStatus = new PrintWriter("C:\\SFC\\COP2552\\Project2\\CurrentStatusOut.txt");
					
				} 
				
				
				catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "CurrenntStatusOut.txt not found");
					System.exit(0);	
				}	 
				
				return outputCurrentStatus;
	 }
	 
	 public PrintWriter openOutputFileTwo()
	 {
		 
			PrintWriter outputOrder = null;
			
			try {
				outputOrder = new PrintWriter("C:\\SFC\\COP2552\\Project2\\Order0916.txt");
				
			} 
			
			catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Order0916.txt not found");
				System.exit(0);	
			}
		
		 return outputOrder;
		 
		 
	 }
	 
	public PrintWriter openOutputFileThree()
	 {
		PrintWriter outputError = null;
		
		try {
			outputError = new PrintWriter("C:\\SFC\\COP2552\\Project2\\Error0916.txt");
			
		} 
		
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error0916.txt not found");
			System.exit(0);	
		}

	 return outputError;	 
		 
	 }
	

	 //******************************** End of File management *********************************************//
	 

	//********************************Read File, main part of program*************************************//

	//this first readFile method reads in the isbn and stock quanitites from the file and loops through 
	//the shipin file in order to get quantities needed to order and adding the shipment to the current stock 
	//if the isbns match
	
	 public void readFiles(File statusIn, File shipIn, PrintWriter outputCurrentStatus, PrintWriter outputOrder)
	{
		//create a Scanner object that used the file as its source of input
			Scanner inputFile = null;
			try {
				inputFile = new Scanner(statusIn);
			} catch (FileNotFoundException e) {
				
				JOptionPane.showMessageDialog(null, "CurrentStatusIn.txt not found");
				System.exit(0);	
				
			}	
		  
		 
		//read the first line from the file -- this is the date so it is not needed -- kind of like a prime read
				String date = inputFile.nextLine();
				
				//test to make sure the date is out of the file
				//System.out.println("The first line of the text is:");
				//System.out.println(date);
		 
		 
		//while the file continues to have content (The CurrentStatusFile)
		while (inputFile.hasNext()) 
		{
			
			//I used a counter otherwise the second while loop printed everything six times
			int counter = 1;
			
			//isbn of first file
			String isbn = inputFile.nextLine();
			
			//current quantity in store
			String current = inputFile.nextLine();
			
			//convert to integer 
			int currentNum = 0;
			currentNum = Integer.parseInt(current);
			
			//stock quantity
			String stock = inputFile.nextLine();
			
			//convert to integer
			int stockNum = 0;
			stockNum = Integer.parseInt(stock);
			
				
			//create a Scanner object that used the file as its source of input
					Scanner inputFileTwo = null;
					try {
						inputFileTwo = new Scanner(shipIn);
					} catch (FileNotFoundException e) {
					
						JOptionPane.showMessageDialog(null, "Ship0916.txt not found");
						System.exit(0);	
					}
				
				//here is the loop to compare the second file to the first file (The Shipped File)
				while (inputFileTwo.hasNext())
				{
				
				//isbn of second file
				String isbnTwo = inputFileTwo.nextLine();
				
				//shipped books quantity in second file
				String shippedBooks = inputFileTwo.nextLine();
				
				
				//convert to integer 
				int shippedbooksNum = 0;
				shippedbooksNum = Integer.parseInt(shippedBooks);
				
				
				
				//if the isbn match, add the quantity shipped to the current quantity
				//in Java the == means comparing pointers. This took a long time to troubleshoot because I knew they were equal but wasn't
				//sure why it wasn't saying they were
				if(isbn.contentEquals(isbnTwo))
					
				{
					//add the quantity shipped to the current quantity
					currentNum = currentNum + shippedbooksNum;
					
					//testing
					//System.out.println("DOES Match");
					
					
					//write out to output file 
					outputCurrentStatus.println(isbn);
					outputCurrentStatus.println(currentNum);
					outputCurrentStatus.println(stockNum);
					
					//testing
					//outputCurrentStatus.println("\nMatch\n");
					
					
					//if stock is less than 25% how many books does it take to get to the stock quantity
					//have to cast one of these to float or else an integer divided by an integer is an integer
					
					if ((currentNum/(float)stockNum) < .25)
					{
						//quantity needed to get to stock num is just subtracting the stock minus the current 
						int stockNeeded = stockNum - currentNum;
						
						//write to order file
						outputOrder.println(isbn);
						outputOrder.println(stockNeeded);
						
					}
							
				
					//no longer need to be in this loop so break out. There was a match in the Ship file, so we can move to 
					//the next isbn in the CurrentStatusFile
					break;
				}	
				
		
				
				//if none of them match, print to the text log
				//this counter was because I was getting six times the print out for every isbn in the first file
				//This will print out once because there are six isbns in the Ship file, so if counter is 6 at this point
				//you know that none match and you can print
				
				if (counter == 6)
				{
					
						//System.out.println("NOOOO Match");
					
						outputCurrentStatus.println(isbn);
						outputCurrentStatus.println(currentNum);
						outputCurrentStatus.println(stockNum);
						counter = 1;
					
						if ((currentNum/(float)stockNum) < .25)
						{
							//quantity needed to get to stock num is just subtracting the stock minus the current 
							int stockNeeded = stockNum - currentNum;
							
							//write to order file
							outputOrder.println(isbn);
							outputOrder.println(stockNeeded);
							
						}
						
						
					
					break;
					
				}
				

				counter++;
				
				} //end of while loop
								
				
				close(inputFileTwo);
					
		}
		
		//have to close the output files after all the data is written
		closeOutputFiles(outputOrder, outputCurrentStatus);
		close(inputFile);
		
	}

	 //******************************************* Error output file ***********************************************//
	 
	
	 //errorText checks for errors and counts the errors in the system
	 
	 public int errorText (File shipIn, File statusIn)
	 {
		 
		 int errors = 0; 
		 
		//create a Scanner object that used the file as its source of input
				Scanner inputFile = null;
				try {
					inputFile = new Scanner(shipIn);
				} catch (FileNotFoundException e) {
					
					JOptionPane.showMessageDialog(null, "Ship0916.txt not found");
					System.exit(0);	
				}	
			 
			  
					//while the file continues to have content (file one) do this
			while (inputFile.hasNext()) 
			{
				
				//I used a counter otherwise the second while loop printed everything six times
				int counter = 0;
				int equalCounter = 0;
				
				//isbn of first file
				String isbn = inputFile.nextLine();
				
				//current quantity in store
				String shipped = inputFile.nextLine();
				
				//convert to integer 
				int shippedNum = 0;
				shippedNum = Integer.parseInt(shipped);
				
				//System.out.println(isbn);
				//System.out.println(shipped);
				
				
				
				
				//create a Scanner object that used the file as its source of input
						Scanner inputFileTwo = null;
						try {
							inputFileTwo = new Scanner(statusIn);
						} catch (FileNotFoundException e) {
						
							JOptionPane.showMessageDialog(null, "CurrentStatusIn.txt not found");
							System.exit(0);	
						}
						
						//read the first line from the file -- this is the date so it is not needed -- kind of like a prime read
						String date = inputFileTwo.nextLine();
						
						//test to make sure the date is out of the file
						//System.out.println("The first line of the text is:");
						//System.out.println(date);
						
					
					//here is the loop to compare the second file to the first file
					while (inputFileTwo.hasNext())
					{
					
					counter++;
						
					//isbn of second file
					String isbnTwo = inputFileTwo.nextLine();
					
					//shipped books quantity in second file
					String current = inputFileTwo.nextLine();
					
					//stock quantity
					String stock = inputFileTwo.nextLine();
					
					
					//Error testing
					
					int isbnInt = Integer.parseInt(isbn.substring(5)); 
					int isbnIntTwo = Integer.parseInt(isbnTwo.substring(5));
					
					//if the isbns matched, make sure to make note of this
					if (isbnInt == isbnIntTwo)
					{
						equalCounter = 1; 
					}
					
					//if there is an error, count it so we know whether to make an error file
					if (isbnInt < isbnIntTwo && counter == 15 && equalCounter != 1)
					
					
					
					{ 
						errors++;
						
						//System.out.println("Working");
						//outputError.println(isbn);
						//outputError.println(shipped);
						
					}
						
					
					
					//end of error testing
			
					//testing
					//System.out.println(counter);
			
					
					} 
					
					//end of while loop
					
					//close the inner loop
					close(inputFileTwo);
						
					
			}
			

			
			//close the outer loop file
			close(inputFile);
		 
			return errors;
		 
	 }
	 
	 
	 //This method is to get the Error file written correctly. In order to do this
	 //I had to read in backwards, the shipIn file first and then loop through the isbn
	 //The logic was difficult for me to figure out, but in order to know that the 
	 //shipping isbn had no previous match and was less than the current status isbn
	 //I had to do it this way. If there are no errors this will not be executed,
	 //thus the Error file will not be written or created
	 
	 public void writeError (File shipIn, File statusIn, int errors)
	 {
		 
		 if (errors > 0)
		 { 
			 
			 	//if there are errors, make the output file Error
				//open the third output file, (Error0916)
				PrintWriter outputError = openOutputFileThree();
				
				//openOutputFileThree();
				systemDate(outputError);
			 
		 
		//create a Scanner object that used the file as its source of input
				Scanner inputFile = null;
				try {
					inputFile = new Scanner(shipIn);
				} catch (FileNotFoundException e) {
					
					JOptionPane.showMessageDialog(null, "Ship0916.txt not found");
					System.exit(0);	
				}	
			 
			  
					//while the file continues to have content (file one) do this
			while (inputFile.hasNext()) 
			{
				
				//I used a counter otherwise the second while loop printed everything six times
				int counter = 0;
				int equalCounter = 0;
				
				//isbn of first file
				String isbn = inputFile.nextLine();
				
				//current quantity in store
				String shipped = inputFile.nextLine();
				
				//convert to integer 
				int shippedNum = 0;
				shippedNum = Integer.parseInt(shipped);
				
				//System.out.println(isbn);
				//System.out.println(shipped);
				
				
				
				
				//create a Scanner object that used the file as its source of input
						Scanner inputFileTwo = null;
						try {
							inputFileTwo = new Scanner(statusIn);
						} catch (FileNotFoundException e) {
						
							JOptionPane.showMessageDialog(null, "CurrentStatusIn.txt not found");
							System.exit(0);	
						}
						
						//read the first line from the file -- this is the date so it is not needed -- kind of like a prime read
						String date = inputFileTwo.nextLine();
						
						//test to make sure the date is out of the file
						//System.out.println("The first line of the text is:");
						//System.out.println(date);
						
					
					//here is the loop to compare the second file to the first file
					while (inputFileTwo.hasNext())
					{
					
					counter++;
						
					//isbn of second file
					String isbnTwo = inputFileTwo.nextLine();
					
					//shipped books quantity in second file
					String current = inputFileTwo.nextLine();
					
					//stock quantity
					String stock = inputFileTwo.nextLine();
					
					
					//Error testing
					
					int isbnInt = Integer.parseInt(isbn.substring(5)); 
					int isbnIntTwo = Integer.parseInt(isbnTwo.substring(5));
					
					//if the isbns matched, make sure to make note of this
					if (isbnInt == isbnIntTwo)
					{
						equalCounter = 1; 
					}
					
					//if the ISBN from the Shipo916 file is less than the value in the CurrentStatusIn.txt input file and
					//no previous match was made, a shipping error has occurred. write the ISBN number and quantity shipped to the Error0916 file.
					if (isbnInt < isbnIntTwo && counter == 15 && equalCounter != 1)
					
					
					
					{ 
						
						
						//System.out.println("Working");
						
						outputError.println(isbn);
						outputError.println(shipped);
						
					}
						
					
					
					//end of error testing
			
					//testing
					//System.out.println(counter);
			
					
					} 
					
					//end of while loop
					
					//close the inner loop
					close(inputFileTwo);
						
					
			}
			
			
			//close the outer loop file
			close(inputFile);
			closeError(outputError);
		 
		
		 }
		 
	 }
	 
	 
	 
	 //method to write the system date at the top of a file
	 public void systemDate(PrintWriter file)
	 
	 {
		 
		 		//system date on top of each output files 
				//https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
				DateFormat date = new SimpleDateFormat("MM/dd/yy");
				Date dateObject = new Date();
			
				
				//date to top of the CurrentStatusOutput text file
				file.println(date.format(dateObject));
				
		 
		 
	 }
	 
	 

	 //close the output files for the CurrentStatusOut and Order
	 public void closeOutputFiles(PrintWriter status, PrintWriter order)
	 
	 {
		 //close the two output files (must be done after everything is written to them!)
		 order.close();
		 status.close();
		
		 
	}

	 //close input files
	 public void close(Scanner file)
	 {
		 file.close();
	 }
	
	 //close the PrintWriter for the error method
	 public void closeError(PrintWriter error)
	 {
		 error.close();
	 }
	
	
}
