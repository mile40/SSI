#/bin/bash!

java -classpath SampleAcn.jar:SampleLM.jar -Djava.security.manager -Djava.security.policy==sampleacn.policy  -Djava.security.auth.login.config==sample_jaas.config  sample.SampleAcn
