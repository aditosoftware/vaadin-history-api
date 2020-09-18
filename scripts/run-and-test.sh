#!/usr/bin/env bash

function doLog() {
  echo -e "\e[1mINFO:\e[21m \e[32m""$1""\e[0m"
}

function doLogError() {
 echo -e "\e[1mERROR:\e[21m \e[31m""$1""\e[0m"
}

scriptDir=$(dirname -- "$(readlink -f -- "$BASH_SOURCE")")

# Switch directory
doLog "Switching working directory to 'cypress'"
cd cypress || return

# Install dependencies for Cypress
doLog "Installing dependencies for 'cypress' using yarn"
yarn

doLog "Switching working directory to '..'"
cd .. || return

# Start the jetty server as daemon using screen.
doLog "Starting the application server (demo/jetty:run) as daemon using screen"
screen -dmS application mvn jetty:run --file history-api-demo/pom.xml

# To save the exit code of the test process.
exitCode=0
{
  # Wait until the application server has been started.
  bash "$scriptDir"/wait-for-it.sh -h localhost -p 8080 -t 60

  doLog "Switching working directory to 'cypress'"
  cd cypress || return

  doLog "Start running tests using cypress"
  yarn cypress run
  exitCode=$? # Save the exit code of "cypress run" to the variable.
  doLog "Cypress exited with exit code'$exitCode'"
  if [ $exitCode -gt 0 ]; then
      doLogError "Cypress tests failed!"
  fi
}

doLog "Stopping application server"
# Always exit the application screen.
screen -X -S "application" stuff "^C"

# Pass the exit code of the cypress test to this script.
exit $exitCode
