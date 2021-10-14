#!/bin/bash

# Installation Maven
echo -n "Maven Install  : In progress..."

if mvn clean install
then
    echo -e "\rMaven Install  : OK            "
    read -rsp $'Press any key to continue...\n' -n1
    exit 0;
else
    echo -e "\rMaven Install  : An error has occurred"
    read -rsp $'Press any key to continue...\n' -n1
    exit 1;
fi
