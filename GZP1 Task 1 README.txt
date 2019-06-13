ReadMe For GZP1 — GZP1 TASK 1: JAVA APPLICATION DEVELOPMENT 
by Kyle Nyce 6-01-2019

Below is a summary of how to use this text-console program and find code that completes the Assessment's objectives.

====================================================
Objective A - LOG-IN FORM 
====================================================
Below are the details to login to the database for two different users.
User 1
User Login - Kyle
User Password - Kyle

User 2
User Login - Paul
User Password - Paul

To change language/location to Spain Spanish:
1. Comment American Locale object at line 42 of ScheduleAssist.java
2. Uncomment Spain Spanish at line 44 of ScheduleAssist.java

The Login Form ends after it welcomes the logged in user in either American English or Spain Spanish.

====================================================
Objective B - CUSTOMER RECORDS
====================================================
After completing a login - select option 1  to create a customer record or option 2 to  update or delete a customer record.

====================================================
Objective C - APPOINTMENTS
====================================================
After completing a login - select option 3 to create an appointment or option 4 to update or delete an appointment.

====================================================
Objective D - CALENDAR VIEWS
====================================================
After completing a login - select option 5 to view the weekly calendar for the logged in user or select option 6 to view the monthly schedule of the logged in user.

====================================================
Objective E - TIME ZONES
====================================================
Appointment Times are saved in user time zones ( which can be affected by daylight savings time) by using the class ZonedDateTime in DBScheduler.java in the method getAppointmentDate at line line 175. This ZonedDateTime is then converted into an SQL compatible Timestamp before being inserted into the database.

====================================================
Objective F - EXCEPTION CONTROL
====================================================
Exception Control Method #1 - I utilize exception control for SQL statements using the Try-With-Resources mechanism in DBInserter.java line 47. 

Exception Control Method #2 - I utilize try, throw, catch exception control in DBLogin.java at Line 59-68.

Scheduling an appointment outside business hours exception can be verified by using option 3 after login to create an appointment before 8 am or after 17 (using military hours for the timestamp).

Scheduling overlapping appointments can be verified by using option 3 after login to schedule an appointment between the start and end of another appointment .

Entering non-existent or invalid customer data exceptions were handled by checking entered strings/primitive ints to ensure they were not empty and did not contain a space. This can be verified by entering a space or entering an empty entry when asked for information on appointment or customer record creation,updation, or deletion. The mechanism used to throw the exceptions are lambda expressions contained in DBExceptions.java and LambdaException.java.

Entering an incorrect username and password was handled by throwing an exception and can be verified by entering a mismatching username and password at the beginning of the application.

====================================================
Objective G - LAMBDA EXPRESSIONS
====================================================
Lambda Expression #1 - Lambda Expression is used in DBExceptions.java in checkForSpacesAndEmpty at line 34. The inline comment describes that the Lambda makes the coding more compact by storing reusable error messages that are used through-out the entire application.

Lambda Expression #2 - Lambda Expression is used in DBScheduler.java in entrySchedule at line 68. The inline comment describes that the public Lambda expression makes the class more efficient by calling a private function. This allows the private function to be redesigned so that the public function can continue to be used by other classes without code refactoring.

Lambda Expression #3 - Lambda Expression is used in DBScheduler.java in entryUpdate at line 387. The inline comment describes that the public Lambda expression makes the class more efficient by calling a private function. This allows the private function to be redesigned so that the public function can continue to be used by other classes without code refactoring.

====================================================
Objective H - ALERTS
====================================================
If the user logs in and has an appointment within 15 minutes, ScheduleAssist.java will print the appointment using appointmentReminder at line 242. If there is no appointment it will also print out "No upcoming appointments found."

====================================================
Objective I - REPORTS
====================================================
Appointment Types By Month Report : After completing a login - select option 7 to view the number of appointment types by month. You will be prompted for the month that you want to view.

Schedule For Each Consultant Report : After completing a login - select option 8 to view the schedule for each consultant for the current day(today). You will need to create an appointment for each user for today to see this function.

One Additional Report of Your Choice :  After completing a login - select option 9 to view all customers by their ID and their name.

====================================================
Objective J - ACTIVITY LOG
====================================================
Upon completing a login , the user's name and timestamp of login are recorded to a file in the root directory of the project files. This file is created if it does not already exist. The file is called "userLoginFile". Any future logins are appended to the end of the file.


====================================================
Objective K - PROFESSIONAL COMMUNICATION
====================================================
I hope you have found all code, comments, and the included ReadMe organized and focused on achieving the objectives of the "GZP1 — GZP1 TASK 1: JAVA APPLICATION DEVELOPMENT". 