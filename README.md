# ExamSoftwareUni
A solution designed for my CW1 and CW2 for my Object Orientated Programming module at uni

To ensure that this solution builds there is a couple steps to take. 

0. Go into the modify options dropdown and press add new configuration. 

1. Ensure that the java SDK is set to Java 16 and the Module is set to ExamsSoftwareUni. See screenshot for all the correct configuration as related to my machine

![alt text](https://github.com/RussianHamster6/ExamSoftwareUni/blob/main/.idea/coconut.png)

2. to Ensure that it builds properly make sure your Run/Debug configurations have been configured with the following VMSetting Equivalents:
--module-path
C:\Users\declan.rhodes\Desktop\javafx-sdk-11.0.2\lib 
--add-modules
javafx.controls,javafx.fxml

where your module-path is the location of the javafx sdk lib file on your machine. 

3. Ensure that the appropriate packages are still present in the File > project structure > project settings > libraries 
There should be:
	lib - You may need to delete the old lib and revconfigure for your local version of JavaFX. I utilised v11.0.2
	com.github.gotson:sqlite-jdbc:3.32.3.6 - This is the url for the Maven for my sqlite package
	
following that the solution should build. The default user should have a login ID of 1 and be a base user for you to create any questions, tests and any other users with. If you want to do a test you need to make a student
and assign them to a test with questions to take. 

Just in case there's any confusion around how to access the edit functionality on the tables double clicking a table entry will open the detailed view of it. I think it's fairly self explanatory but I think there's a little ambiguity. 

When entering a multiple choice question ensure that the answer is either A,B,C or D and that the question description contains the answers for all of the letters eg. 
Question Description: What is 2 + 2? A-1, B-2, C-3, D-4 
Answer: B

Thanks 
Declan Rhodes
