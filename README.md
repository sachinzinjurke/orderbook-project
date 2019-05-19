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
    
 6. Below are stats url for order book:
 
 	User can get all the valid and invalid orders related stats
 	http://localhost:8080/instruments/getvaliditystats/1
	
	User can get the order book stats like biggest order, smallest order
	http://localhost:8080/instruments/getorderbookstats/1
    
 6. Order book Execution is kept constant for all executions and its quantity is set to 10 and price is 20.00 
 
 #Project Flow:
 
 Step-1:
 User will submit the Order book close request through api given above. Depending upon the state of Order Book service will update the     state of order book. If user marks the state to CLOSE, Instrument will be added for order book processing flow.
 
 Step-2:
 Upon submission of Orderbook status, service create a Execution context for order book processing. Execution context wraps Instrument and Execution for performing further processing. Execution context is added to Blocking Queue.
 
 Step-3:
 OrderBookProcessingThread is continously listening to this blocking queue. Whenever it finds any data in queue it will poll the data in out case it's ExecutionContext. Execution context is passed to set of rules. Each rule is doing specific functionality to the order book processing. There are 5 such rules as below.
 
 OrderValidityMarkerRule - Marks order validity on order price. Only LIMIT orders are taken care by this rules.By default all MARKET orders are marked invalid for Orderbook processing.
 
 ValidBookDemandRule - Derives valid demand for the order book.
 
 ExecutionAcceptanceRule - Decides whether new execution for the same orderbook should be accepted or not.
 
 ExecutionQuantityLinearDistributionRule - If the execution quantity is less than the valid demand this rules comes into picture. It will divide the quantity to all orders by weighted ratio and further decides orderbook execution.
 
 OrderBookExecutedMarkerRule - Marks the Order book as EXECUTED if previous rules decides that orderbook processing is  completed.
 ValidBookDemandRule
 
 
 Here is project flow diagram:
 
 
 https://github.com/sachinzinjurke/orderbook-project/blob/master/Orderbook%20Management%20Service
 
 #What is pending :
 
 1.Exception handling
 2. Unit Testing
 
 
 

