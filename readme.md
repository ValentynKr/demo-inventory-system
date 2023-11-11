# Inventory System Application

A simple inventory system application using Spring Boot that manages 
products for a store. The application allows users to view a list of 
available products, add new products, update and remove existing ones. 
Each product has a name, description, price, and quantity. The product 
information is persisted in a MySQL database using Hibernate.

## Setup

1. Clone the repository:

```
git clone https://github.com/ValentynKr/demo-inventory-system
```

2. Set up the MySQL database:

Create a new MySQL database and import the structure from the `mydb_products.sql` 
file in the repository `db` folder.

3. Configure the application:

Create a new file called `application.properties` in the `src/main/resources` directory, 
and add the following properties:

```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database?useSSL=false
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

Replace `your_database`, `your_username`, and `your_password` with the appropriate values.

>If `application.properties` already exists in the project - keep it as is!

Set up in IDE environment variables in run configurations respectively:

```
DB_USER_NAME=your_user_name_set_in_DB
DB_PASSWORD=your_password_set_in_DB
```

4. Build and run the application via Maven or IDE:

```
mvn clean install
mvn spring-boot:run
```

The application will start at `localhost:8080/api/products`.

## Endpoints

- `GET /api/products`: List all products.

- `GET /api/products/{id}`: Get product details by ID.

- `GET /api/products/getAddProductView`: Show the view to add a new product.

- `GET /api/products/getEditProductView/{id}`: Show the view to edit an existing product.

- `POST /api/products`: Add a new product.

- `POST /api/products/update/{id}`: Update an existing product.

- `POST /api/products/{id}`: Delete a product.

The project was created with the assistance of ChatGPT.

## Feedback

1. Was it easy to complete the task using AI? 

While working with AI, generating the skeleton of the controller, services, views, and model, along with unit tests, was relatively easy. However, I encountered difficulties during the integration testing and debugging process, particularly when working with Hibernate.

2. How long did the task take you to complete?

The task took me a total of 6 hours of consecutive work to complete. About 2 hours of that time was spent clarifying prompts and debugging various issues.

3. Was the code ready to run after generation? What did you have to change to make it usable?

In 60% of the cases, the generated code was ready to run without any modifications. In 20% of the cases, the code required light refactoring before it could be used effectively. The remaining 20% of the cases necessitated writing code from scratch.

4. Which challenges did you face during the completion of the task?

Some of the challenges I faced during the task completion included debugging stack traces, correcting invalid method suggestions, configuring and unifying the code style, as well as creating integration tests.

5. Which specific prompts did you learn as good practices to complete the task?

One good practice I learned during the task involves generating new methods and views based on analogous, previously generated examples. This approach enabled more efficient coding and a smoother development process overall.