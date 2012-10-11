rem /*
rem define library folder
rem */
set lib=.\lib
rem /*
rem folder bin as classpath
rem */
set NEWPATH=bin
rem /*
rem for each *.jar in library folder, append it after classpath
rem */
for %%1 in (%lib%\*.jar) do call :concat %%1

rem /*
rem start chrome before server start
rem */
start chrome http://localhost:8080/EmbeddingJettyTest/
rem /*
rem Execute JettyClass
rem */
java -Xmx128m -classpath %NEWPATH% jetty.JettyClass
goto :eof

rem /*
rem sub function to append string
rem */
:concat
set NEWPATH=%NEWPATH%;%1
goto :eof