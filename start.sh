mvn clean install
mkdir run
cd run
rm -rf sheldon-*.jar
cp ../target/sheldon-*.jar .
java -jar sheldon.*.jar
