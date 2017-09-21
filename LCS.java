import java.util.*;
import java.io.*;

class LCS extends FileNotFoundException
{
	/*
	Input : This fucntion accepts String

	Function : it performs operations on string and stores each different combination in a set

	Output : returns set list which has different combinations of sequences
	*/
	public Set<String> sequencesOfString(String string1){
		String string=string1.replaceAll("\\s+","");
		String m;
		Set<String> combi = new HashSet<String>();			
		for(int i=0;i<string.length();i++){
			m="";
			for(int j=0;j<i;j++){
				m=m+string.charAt(j);
				combi.add(m);
			}
		}
		for(int i=0;i<string.length();i++){
			m="";
			for(int j=i;j<string.length();j++){
				m=m+string.charAt(j);
			}
			combi.add(m);
		}
		for(int i=0;i<string.length();i++){
			m="";
			m=m+string.charAt(i);
			combi.add(m);
		}
	return combi;
	}

	/*
	Input : It accepts arraylist of different sequences of string 1 and also accepts string 2

	Function : Every sequences of string1 are iterated over string2 if found the length of that string is calculated and stored in arraylist of integer

	Ouptut : Output is ArrayList which has counts of lengths of similar sequences in both strings
	*/
	public ArrayList<Integer> highestCopied(ArrayList<String> combinations,String string2 ){
		ArrayList<Integer> countofCopies=new ArrayList<Integer>();
		int length;
		for(int i=0;i<combinations.size();i++){
			String str=combinations.get(i);
			if(string2.contains(str)){
				length=str.length();
				countofCopies.add(length);
			}
		}
		Collections.sort(countofCopies);
	return countofCopies;
	}
	
	/*
	Input : Accepts highestcount from counts List and lengths of two strings

	Function : It calculates the lcs 
				highestcount* no of files /(lengths of strings )

	output: returns the percent value calculated
	*/
	public double lcsCalculation(int highestCount , int length1, int length2){
		double lcs=((highestCount*2.0)/(length1+length2))*100;
		return lcs;
	}
	public static void main(String[] args) 
	{
		System.out.println("--------------------------LCS-------------------------------");
		double lcs;
		String string;

		Scanner input = new Scanner(System.in);
		String path = args[0];				//path is passed while running
		File folder = new File(path);
		File[] listoffiles=folder.listFiles();	//list of files are stored from folder

		ArrayList<String> fileNameWithPath=new ArrayList<>();		//arraylist for file with path
		ArrayList<String> fileNames=new ArrayList<>();				//arraylist for file names 

		for(int i=0;i< listoffiles.length;i++){
			if(listoffiles[i].isFile()){
				String temp = listoffiles[i].getName();
				if(temp.contains(".txt")&&!(temp.contains("output"))){		//every file from lisoffiles checked for .txt 
		  			fileNameWithPath.add(path+"/"+temp);					//if txt found path and also file name is stored in lists
		  			fileNames.add(temp);
				}
			}
		}

		System.out.print("\t\t");				//matrix printing of first line
		for(int i=0;i<fileNames.size();i++){
			System.out.print(fileNames.get(i)+"\t");
		}

		for(int i=0;i<fileNameWithPath.size();i++){
			System.out.println("\n");
			System.out.print(fileNames.get(i)+"\t");
			for(int j=0;j<fileNameWithPath.size();j++){
				if(i==j){					//if both files are same we print it as 0.00 simply
					System.out.print("0.00\t\t");
				}
				else{
					String f1=fileNameWithPath.get(i);				//file is read and checks for all data
					File file1 =new File(f1);
					String string1="";
					try{
						Scanner input1=new Scanner(file1);
						while(input1.hasNextLine()){				//file .txt is converted to string
								string1+=input1.nextLine();
								string1+=" ";
						}
					}	
					catch (FileNotFoundException element){			//exception is raised if file not found
							System.err.println("NoData");
					}
					
					String f2=fileNameWithPath.get(j);
					File file2 =new File(f2);				//file is read and checks for all data
					String string2="";
					try{
						Scanner inp=new Scanner(file2);
						while(inp.hasNextLine()){					//file .txt is converted to string
							string2+=inp.nextLine();
							string2+=" ";
						}
					}
					catch (FileNotFoundException element){			//exception is raised if file not found
						System.err.println("NoData");
					}

					LCS lcs1= new LCS();							//class object is created

					Set<String> combi=lcs1.sequencesOfString(string1);		// the permutations of string are inserted into set
					ArrayList<String> combinations=new ArrayList<String>();
					combinations.addAll(combi);								//set was used to remove dups and this set collection is appened to arraylist

					ArrayList<Integer> countofCopies = new ArrayList<Integer>();
					countofCopies=lcs1.highestCopied(combinations,string2);		//for every combination in arraylist is checked or iterrated on the string 2 if found the length of equences is calculated and appended to set list

					int highestCount=countofCopies.get(countofCopies.size()-1);	//highest count copied is calculated by get and size funtion of arraylist
					string=string1.replaceAll("\\s+","");
					int length1=string.length();		// length of string 1 is calculated after removing spaces
					string=string2.replaceAll("\\s+","");
					int length2=string.length();		// length of string 2 is calculated after removing spaces

					if(length1==0 && length2==0){		// if length1 and length2 are 0 then print 100 directly
						System.out.print("100\t\t");
					}
					else{
						if(countofCopies.size()==0){	//if sequences count copied is zero print 100 directly
							System.out.print("100\t\t");
						}
						else{
							lcs=lcs1.lcsCalculation(highestCount,length1,length2);	// highest count and length1 , length2 are send to lcs calculation to calculate result
							System.out.print(String.format("%.2f",lcs)+"\t\t");		// value is printed here
						}
					}
				}
			}
		}
	}	
}