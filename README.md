# H1B Data Analytics



### Problem
A newspaper editor was researching immigration data trends on H1B(H-1B, H-1B1, E-3) visa application processing over the past years, trying to identify the occupations and states with the most number of approved H1B visas. She has found statistics available from the US Department of Labor and its Office of Foreign Labor Certification Performance Data. But while there are ready-made reports for 2018 and 2017, the site doesnâ€™t have them for past years.
The aim of the challenge was to develop the mechanism to analyze historic data and determine "Top 10 Occupations" and "Top 10 States" for certified visa applicants.

### Approach
Used Java to solve the challenge. Below is the summary of the approach:
1. Read the records from input file h1b_input.csv line by line. The index of columns is obtained from the header of the file. 
2. For applicants with status "CERTIFIED", occupation and working state are saved in Map data structure as key and their corresponding count as values and simultaneously, total count of certified applicants is calculated. While iterating through the records of certified applicants, it is added to map if not present else the count is incremented by 1 for the existing one.
3. The occupation string is modified for removing leading and trailing quotes. 
4. The Map for occupation and states are sorted by values in descending order to get top 10 occupations and states. 
5. If the Maps has less than 10 records then all records are written to output file using buffered write; else top 10 records are written to output files. While writing to the output files, the percentage of count of occupations/states of certified applicants over total count of applicants is calculated. 
6. Finally, at the end of the inputs and output files, the corresponding streams are closed. 


### Instructions to Run 
* The experimental setup includes:
	* Programming Language: Java 1.8.
* Download or Git clone the directory to local system. 
* Run the run.sh script to compile and run the program. 
* The input csv file with name "h1b_input.csv" is placed in input folder and output files are saved in output folder. 


### Assumptions 
* For future use, the input file needs to be placed in input directory with the same name with all required information fields and header names for program to run successfully without modification. 






