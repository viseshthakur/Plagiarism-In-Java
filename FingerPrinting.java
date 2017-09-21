import java.util.*;
import java.lang.*;
import java.io.*;

class FingerPrinting extends FileNotFoundException
{
	/*
	Input : This function accepts string as input and anagram value which to be used on string to  partition

	Function : If anagram is 4 the string is divided into four chars each time in a sequence of string

	output: These words is appended to Set List
	*/
	public Set<String> anagramComb(String str1,int anagram){
		Set<String> combi = new HashSet<String>();			//set list defined
		for(int i=0;i<str1.length()-anagram+1;i++){			//condition to break string till anagram value
			String s="";
			for(int j=i;j<anagram+i;j++){
				s=s+str1.charAt(j);
			}
			combi.add(s);				//append every word to Set
		}	
		return combi;
	}

	/*
	Input : It accepts ArrayList which has set of words divided on anagarm and also anagram value

	Functions : For every word ascii value of each character of word is mutpliplied with 2 power of anagram - j -1 
				is summed and appended to a new arraylist integer

	Output : ArrayList containing all the values of words .
	*/
	public ArrayList<Integer> valueOfCombination(ArrayList<String> strcomb1,int anagram){
		ArrayList<Integer> valcomb1=new ArrayList<Integer>();
		int sum=0;

		for(int i=0;i<strcomb1.size();i++){
			sum=0;
			for(int j=0;j<(strcomb1.get(i).length());j++){
				char character = strcomb1.get(i).charAt(j); // This gives the character 'a'
				int ascii = (int) character;
				sum=(int)(sum+(  (ascii)* ((Math.pow(2,anagram-j))-1)));		//calculation of fp for every word

			}
			valcomb1.add(sum);
		}
		return valcomb1;
	}

	public static void main(String[] args) 
	{
		System.out.println("--------------------------Finger Printing-------------------------------");
		System.out.println("Anagram for FingerPrinting=4");
		int anagram=4;
		Scanner input = new Scanner(System.in); 
		String path = args[0];							//args[0] is path which need to be given while running

		File folder = new File(path);					
		File[] listoffiles=folder.listFiles();			//Files are stored in form of List from given path and folder

		ArrayList<String> fileNameWithPath=new ArrayList<>(); 		//List to store file names with path
		ArrayList<String> fileNames=new ArrayList<>();				//used to store file names

		for(int i=0;i< listoffiles.length;i++){				//for every file in list it is checked for .txt format
  			if(listoffiles[i].isFile()){		
    		String temp = listoffiles[i].getName();
    			if(temp.contains(".txt")&&!(temp.contains("output"))){		
      			fileNameWithPath.add(path+"/"+temp);					//only files with .txt are stored in file names with path list
      			fileNames.add(temp);									//.txt file names are stored in file names list
 				}
  			}
		}

		System.out.print("\t\t");				
		for(int i=0;i<fileNames.size();i++){				//prints first line of matrix with file names
			System.out.print(fileNames.get(i)+"\t");
		}

		for(int i=0;i<fileNameWithPath.size();i++){			//Every file is checkedwith other files and plagiarism is calculated
			System.out.println("\n");
			System.out.print(fileNames.get(i)+"\t");
			for(int j=0;j<fileNameWithPath.size();j++){
				if(i==j){									//if two files are same output or bagofwords result is 0
					System.out.print("0.00\t\t");
				}
				else{

					String f1=fileNameWithPath.get(i); 
					File file1 =new File(f1);
					String string1="";
					try{
						Scanner input1=new Scanner(file1);
						while(input1.hasNextLine()){			//file 1 is read and converted to string
							string1+=input1.nextLine();
							string1+=" ";
						}
					}	
					catch (FileNotFoundException element){	//exception is raised if no file found
						System.err.println("NoData");
					}
				
					String f2=fileNameWithPath.get(j);
					File file2 =new File(f2);
					String string2="";
					try{
						Scanner inp=new Scanner(file2);
						while(inp.hasNextLine()){			//file 2 is read and converted to string
							string2+=inp.nextLine();
							string2+=" ";
						}
					}
					catch (FileNotFoundException element){		//exception is raised if no file found
						System.err.println("NoData");
					}


					String str1=string1.replaceAll("\\s+",""); //removes all spaces
					str1.toLowerCase();							//makes string to lower case
					String str2=string2.replaceAll("\\s+","");	//removes all spaces
					str2.toLowerCase();							//makes string to lower case

					FingerPrinting fp1=new FingerPrinting();	//class object

					Set<String> stringComb1=fp1.anagramComb(str1,anagram);	//string is divided on anagram value and stored in set
					ArrayList<String> strcomb1= new ArrayList<String>();
					strcomb1.addAll(stringComb1);							//set was used to remove any duplicates and now converted to arraylist

					FingerPrinting fp2=new FingerPrinting();	//class object

					Set<String> stringComb2=fp2.anagramComb(str2,anagram);	//string is divided on anagram value and stored in set
					ArrayList<String> strcomb2= new ArrayList<String>();		
					strcomb2.addAll(stringComb2);							//set was used to remove any duplicates and now converted to arraylist


					ArrayList<Integer> valcomb1=new ArrayList<Integer>();
					valcomb1=fp1.valueOfCombination(strcomb1,anagram);			// for every word anagram is calculated based on ascii value of characters of  word and values are stored in arrayList
					ArrayList<Integer> valcomb2=new ArrayList<Integer>();
					valcomb2=fp2.valueOfCombination(strcomb2,anagram);			// for every word anagram is calculated based on ascii value of characters of  word and values are stored in arrayList

					ArrayList<Integer> count=new ArrayList<Integer>();			//A new arraylist count is taken
					for(int k=0;k<valcomb1.size();k++){
						if(valcomb2.contains(valcomb1.get(k))){					//for every element in arraylist 1 check wether same element is present in arraylist2 and add that value to new count list
							count.add(valcomb1.get(k));
						}
					}

					int sizeofcount=count.size();			//size of count list 
					int sizeofvalcomb1=valcomb1.size();		//size of arraylist 1
					int sizeofvalcomb2=valcomb2.size();		//size of arraylist 2

					double result=((sizeofcount*2.0)/(sizeofvalcomb1+sizeofvalcomb2))*100.00;	//finger printing is calculates based on sizes and no of files
					System.out.print(String.format("%.2f",result)+"\t\t");
				}
			}
		}
	}
}