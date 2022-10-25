***

## Getting Started
IntelliJ IDEA is recommended

<ins>**1. Downloading the repository:**</ins>

Start by cloning the repository with `git clone https://github.com/AlsoNeDonil/SequenceProcessor`

<ins>**2. Run project**</ins>

1. Run the `gradlew bootRun` on Windows or `./gradlew bootRun` on Linux

***

## API
`POST /process` with body
{
    "filename" : path to file,
    "action" : Operation
}

<ins>Operation list </ins>
"min" - Min number in file
"max" - Max number in file
"average" - Average of numbers
"median" - Median number in file
"increase_sequence" - Increasing sequence(sequences) of maximum length
"decrease_sequence" - Increasing sequence(sequences) of maximum length

***


## Min number

### Request

`POST /process/`

    curl -i -X POST -H "Content-Type:application/json" -d "{""filename"":""C:/Users/NeDonil/Desktop/10m.txt"", ""action"":""min""}" http://localhost:8080/process

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chuncked
    Date: Tue, 25 Oct 2022 18:54:13 GMT

    -49999996
## Max number

### Request

`POST /process/`

    curl -i -X POST -H "Content-Type:application/json" -d "{""filename"":""C:/Users/NeDonil/Desktop/10m.txt"", ""action"":""max""}" http://localhost:8080/process

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chuncked
    Date: Tue, 25 Oct 2022 18:54:13 GMT

    49999978
## Average

### Request

`POST /process/`

    curl -i -X POST -H "Content-Type:application/json" -d "{""filename"":""C:/Users/NeDonil/Desktop/10m.txt"", ""action"":""average""}" http://localhost:8080/process

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chuncked
    Date: Tue, 25 Oct 2022 18:54:13 GMT

    7364.418442641844
## Median 

### Request

`POST /process/`

    curl -i -X POST -H "Content-Type:application/json" -d "{""filename"":""C:/Users/NeDonil/Desktop/10m.txt"", ""action"":""median""}" http://localhost:8080/process

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chuncked
    Date: Tue, 25 Oct 2022 18:54:13 GMT

    25216
## Increase sequence

### Request

`POST /process/`

    curl -i -X POST -H "Content-Type:application/json" -d "{""filename"":""C:/Users/NeDonil/Desktop/10m.txt"", ""action"":""increase_sequence""}" http://localhost:8080/process

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chuncked
    Date: Tue, 25 Oct 2022 18:54:13 GMT

    [[-48190694,-47725447,-43038241,-20190291,-17190728,-6172572,8475960,25205909,48332507,48676185]]
## Decrease sequence

### Request

`POST /process/`

    curl -i -X POST -H "Content-Type:application/json" -d "{""filename"":""C:/Users/NeDonil/Desktop/10m.txt"", ""action"":""decrease_sequence""}" http://localhost:8080/process

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chuncked
    Date: Tue, 25 Oct 2022 18:54:13 GMT

    [[47689379,42381213,30043880,12102356,-4774057,-5157723,-11217378,-23005285,-23016933,-39209115,-49148762]]