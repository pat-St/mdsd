# Model Driven Software Development Exmaple Project
This project contains a template generator to transform the incoming config from the REST call an return an auto generated html page, which displays svg shapes.
## Used technologies
* SBT
* Dotty (Scala 3)
* Play Rest
* Play JSON
* Play Twirl
* Scala Parser Combinators
* Jdk >= 11
## How to start
* install sbt from **[here](https://www.scala-sbt.org/download.html)**
* install or change your system to java 11. With Ubuntu check and set `$JAVA_HOME`
* install **[Visual Studio Code](https://code.visualstudio.com/Download)** or **[VSCodium](https://vscodium.com/#install)** (recomendation)
* add the following [dotty](https://marketplace.visualstudio.com/items?itemName=lampepfl.dotty) plugin to your VSC 
* fetch this repo with `git pull https://github.com/pat-St/mdsd.git` 
* start your terminal an navigate to the local repository
* in the root directory exec `sbt launchIDE`
* now open the integrated terminal and run `sbt compile`
* to start the project run `sbt run`

### REST Request:
* send this http request to the application:
```http
POST / HTTP/1.1
Host: localhost:9000
Content-Type: text/plain

root stick figure 100 100 #aabb00
rectangle 29 18 30 2 Green
rectangle 10 25 2 40 Blue
rectangle 20 46 2 20 Black
rectangle 20 46 30 2 Red
rectangle 40 46 30 2 Red
circle 30 10 8 Red
```

### REST Response:
* your response should look like this:
```html
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Auto generated shapes</title>
	<link rel="stylesheet" media="screen" href="/assets/stylesheets/main.css">
	<link rel="shortcut icon" type="image/png" href="/assets/images/favicon.png">
</head>
<body>
	<h1>stick figure </h1>
	<div>
		<svg width="100" height="100" style="background-color: #aabb00;">
			<rect fill="Green" height="30" width="2" x="29" y="18"></rect>
			<rect fill="Blue" height="2" width="40" x="10" y="25"></rect>
			<rect fill="Black" height="2" width="20" x="20" y="46"></rect>
			<rect fill="Red" height="30" width="2" x="20" y="46"></rect>
			<rect fill="Red" height="30" width="2" x="40" y="46"></rect>
			<circle fill="Red" cx="30" cy="10" r="8"></circle>
		</svg>
	</div>
	<script src="/assets/javascripts/main.js" type="text/javascript"></script>
</body>
</html>
```
