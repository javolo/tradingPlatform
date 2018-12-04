### Dummy FX Application
Simple FX application that allows you to BID or ASK for some amount of currency.

### Design

In the model folder there is the object classes I would create for the trading platform. They are open to include more attributes on them if required.
They are designed to mimic/store the information of a database table.


### Code structure
The application is structured in layers:

A *controller* is responsible for handling the requests and communicating with the layer service for providing a response.
A *service* is responsible for executing the business logic
A *model* represents the classes and elements of the platform.

### Assumptions

1. In the "globalConfig.properties" file you can manipulate the rate of the change or even change the currency conversion.
2. A match order happen when there is an ASK order amount fully match with a BID order. For simplification they need to be the same amount (it can be
extended to partially matched in the future)
3. You can cancel an order only if it hasn´t been matched. Once it´s matched you can´t cancel it.
In the Cancel order functionality I assume that the order is already on the system so I don´t check that already exists.
4. I normally take the UserID from the session after the someone has logged into the application. We record against the session a unique identifier after they log in.
For this example the userID will be created in the global properties.

### Operations that with a DB would be different.

1. Check if there is an order with the same amount
2. Obtain the matched orders
3. Obtain the unmatched orders


