# WinterSupplementCalculator

- Testing Instructions
  - Run app and open postman -> http://localhost:8080/api/mqtt/subscribe?topicId=<MQTT topic ID>
  - MQTT topic ID - you will get it from the Winter Supplement Calculator site
  - each time you refresh the page - you ll get a new id and have to run this post request
  - after this post request, you ll see a log message like
    eg: "Subscribed to topic: BRE/calculateWinterSupplementInput/5bbca270-4705-4d46-8bd0-73c01705775a"
  - go to the site and enter the details in the form and click on submit
  - it should publish input data to the MQTT topic 
  - need to figure out how we ll monitor the output
  - Todo: exception handling, Authentication (can use Keycloak for example), logging framework etc
