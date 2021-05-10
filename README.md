# Person - address
## How to run :
cd to the root project then type 'mvn clean install' then 'mvn compile' finally 'mvn exec:java -Dexec.mainClass=com.local.main.MainClass'. (Commands are run without the ').
The program will run and display the list of options.

## Database used :
SQLite. Two tables were created address and person, with address having the person_id as a foreign key, to allow a person have multiple addresses.

## Tests used:
JUnit 5 library was used to test the cases, 'mvn test' to run.

## Input & Output:
Input of the user is taken through a Scanner and the output is printed to the user.
