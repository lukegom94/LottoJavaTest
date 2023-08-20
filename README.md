# LottolandJavaTest
Solution provided by candidate **Lucas Gomez-Rodero**.

![imagen](https://github.com/lukegom94/LottoJavaTest/assets/83473675/c5150199-a34a-4ff3-b545-750dc0f5a09b)


*Please, beware that this is not production-ready code, just an effort to display the way I think about software development*.


# 1. Solution's approach:
In the belief that any professional development should be easily **scalable**, I have approached the solution to scale in 3 ways:
- The player's strategy should be easy to modify.
   - Solution: *Strategies are defined in .properties file (upgradable to config-repo)*.
     
- It should be easy to substitue the memory storage of results by a database storage.
   - Solution: *creation of 'Repository' interface that can have mulitple implementations*.
- The application should allow adding other games such as, for example, 'Flip the Coin'.
   - Solution: *in real-world application I would develop Game interface and 2-Players_Game abstract class (not done in this assignment)*.
 
# 2. Application deployment:
For the sake of simplicity, *target .jar*, *Dockerfile* and *docker-compose.yml* files have been added so that the application can be easily deployed with command **docker-compose up**.

Game simulator can be accessed in address: http://localhost:8080/game


# 3. Code overview:
Clean architecture approach:

![imagen](https://github.com/lukegom94/LottoJavaTest/assets/83473675/0ee17c28-d1e4-4306-8ba9-13c31e021e55)

Some noticeable aspects of the code are the use of Thymeleaf for the page views and the separation of Repository layer in Game and GameStats.

# 3.1. Game simulation logic:
In a distributed-system architecture, the simulation of games could be provided by an independent microservice.

The RPSGameSimulator class can simulate different strategies for players. I have preferred developing a custom simulation logic using numbers (0,1,2), instead of copying and pasting a solution from the internet for having some fun :).

# 3.2. Testing:
Please, beware this is far from production-ready code, and this project clearly lacks testing. I would never push to production a project with so little testing.

Some testing has been performed in class *RPSGameServiceTest*, using mocks and an OutputCaptureExtension to ensure logs are working properly when Exception is thrown. This is just a way of showing what I would consider some good testing practices, but the class is not fully tested so far.

![imagen](https://github.com/lukegom94/LottolandJavaTest/assets/83473675/1448451f-cf1e-4215-99e3-f3da6585332d)

In a real-life project I would test most method in the project and ensure logic works properly and any irregular situation is covered. Integration tests should be added as well.









