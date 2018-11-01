import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

public class H1bCounting {

	private static final String CSV_DELIMITER = ";";

	public static void main( String args[] ){

		//Read the command line input for file name
		String filename = args[0];
		
		//String filename = "input/h1b_input.csv";

		try {
			
           //Function call
			topOccupationCount(filename);

		}catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	
    /*
     * Function to parse input file and store in data structure
     * @param filename - Input filename
     * @throws FileNotFoundException, IOException
     */
	public static void topOccupationCount(String filename) throws FileNotFoundException,  IOException{ 

		Map<String, Integer>  occupationMap = new TreeMap<String, Integer>();
		Map<String, Integer>  stateMap = new TreeMap<String, Integer>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		try{

			String line = "";

			//Getting the header
			String line1 = br.readLine();

			
			String[] header = line1.split(CSV_DELIMITER); 
			int socCodeIndex = 0;
			int socNameIndex = 0;
			int statusIndex = 0;
			int stateIndex = 0;
			int count = 0;

			//Iterate through headers to get index of required columns - Status, Work State, Occupation Name
			for (int i = 0; i < header.length; i++){

				if(header[i].contains("SOC_CODE")){
					socCodeIndex = i;
				}

				if(header[i].contains("SOC_NAME")){
					socNameIndex = i;
				}

				if(header[i].contains("STATUS")){
					statusIndex = i;
				}

				if(header[i].contains("WORKSITE_STATE")){
					stateIndex = i;
				}
			}


			//System.out.println("socCodeIndex" + socCodeIndex + " " + socNameIndex + " "+ statusIndex +" "+ stateIndex);
			
			//Parse the file 
			while ((line = br.readLine()) != null) 
			{

				//Remove unwanted strings matching regex before tokenization example &AMP;
				if(Pattern.matches(".*&[a-zA-Z]{2,3};.*",line)){
            		//System.out.println("here");
        			line = line.replaceAll("&[a-zA-Z]{3};","");
        		}
				
				line = line.replaceAll("\"", "");

				//Tokenize the read line and stored in arrays
				String[] applicantDetail = line.split(";");
				
				//System.out.println("" + applicantDetail[0] +" "+ applicantDetail.length);
                
				//Store in map only if certified
				if(("CERTIFIED").equals(applicantDetail[statusIndex])){

					
					count++; //Count to get total certified
					
					//String oocupation = applicantDetail[socNameIndex].replaceAll("^\"|\"$", "");
					
					//Count for Occupation
					if(occupationMap.containsKey(applicantDetail[socNameIndex])){

						occupationMap.put(applicantDetail[socNameIndex],occupationMap.get(applicantDetail[socNameIndex])+1);

					}else{
					
						occupationMap.put( applicantDetail[socNameIndex], 1);
						
					}


					//Count for State
					if(stateMap.containsKey(applicantDetail[stateIndex])){

						stateMap.put(applicantDetail[stateIndex],stateMap.get(applicantDetail[stateIndex])+1);

					}else{

						stateMap.put(applicantDetail[stateIndex], 1);
					}	


				}


			}

			//Function call to sort map by value 
			occupationMap =  sortByValue(occupationMap); 
			stateMap = sortByValue(stateMap);
			
			//Header to output file
			String headerOutput = "TOP_OCCUPATIONS;NUMBER_CERTIFIED_APPLICATIONS;PERCENTAGE";
			fileWriter( "top_10_occupations.txt", occupationMap, count, headerOutput );

			headerOutput = "TOP_STATES;NUMBER_CERTIFIED_APPLICATIONS;PERCENTAGE";  
			fileWriter( "top_10_states.txt", stateMap, count, headerOutput ); 


   		}catch(Exception e){
			e.printStackTrace();

		}

		finally{
			br.close(); 
		}
	}


	/**
	 * Function to sort map by values in descending order
	 * @param hm
	 * @return Hashmap sorted by count descending order
	 */
	
	//Method for sorting the TreeMap based on values
	  public static <K, V extends Comparable<V>> Map<K, V> 
	  sortByValue(final Map<K, V> map) {
	    Comparator<K> valueComparator = 
	             new Comparator<K>() {
	      public int compare(K k1, K k2) {
	        int compare = 
	              map.get(k2).compareTo(map.get(k1));
	        if (compare == 0) 
	          return 1;
	        else 
	          return compare;
	      }
	    };
	 
	    Map<K, V> sortedByValues = 
	      new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
	  }
	
	
	/**
	 * Function to write Map contents to file
	 * @param filename - Output file name
	 * @param map - map to write in output file
	 * @param count - total count of certified applicants
	 * @param headerString - Header for output file
	 * @throws IOException
	 */
	public static void fileWriter(String filename, Map<String, Integer> map, int count, String headerString) throws IOException{

		int fileLength = map.size() < 10 ? map.size() : 10;
		File fout = new File("output/"+filename);
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {

			int counter = 0; //Counter for top 10 
			fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));

			bw.write(headerString);
			bw.newLine();

			Iterator it = map.entrySet().iterator();
			while(it.hasNext() && counter < fileLength){

				Map.Entry pair = (Map.Entry)it.next();
				Integer temp = (Integer) pair.getValue();
				Integer tempCount = (Integer)count;
				
				//Calculate percentage
				//Double percentage = ((temp * 100.0 ) / tempCount);
				Double percentage = (double) Math.round((temp * 100 / tempCount ) * 10) / 10;
				
				//String oocupation = ((String) pair.getKey()).replaceAll("^\"|\"$", "");
				
				bw.write(pair.getKey() + ";" + pair.getValue() + ";" + percentage  + "%");
				bw.newLine();
				counter++;
			}


		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch(IOException e){

			e.printStackTrace();
		}
		finally{

			bw.close();
		}


	}



}
