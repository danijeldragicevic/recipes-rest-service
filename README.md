# Recipes REST service
Backend service who is able to crete, update, search and delete recipes. 
- Only registered users can create and see recipes.
- Recipes can be updated and deleted only by the users who created them.

Service use embedded H2 database and Basic authentication.

# Technology
- Java 11
- Spring Boot 2.7.1 (Spring Web MVC, Spring Data Jpa, Spring Security, Spring Validation, Project Lombok, H2 database)
- Gradle 7.4

# To run application:
Navigate to the project root directory and run **./gradlew bootRun**

# Exposed endpoints:
By default, service will run on **http://localhost:8881/** <br/>
Following endpoints will be exposed:

| Methods | Urls                                 | Actions                                     |
|---------|--------------------------------------|---------------------------------------------|
| POST    | api/register                         | Register new user                           |
| POST    | api/recipe/new                       | Create new recipe                           |
| GET     | api/recipe/:id                       | Get recipe by :id                           |
| GET     | api/recipe/search?category=[keyword] | Find all recipes with the category :keyword |
| GET     | api/recipe/search?name=[keyword]     | Find all recipes with the name :keyword     |
| PUT     | api/recipe/:id                       | Update recipe by :id                        |
| DELETE  | api/recipe/:id                       | Delete recipe by :id                        |

# Examples
**Example 1:** POST /api/recipe/new request without authentication
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```
Status code: 401 (Unauthorized)


**Example 2:** POST /api/register request without authentication
```
{
   "email": "Cook_Programmer@somewhere.com",
   "password": "RecipeInBinary"
}
```
Status code: 200 (Ok)

Further, POST /api/recipe/new request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary
```
{
   "name": "Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```
Response:
```
{
   "id": 1
}
```

Further, PUT /api/recipe/1 request with basic authentication; email (login): Cook_Programmer@somewhere.com, password: RecipeInBinary
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```
Status code: 204 (No Content)

Further, GET /api/recipe/1 request with basic authentication; email (login): Cook_Programmer@somewhere.com, password: RecipeInBinary
Response:
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "date": "2020-01-02T12:11:25.034734",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

**Example 3:** POST /api/register request without authentication
```
{
   "email": "CamelCaseRecipe@somewhere.com",
   "password": "C00k1es."
}
```
Status code: 200 (Ok)

Further, response for the GET /api/recipe/1 request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "date": "2020-01-02T12:11:25.034734",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

Further, PUT /api/recipe/1 request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.
```
{
   "name": "Warming Ginger Tea",
   "category": "beverage",
   "description": "Ginger tea is a warming drink for cool weather, ...",
   "ingredients": ["1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"],
   "directions": ["Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)", "Steep for 5-10 minutes", "Drink and enjoy"]
}
```
Status code: 403 (Forbidden)


Further, DELETE /api/recipe/1 request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.
Status code: 403 (Forbidden)

# Licence
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
