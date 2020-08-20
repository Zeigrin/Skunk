
public class Student {
	int studentid;
	String major;
	String gender;
	int score1;
	int score2;
	
	public void OutPut()															//used to ensure and check data accuracy.
	{
		System.out.println(studentid);
		System.out.println(major);
		System.out.println(gender);
		System.out.println(score1);
		System.out.println(score2);
		System.out.println("---------------------------------------");				//some formatting to separate entries.
	}
	
	public int HighestScore()														//compares the two scores of the student object and returns the higher one. 
	{
		int HighestScore = 0;
		if(score1 > score2)
		{
			HighestScore = score1;
		}
		if(score1 < score2)
		{
			HighestScore = score2;
		}
		//System.out.println(HighestScore);
		return HighestScore;
	}
	
	public int GirlProgrammers()													//determines if the student is both a female and a computer science major.
	{
			int GirlProgrammerID = 0;
			if(gender.contentEquals("F") && major.contentEquals("computer science"))
			{
				GirlProgrammerID = studentid;
			}
			return GirlProgrammerID;

	}

}


