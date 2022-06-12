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
`http://localhost:4567/hello`

#### - Problem
- **Post**:
`http://localhost:4567/problem/addProblem`
- **Get All**:
`http://localhost:4567/problem/searchAllProblems`
- **Get By ID**:
`http://localhost:4567/problem/searchProblemByID/<problemID>`
- **Delete**
`http://localhost:4567/problem/deleteProblemByID/<problemID>`

#### - ExpectedAnswer


## Postman File
- 📄 [**Sparkjava-Boca-Reqs**](/Postman/SparkjavaBOCA.postman_collection.json)

## Stop
`stop();`
