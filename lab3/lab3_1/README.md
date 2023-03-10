
# Lab 3 - Multi-layer Application Testing (Spring Boot)

### 3.1 Employee Manager

**a) Identify a couple of examples that use AssertJ expressive methods chaining.**

- AssertJ is a Java library that provides a rich set of assertions and truly helpful error messages and improves test code readability.

*Below you can find the examples from the Employee Manager project:* 
```java
        assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
        
        assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());

        assertThat(found).extracting(Employee::getName).containsOnly("bob");

        assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
```

**b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).**

*Mocking the responses of the repository without using database:*

```java
       @Mock( lenient = true)
       private EmployeeRepository employeeRepository;
       
       public void setUp() {

        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);
        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());

       }
```
**c) What is the difference between standard @Mock and @MockBean?**

In shortcut: @Mock and @MockBean are annotations used in testing frameworks to create mock objects for dependencies of the code under test and main differences between them is the testing framework they are used in and the scope of mocking.

@Mock is an annotation provided by the Mockito library, which is a mocking framework for Java. It creates a mock object of a class or interface and is usually used in unit testing to isolate the code under test and test it in isolation from its dependencies. The scope of the mock object created by @Mock is limited to the test method in which it is used.

On the other hand, @MockBean is an annotation provided by the Spring Framework's testing module. It creates a mock object of a class or interface and registers it in the Spring application context. This makes it suitable for integration testing where we need to test the interaction between different components of the application. The scope of the mock object created by @MockBean is the entire Spring application context.

**d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?**

The "application-integrationtest.properties" file can be used to provide configuration properties specific to the integration tests. When running integration tests, Spring Boot loads the properties from this file in addition to the default application.properties file. This allows us to customize the behavior of our application specifically for integration tests, such as for ex. configuring a connection with real database.

**e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?**

The main differences between these strategies are the level of integration and the scope of the test. @WebMvcTest is the most lightweight and focused strategy, as it only verifies the behavior of the controller. EmployeeRestControllerIT and EmployeeRestControllerTemplateIT are both integration tests that involve multiple components of the application, but the former uses MockMvc for simulating requests, while the latter uses a real REST client. The use of a real REST client adds more complexity to the test, as it involves request and response marshaling/unmarshaling. On the other hand, it provides a more realistic scenario for testing the API's behavior.