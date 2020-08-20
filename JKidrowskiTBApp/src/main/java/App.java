import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class App {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ImportSheets();
	}
	
    static void ImportSheets() throws IOException
    {
    	//setup for student info sheet
    	File StudentInfo = new File("./Spreadsheets/Student Info.xlsx");						//makes our file accessible via variable
    	FileInputStream INPTStudentInfo = new FileInputStream(StudentInfo);						//accesses the file
    	XSSFWorkbook WBInfo = new XSSFWorkbook(INPTStudentInfo);								//sets up a workbook using our spreadsheet file.
    	XSSFSheet InfoSheet = WBInfo.getSheetAt(0);												//gets the first sheet of the document
    	int i = 0;																				//used to increment the array.
    	Student [] Students = new Student[InfoSheet.getLastRowNum()];							//Array to hold the student object data being brought in.

    	//setup for score sheet
    	File TestScore = new File("./Spreadsheets/Test Scores.xlsx");							//makes our file accessible via variable
    	FileInputStream INPTScore1 = new FileInputStream(TestScore);							//accesses the file
    	XSSFWorkbook WBScore1 = new XSSFWorkbook(INPTScore1);									//sets up a workbook using our spreadsheet file.
    	XSSFSheet Score1Sheet = WBScore1.getSheetAt(0);											//gets the first sheet of the document
    	
    	//setup for score 2 sheet
    	File TestScore2 = new File("./Spreadsheets/Test Retake Scores.xlsx");					//makes our file accessible via variable
    	FileInputStream INPTScore2 = new FileInputStream(TestScore2);							//accesses the file
    	XSSFWorkbook WBScore2 = new XSSFWorkbook(INPTScore2);									//sets up a workbook using our spreadsheet file.
    	XSSFSheet Score2Sheet = WBScore2.getSheetAt(0);											//gets the first sheet of the document
    	
    	//Runs to fill the array before we start changing values using the spreadsheet
    	for (int a = 0 ; a < Students.length ; a++) {
    	    Students[a] = new Student();
    	}
    	
    	for (Row row : InfoSheet) 																//for every row in infoSheet
    	{
    		  for (Cell cell : row) 															//for every cell in a row
    		  {
    			  if(cell.getCellType() !=null)	  												//if the cell is not null we will display the value
    			  {
    				  if(cell.getRowIndex()!=0) 												//ignores the first row since it just marks the column.
    				  {
    					  if(cell.getColumnIndex()==0) 											//this column is the ID
    					  {
    						  Students[i].studentid = (int) cell.getNumericCellValue();
    					  }
    					  if(cell.getColumnIndex()==1) 											//this column is the major
    					  {
    						  Students[i].major = cell.getStringCellValue();
    					  }
    					  if(cell.getColumnIndex()==2) 											//this column is the gender
    					  {
    						  Students[i].gender = cell.getStringCellValue();
    						  i++;																//increment when the last entry in the row is committed to the array.
    					  }
    				  }
    			  }
    		  }
    	}																						//end of info sheet loop.
    	WBInfo.close();																			//closes the info workbook object
    	i=0;																					//reset the i value
    	for (Row row : Score1Sheet) 															//for every row in ScoreSheet
    	{
    		  for (Cell cell : row) 															//for every cell in a row
    		  {
    			  if(cell.getCellType() !=null)	  												//if the cell is not null we will display the value
    			  {
    				  if(cell.getRowIndex()!=0) 												//ignores the first row since it just marks the column.
    				  {
    					  if(cell.getColumnIndex()==0) 											//this column is the ID
    					  {
    						  //do nothing because we don't need to. maybe delete this later.
    					  }
    					  
    					  if(cell.getColumnIndex()==1) 											//this column is the score
    					  {
    						  if(WBScore1.getSheetAt(0).getRow(cell.getRowIndex()).getCell(0).getNumericCellValue() == Students[i].studentid )	//verifies the ID matches the current entry in the array
    						  {
    							  Students[i].score1 = (int) cell.getNumericCellValue();
    							  i++;
    						  }
    					  }
    					  
    				  }
    			  }
    		  }
    	}																						//end of loop
    	WBScore1.close();																		//closes the Score1 workbook.
    	i=0;																					//reset the i value
    	for (Row row : Score2Sheet) 															//for every row in re-take score
    	{
    		  for (Cell cell : row) 															//for every cell in a row
    		  {
    			  if(cell.getCellType() !=null)	  												//if the cell is not null we will display the value
    			  {
    				  if(cell.getRowIndex()!=0) 												//ignores the first row since it just marks the column.
    				  {
    					  if(cell.getColumnIndex()==0) 											//this column is the ID
    					  {
    						  while(cell.getNumericCellValue() != Students[i].studentid)
    						  {
    							  i++;															//while the cell value of the row does not equal our array value cycle the array.
    						  }
    					  }
    					  
    					  if(cell.getColumnIndex()==1) 											//this column is the re-take score
    					  {
    							  Students[i].score2 = (int) cell.getNumericCellValue();		// commits the re-take value to the array.
    							  i=0;															//resets the array if the id matches so we can start again with the next row.
    					  }
    				  }
    			  }
    		  }
    	}																						//end of loop
    	WBScore2.close();																		//closes the score2 workbook.
    	//output test

    	/*
    	for (int t = 0; t < Students.length; t++)												//tests the output of the object
    	{
    		Students[t].OutPut();
    	}
    	*/

    	int ScoreTotal = 0;
    	for (int t2 = 0; t2 < Students.length; t2++)											//tests the output of the highest score
    	{
    		ScoreTotal = ScoreTotal + Students[t2].HighestScore();
    	}
    	int Average = ScoreTotal/Students.length;
    	System.out.println("The class averge is:" + Average);				//outputs the class average
    	
    	int [] GirlProgrammerIds = new int [3];
    	int gp=0;
    	for (int t3 = 0; t3 < Students.length; t3++)											//tests for the gender + major id output
    	{
    		Students[t3].GirlProgrammers();														//returns the students id if the student is a female computer science major.
    		if(Students[t3].GirlProgrammers() != 0)
    		{
    			GirlProgrammerIds[gp] = Students[t3].GirlProgrammers();
    			gp++;
    		}
    	}
    	Arrays.parallelSort(GirlProgrammerIds);													//sorts the array from low to high.
    	for(int gpo = 0; gpo < GirlProgrammerIds.length; gpo++)									//loops through the array to output the female computer science majors.
    	{
    		System.out.println(GirlProgrammerIds[gpo]);
    	}
    	
    	
    	//only JSON output from here down-------------------------------------------------------------------------------------------------------------------------------------------
    	
    	JSONObject StudentData = new JSONObject();
    	
    	StudentData.put("id", "jasonkidrowski@gmail.com");
    	StudentData.put("name", "Jason Kidrowski");
    	StudentData.put("average", Average );
    	//StudentData.put("id", "jasonkidrowski@gmail.com");
    	//StudentData.put("name", "Jason Kidrowski");
    	
    	
    	JSONArray studentIds = new JSONArray();
    	studentIds.add(GirlProgrammerIds[0]);
    	studentIds.add(GirlProgrammerIds[1]);
    	studentIds.add(GirlProgrammerIds[2]);

    	StudentData.put("studentIds",studentIds);
    	System.out.println(StudentData);

    	
    	URL url = new URL ("http://54.90.99.192/challenge");
    	HttpURLConnection con = (HttpURLConnection)url.openConnection();

    	con.setRequestMethod("POST");
    	con.setRequestProperty("Content-Type", "application/json; utf-8");
    	con.setRequestProperty("Accept", "application/json");
    	con.setDoOutput(true);

    	String jsonInputString = StudentData.toJSONString();
    	
    	try(OutputStream os = con.getOutputStream()) {
    	    byte[] input = jsonInputString.getBytes("utf-8");
    	    os.write(input, 0, input.length);			
    	}
    	
    	try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) 
    			{
    			    StringBuilder response = new StringBuilder();
    			    String responseLine = null;
    			    while ((responseLine = br.readLine()) != null) 
    			    	{
    			        	response.append(responseLine.trim());
    			    	}
    			    System.out.println(response.toString());
    			}
    }

}
