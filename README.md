# Pink Star Calculator

A Spring Boot web calculator. The page (`src/main/resources/static/index.html`)
sends your equation to the Java backend, which does the math.

## Run it on your computer

    mvn spring-boot:run

Then open http://localhost:8080

## Deploy to Render

1. Create an empty repository on GitHub.
2. In this folder run:

       git init
       git add .
       git commit -m "Initial calculator commit"
       git remote add origin <your-github-repo-url>
       git branch -M main
       git push -u origin main

3. On Render: New + -> Web Service -> connect the repo -> Runtime: Docker ->
   Free tier -> Deploy Web Service.
4. Open the public URL Render gives you, test a calculation, then submit it.

## Endpoints

- `GET /evaluate?expression=38670%C3%B750000` -> `{"result":"0.7734"}`
- `GET /calculate?num1=6&num2=3&operator=divide` -> `{"result":"2"}`
  (operators: add, subtract, multiply, divide)
