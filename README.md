# orderbook-project


Order book project is orderbook management service. Order book project is divided into two modules as below:
1. orderbook-core - This module will hold all the core classes specific to order book service.
2. orderbook-management-service - This module is Springboot web project which exposed REST interface for users.
Service is providing REST interface for user to do below things.

1. User can change the status of orderbook from OPEN to CLOSE state.
2. User can check all the orders from orderbook
3. User can also view only LIMIT orders. This api is exposed for debugging purpose.

Assumption:

1. Service is dealing with 4 instruments and all are pre-configured during boot up from Instrument id 1 to 4 with empty Order book. 
2. Once web application context is loaded predefined orders are loaded against random Instrument Orderbook. This is done to redule the          testing efforts. By default all order books status is OPEN.
3. You can check the orders loaded against an Instrument by running the service locally and hitting the below endpoint.

    http://localhost:8080//instruments/4
    
4. User can change the order book status from OPEN to CLOSE using below POST request.

    http://localhost:8080/instruments/orderbookstatus
    
    post body: 
    Note: Order book status should be OPEN, CLOSE or EXECUTED
    {
	"instrumentId":"2",
	"orderBookStatusType":"CLOSE"
   }
   
 5. If user want to know only LIMIT orders against a Instrument he can use below url
 
    http://localhost:8080//instruments/getlimitorders/1
    
 6. Order book Execution is kept constant for all executions and its quantity is set to 10 and price is 20.00 
 
 #Project Flow:
 
 https://github.com/sachinzinjurke/orderbook-project/blob/master/Untitled%20Diagram.png
 

