#!/bin/bash

echo "Generating Allure Report..."
echo

echo "Step 1: Running tests to generate allure-results..."
mvn clean test

echo
echo "Step 2: Generating HTML report..."
mvn allure:report

echo
echo "Step 3: Opening report in browser..."
mvn allure:serve

echo
echo "Allure report generation complete!" 