cd ../
cd src
javac -d ../bin -classpath ../lib/jdom.jar:../lib/junit-4.8.2.jar:. ch/idsia/agents/controllers/MyAgent.java
cd ../bin
java -classpath ../lib/jdom.jar:. ch.idsia.scenarios.Main -ag ch.idsia.agents.controllers.MyAgent -ld 1