#!/bin/bash
ARTIFACT=trove
REPO_ID="-DrepositoryId=release.aaloa.org"
REPO_URL="-Durl=http://nexus.aaloa.org/content/repositories/releases"
OPT="$REPO_ID $REPO_URL"

mvn deploy:deploy-file $OPT -DpomFile=pom.xml -Dfile=${ARTIFACT}.jar 
mvn deploy:deploy-file $OPT -DpomFile=pom.xml -Dfile=${ARTIFACT}-sources.jar -Dclassifier=sources
mvn deploy:deploy-file $OPT -DpomFile=pom.xml -Dfile=${ARTIFACT}-javadocs.jar -Dclassifier=javadocs
