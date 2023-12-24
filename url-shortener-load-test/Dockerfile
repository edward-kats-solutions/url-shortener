FROM maven:3.8.1-openjdk-17

WORKDIR /usr/src/load-test
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

COPY deploy/run-simulations.sh ./run-simulations.sh
RUN chmod +x run-simulations.sh

CMD ["/usr/src/load-test/run-simulations.sh"]
