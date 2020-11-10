# OpCl-JAVA-P5
A local web API, reading a specific json files to process informations using Endpoints.

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
  
## Endpoints Type
