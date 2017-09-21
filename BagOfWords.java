import java.util.*;
import java.io.*;

class BagOfWords extends FileNotFoundException
{
	/*
	Inputs : This function accepts String as input.

	Function: This function removes all the special characters and appends it with no space
		      It is again converted to lower case
		      And string is splitted into words by passing " " delimeter in regex

	Output : This function returns HashMap a key value pair which has words as keys and 
			 value as frequency of times word appeared	
	*/
	public HashMap<String,Integer> stringToDictionary(String string1){
		int count=0;
		ArrayList<String> arrlist1=new ArrayList<String>();

		string1= string1.replaceAll("[^\\w\\s]","").toLowerCase(); 		//String is replaced with special charac and converted to lower case
		for(String split : string1.split(" ")){							//string is splitted to words and stored in arrayList
			arrlist1.add(split);
		}

		HashMap<String,Integer> map=new HashMap<String,Integer>();
		int length=arrlist1.size();

		for (String str: arrlist1){				//every word is checked for dups and inseerted into hashmap
           	//System.out.println(str);
            if(map.containsKey(str)){
                count=map.get(str);
                count=count+1;				//if word already exists in hashmap count is incremeneted
                map.put(str,count);
        	}
            else{
                map.put(str,1);				//else word is inserted with value 1
            }
        }
   	return map;
	}

	/*
	Input : This function accepts two hashmaps (tor strings converted to hashmap with words)

	Function : This function compares first hashmap with second
			   If words in firstmap are in second hashmap it multiplies the values of that word 
			   from both hashmap and sum it till last element

	Output : It returns the  sum value of similar words frequencies
	*/
	public static double dotProduct(HashMap<String,Integer> mapof1,HashMap<String,Integer> mapof2){
		double dotpro=0;
		Set s=mapof1.entrySet();
		for (String key : mapof1.keySet()){			//for every key in hashmap1 this key is checked with other hashmap and values are multiplied
    		if(mapof2.containsKey(key)){
    			dotpro=dotpro+(mapof1.get(key)*mapof2.get(key));
    		}
		}
	return dotpro;
	}

	/*
	Input : It accepts input hasmap or a single dictionary

	Function : For every key in hashmap its value is powered to 2 and summed until last element
			   This sum value is squared and returned

	Output : It returns the vector value of hashmap
	*/
	public double vectorCalculation(HashMap<String,Integer> mapof1){
		double vec=0.0;
		Set s=mapof1.entrySet();
		for (String key : mapof1.keySet()){		
			vec=vec+Math.
			pow(mapof1.get(key),2);    			//calculating power of each value and summing
		}
		vec=Math.sqrt(vec);						//squaring summed value
		return vec;
	}

	public static void main(String[] args) {
		System.out.println("--------------------------Bag Of Words-------------------------------");
		double bag_of_words=0.0;
		double dotpro;

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

					HashMap<String,Integer> mapof1=new HashMap<String,Integer>();		//hashmap objects to calculate frequencies
					HashMap<String,Integer> mapof2=new HashMap<String,Integer>();

					BagOfWords bow1=new BagOfWords();			//class object to access methods of class
					mapof1=bow1.stringToDictionary(string1);	//now string is converted to hashmap (dictionary)

					BagOfWords bow2=new BagOfWords();			//class object to access methods of class
					mapof2=bow2.stringToDictionary(string2);	//now string is converted to hashmap (dictionary)

					dotpro=BagOfWords.dotProduct(mapof1,mapof2);	//static method called by class name it calculates dot pro similar dictionary words in both maps are multiplied by frequencies

					double vec1=bow1.vectorCalculation(mapof1);		//vector of dictionary 1 is calculated
					double vec2=bow2.vectorCalculation(mapof2);		//vector of dictionary 2 is calculated 

					bag_of_words=(dotpro/(vec1*vec2))*100.0;		//dot pro / vector1*vector2 gives copied amount of words		//
					System.out.print(String.format("%.2f",bag_of_words)+"\t\t");
				}
			}
		}
	}	
}

