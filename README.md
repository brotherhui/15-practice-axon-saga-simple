# 15-practice-axon-saga-simple
axon with in memory db, without rabbitmq or mysql. used saga, h2 and with h2 console enabled

This is the workable version and simplest version. Base on the concept in this version, you will understand axon and CQRS better. Then you can easily enhance it with rabbitmq or kafka.

Thanks to https://github.com/AxonFramework/AxonBank, when you finish my example and then go to see AxonBank, you will understand the concept better.
Please read Domain Driver Design for Axon


# Run it
1. start the server
2. access http://localhost:8080/h2 for h2 console
   use the enbamded h2 with below information:
     url: jdbc:h2:mem:pocdb
     username: root
     password: 123456
3. access http://localhost:8080/account/create/AC1001/500 to create first account
4. access http://localhost:8080/account/create/AC1002/550 to create second account
5. access http://localhost:8080/transfer/AC1001/AC1002/150 to transfer money

In the same time, please moniter h2 console for the data change. You may understand better then

# Important part
Each command is asign to an aggregrate id. that is the connector for command and aggregrate.
