#
# quick build script
#
echo "Initializing Maven"
mvn initialize

echo "Doing clean install with Maven"
mvn clean install
