# OpCl-JAVA-P5
A local web API, reading a specific json files to process informations using Endpoints. This API respects SOLID princips and REST architecture.

##  Run API

Run jar in `target/`.  
Default Controller URL `http://localhost:8080/`.   
You can change port in `src/main/resources/application.properties`  

## Json input file
> example  

datafile.json :

``` 
  {
    "persons": [
        { "firstName":"John", "lastName":"Boyd", "address":"1509 Culver St", "city":"Culver", "zip":"97451", "phone":"841-874-6512", "email":"jaboyd@email.com" },
        { "firstName":"Jacob", "lastName":"Boyd", "address":"1509 Culver St", "city":"Culver", "zip":"97451", "phone":"841-874-6513", "email":"drk@email.com" },
        { "firstName":"Tenley", "lastName":"Boyd", "address":"1509 Culver St", "city":"Culver", "zip":"97451", "phone":"841-874-6512", "email":"tenz@email.com" },
        { "firstName":"Roger", "lastName":"Boyd", "address":"29 15th St", "city":"Culver", "zip":"97451", "phone":"841-874-6512", "email":"jaboyd@email.com" },
        { "firstName":"Felicia", "lastName":"Boyd", "address":"29 15th St", "city":"Culver", "zip":"97451", "phone":"841-874-6544", "email":"jaboyd@email.com" },
      ],
    "firestations": [
	{ "address":"1509 Culver St", "station":"3" },
        { "address":"29 15th St", "station":"2" },
	],
    "medicalrecords": [
        { "firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984", "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"] },
        { "firstName":"Jacob", "lastName":"Boyd", "birthdate":"03/06/1989", "medications":["pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"], "allergies":[] },
        { "firstName":"Tenley", "lastName":"Boyd", "birthdate":"02/18/2012", "medications":[], "allergies":["peanut"] },
        { "firstName":"Roger", "lastName":"Boyd", "birthdate":"09/06/2017", "medications":[], "allergies":[] },
        { "firstName":"Felicia", "lastName":"Boyd","birthdate":"01/08/1986", "medications":["tetracyclaz:650mg"], "allergies":["xilliathal"] },
}
```  
  
## Endpoints

### GET  

**http://localhost:8080/firestation?stationNumber={station_number}**
  
>This url should return a list of people covered by the corresponding fire station.
>So if the station number = 1, it must return the inhabitants covered by station number 1. The list
>must include the following specific information: first name, last name, address, telephone number. Furthermore,
>it must provide a count of the number of adults and the number of children (any individual aged 18 or over
>less) in the service area.  
  
**http://localhost:8080/childAlert?address={address}**
  
>This url should return a list of children (any individual aged 18 or younger) living at this address.
>The list should include the first and last name of each child, their age and a list of others
>household members. If there is no child, this url may return an empty string
  
**http://localhost:8080/phoneAlert?firestation={firestation_number}**
  
>This url must return a list of the telephone numbers of the residents served by the fire station.
>firefighters. We will use it to send emergency text messages to specific households.
  
**http://localhost:8080/fire?address={address}**
  
>This url must return the list of inhabitants living at the given address as well as the number of the barracks
>of firefighters serving it. The list should include name, phone number, age and background
>medical (drugs, dosage and allergies) of each person
  
**http://localhost:8080/flood/stations?stations={list_of_station_numbers}**
  
>This url should return a list of all homes served by the barracks. This list should include the
>people by address. It must also include the name, telephone number and age of the inhabitants, and
>include their medical history (medications, dosage and allergies) next to each name.
  
**http://localhost:8080/personInfo?firstName={firstName(not_required)}&lastName={lastName}**
  
>This url must return the name, address, age, email address and medical history (drugs,
>dosage, allergies) of each inhabitant. If more than one person has the same name, they must
>all appear.
  
**http://localhost:8080/communityEmail?city={city}**
  
>This url must return the email addresses of all the inhabitants of the city
  
### ADD / PUT / DELETE

You must send a Json body :

**/person**

```
{ "firstName":"Didier", "lastName":"Jean", "address":"1509 Culver St", "city":"Culver", "zip":"97451", "phone":"2000006", "email":"jaboyd@email.com" }
```  
To create / modify or delete a person.

**/firestation**

```  
{ "address":"29 15th St", "station":"2" }
```  

*There is no 'put' for firestation*  
To create / modify or delete a firestation.

**/medicalRecord**

```  
{ "firstName":"Didier", "lastName":"Jean", "birthdate":"03/06/1984", "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"] }
```  

To create / modify or delete a medicalRecord.
  
    
---------------------------------------
*Thank You.  
CLemDv*
