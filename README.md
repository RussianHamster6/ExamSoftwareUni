# ExamSoftwareUni
A solution designed for my CW1 and potentially CW2 for my Object Orientated Programming module at uni

To ensure that this solution builds there is a couple steps to take. 

1. to Ensure that it builds properly make sure your Run/Debug configurations have been configured with the following VMSetting Equivalents:
--module-path
C:\Users\declan.rhodes\Desktop\javafx-sdk-11.0.2\lib 
--add-modules
javafx.controls,javafx.fxml

where your module-path is the location of the javafx sdk lib file on your machine. 

2. Ensure that the appropriate packages are still present in the project structure > project settings > libraries 
There should be:
	lib - This should contain your javafx-sdk-11.0.2\lib as a class and source
	sqlite-jdbc-3.32.3.6 - This should be the maven install for this package
	
following that the solution should build. The default user should have a login ID of 1 and be a base user for you to create any questions, tests and any other users with. If you want to do a test you need to make a student
and assign them to a test with questions to take. 

Just in case there's any confusion around how to access the edit functionality on the tables double clicking a table entry will open the detailed view of it. I think it's fairly self explanatory but I think there's a little ambiguity. 

Thanks 
Declan Rhodes
