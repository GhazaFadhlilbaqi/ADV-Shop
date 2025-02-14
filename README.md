# Advanced Programming Tutorial & Assignments
## Muhammad Ghaza Fadhlilbaqi - 2306173321
### Reflection 1
> You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code.

I've applied several clean code principles to improve readability and maintainability. I use ```meaningful names``` for functions and variables, making their purpose clear. My functions follow the ```Do one thing rule```, keeping them focused and modular. I’ve also added ```input validation``` to ensure the correct data types to be inputted in the forms.

However, I need to ```add more comments``` for better documentation for each feature of my code. Another thing to take note of is to implement ```error handling```, to ensure the code runs smoothly even when issues arise. Addressing these will make my code more reliable and maintainable.

### Reflection 2
>After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors? 

Thinking on what unit tests were needed to be made was challenging at first, but it helped me understand my code better. I learned that it's not about the number of tests, but rather about testing all the important scenarios and edge cases. For example, in the ```ProductRepositoryTest```, I made sure to test not just the positive situations (like successful creation) but also to include some error cases (like trying to edit non-existent products).

I also think that 100% coverage doesn't guarantee our code to be bug-free. There could still be logical errors or edge cases I haven't thought about. It's more important to write meaningful tests that actually verify the functionality rather than just trying to test every single line of code.

>Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. 

Suppose i made a new functional test suite for verifying product list items, I think there would be some code smells. Logically, the new verifying product list item test was basically copying a lot of setup code from ```CreateProductFunctionalTest```, which violates the DRY (Don't Repeat Yourself) principle. This duplicate code would make maintenance harder. If we were to change how we set up tests, we'd have to change it in multiple places.

>What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner! 

I think creating a base test class (for example ```BaseFunctionalTest```) that contains all the common setup code and helper methods, such as setting the base product name and quantity. Then all the following functional tests could extend this base class. This would make the code more maintainable and reduce duplication. Another improvement could be creating helper methods for common operations like ```createProduct``` or ```editProduct``` that could be reused across different test classes.

