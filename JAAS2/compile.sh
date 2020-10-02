#bin/bash!

echo "cleaning working directory"
rm -rf *.jar 

#compilation du code
echo "compilation du code"

javac sample/SampleAction.java sample/SampleAzn.java sample/module/SampleLoginModule.java sample/principal/SamplePrincipal.java

echo "copile done"


#création des fichiers jar 

echo "creatng SampleAzn.jar"

jar -cvf SampleAzn.jar sample/SampleAzn.class sample/MyCallbackHandler.class

echo "SimpleAzn.jar  done"

echo "creating SampelAction.jar"

jar -cvf SampleAction.jar sample/SampleAction.class

echo "SampleAction.jar done"

echo "creating SampleLM.jar"

jar -cvf SampleLM.jar sample/module/SampleLoginModule.class sample/principal/SamplePrincipal.class

echo "SampleLM.jar done"
