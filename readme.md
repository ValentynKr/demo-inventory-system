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