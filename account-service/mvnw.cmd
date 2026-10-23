@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper Startup Batch script
@REM ----------------------------------------------------------------------------

@IF "%DEBUG%"=="" @ECHO OFF
@REM set %ENABLE_DELAYED_EXPANSION% to on if you need it
SETLOCAL ENABLEDELAYEDEXPANSION

SET MAVEN_CMD_LINE_ARGS=%*

@REM Find the project root directory
SET MAVEN_PROJECTBASEDIR=%~dp0
:strip
IF "%MAVEN_PROJECTBASEDIR:~-1%"=="\" SET MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%
IF "%MAVEN_PROJECTBASEDIR:~-1%"=="\" GOTO strip

IF NOT EXIST "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties" (
  ECHO Error: Could not find %MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties
  EXIT /B 1
)

SET WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@REM Provide a default value for JAVA_HOME if not set
IF "%JAVA_HOME%"=="" (
  IF EXIST "C:\Program Files\Java\jdk-17" (
    SET "JAVA_HOME=C:\Program Files\Java\jdk-17"
  )
)

IF "%JAVA_HOME%"=="" (
  FOR /F "tokens=2*" %%A IN ('REG QUERY "HKLM\SOFTWARE\JavaSoft\Java Development Kit" /v CurrentVersion 2^>NUL') DO (
    FOR /F "tokens=2*" %%C IN ('REG QUERY "HKLM\SOFTWARE\JavaSoft\Java Development Kit\%%B" /v JavaHome 2^>NUL') DO (
      SET "JAVA_HOME=%%D"
    )
  )
)

IF "%JAVA_HOME%"=="" (
  ECHO Error: JAVA_HOME not found in your environment. >&2
  ECHO Please set the JAVA_HOME variable in your environment to match the >&2
  ECHO location of your Java installation. >&2
  EXIT /B 1
)

IF NOT EXIST "%JAVA_HOME%\bin\java.exe" (
  ECHO Error: JAVA_HOME is set to an invalid directory. >&2
  ECHO JAVA_HOME = "%JAVA_HOME%" >&2
  ECHO Please set the JAVA_HOME variable in your environment to match the >&2
  ECHO location of your Java installation. >&2
  EXIT /B 1
)

SET "JAVACMD=%JAVA_HOME%\bin\java.exe"

@REM Start Maven Wrapper
"%JAVACMD%" -Dfile.encoding=UTF-8 -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -cp "%WRAPPER_JAR%" %WRAPPER_LAUNCHER% %MAVEN_CMD_LINE_ARGS%
IF ERRORLEVEL 1 EXIT /B %ERRORLEVEL%
