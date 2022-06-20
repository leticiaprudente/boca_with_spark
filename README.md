# LP2: Projeto Final com [Sparkjava](https://sparkjava.com/)

### Aluna: Leticia Macedo Prudente de Carvalho
### RA: 1460281813024
### Fatec São José dos Campos - 2022

## Run Application 
####  Step 0
`cd <DIRECTORY HOME>`

####  Step 1
`mvn clean install`

####  Step 2
`mvn compile package`

####  Step 3
`mvn exec:java -Dexec.mainClass="Main"`

## Routes

#### - Hello World
- **Post**:`http://localhost:4567/hello`

#### - Problem
- **Post**:
`http://localhost:4567/problem/addProblem`
- **Get All**:
`http://localhost:4567/problem/searchAllProblems`
- **Get By ID**:
`http://localhost:4567/problem/searchProblemByID/<problemID>`
- **Delete By ID**
`http://localhost:4567/problem/deleteProblemByID/<problemID>`

#### - Expected Answer
- **Post**:
`http://localhost:4567/expectedAnswer/addExpectedAnswer`
- **Get All By Problem ID**:
`http://localhost:4567/expectedAnswer/searchAnswersByProblemID/<problemID>`

#### - Source Code
- **Post**: `http://localhost:4567/sourceCode/addSourceCode`
- **Get By Filter**: `http://localhost:4567/sourceCode/searchSourceCodeByFilter`

## Postman File
- 📄 [**Sparkjava-Boca-Postman**](/Postman/SparkjavaBOCA.postman_collection.json)

## Project presentation 
- [**YouTube video**](https://youtu.be/FNvHO5KnxgY)

## Stop Application
- `stop();`
- `ctrl+c`
