# networkSimulator
A simple network simulator application to show the connection between two computers with the help of their connection strength and the repeaters.

Steps to Run the Application 

1. Install gradle from the given url https://gradle.org/install/
2. Install required version of java 
3. clone the repo 
4. go to the folder and run the command
    i)   gradle clean build 
    ii)  go the build folder and run the command java -jar {file_name}.war
    iii) {file_name} - the name of the file in the build folder
5. The application will be running on the port localhost:8080
6. run the command in your terminal "curl --request POST --data-binary "@command{COMMAND_NUMBER}.txt" --header "Content-Type: text/plain" --url http://localhost:8080/ajiranet/process"