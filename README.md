# CoinMarketCap

## System Requirements
Maven version  3.5.3
JDK version  1.8.0_91
Browser version Chrome 93 and corresponding chromedriver already in package


## Run the Frontend

mvn clean install -p package-test -Dmaven.test.skip=false -Dtest.tagname=frontEnd -Dtest.threadcount=1 -Dtest.env=STG

## Run the Backend

mvn clean install -p package-test -Dmaven.test.skip=false -Dtest.tagname=backEnd -Dtest.threadcount=1 -Dtest.env=STG

## Run all tests together
mvn clean install -Dtest.env=STG