rem /* define library folder */
set lib=.\lib
rem /* folder bin as classpath */
set NEWPATH=bin
rem /* for each *.jar in library folder, append it after classpath */
for %%1 in (%lib%\*.jar) do call :concat %%1

rem /* start chrome before server start */
start chrome http://localhost:8080/EmbeddingJettyTest/
rem /* Execute JettyClass */
java -Xmx128m -classpath %NEWPATH% jetty.JettyClass
goto :eof

rem /* sub function to append string */
:concat
set NEWPATH=%NEWPATH%;%1
goto :eof