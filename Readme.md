Project's web site :
http://www.areca-backup.org/

Project's page on Source Forge :
http://sourceforge.net/projects/areca/


See project's web site for user's manual, tutorial and FAQ.


# Info about this fork
This forks aims to create a Gradle reproducible build for all environments (linux/windows x86/x64) using Maven repositories.

Dependencies are almost the same as original Ant build.


## Dependencies
I tried to replicate build using the same version of original Ant build but referencing versions currently available from remote repositories.

I were unable to find jsch version so I used the one with the closest in time jar contents.

| Original jar name                                  | Inferred version     | Gradle remote dependency                               | 
|----------------------------------------------------|----------------------|--------------------------------------------------------|
| activation.jar                                     | 1.1                  | javax.activation:activation:1.1                        |
| commons-codec-1.4.jar                              | 1.4                  | commons-codec:commons-codec:1.4                        |
| commons-net-1.4.1.jar                              | 1.4.1                | commons-net:commons-net:1.4.1                          |
| jakarta-oro-2.0.8.jar                              | 2.0.8                | oro:oro:2.0.8                                          |
| jsch.jar                                           | ?                    | 0.1.4.2                                                |
| local_policy.jar                                   | n/a                  | n/a                                                    |
| javax.mail.jar                                     | 1.4                  | javax.mail:mail:1.4                                    |
| org.eclipse.core.commands_3.2.0.I20060605-1400.jar | 3.2.0.I20060605-1400 | org.eclipse.core:org.eclipse.core.commands:3.6.0       |
| org.eclipse.equinox.common_3.2.0.v20060603.jar     | 3.2.0.v20060603      | org.eclipse.equinox:org.eclipse.equinox.common:3.6.0   |
| org.eclipse.jface_3.2.0.I20060605-1400.jar         | 3.2.0.I20060605-1400 | org.eclipse:jface:3.3.0-I20070606-0010                 |
| swt.jar       (linux x64)                          | 4.332                | org.eclipse.swt:org.eclipse.swt.gtk.linux.x86_64:4.3   |
| swt.jar       (linux x86)                          | 4.332                | org.eclipse.swt:org.eclipse.swt.gtk.linux.x86:4.3      |
| swt.jar       (windows x64)                        | 4.332                | org.eclipse.swt:org.eclipse.swt.win32.win32.x86_64:4.3 |
| swt.jar       (windows x86)                        | 4.332                | org.eclipse.swt:org.eclipse.swt.win32.win32.x86:4.3    |

## File encoding
Files had iso-8859-1 enconding so I've converted them into utf-8.


## Needed linux packages
Install packages libattr1-dev and libacl1-dev with your linux distro package manager.
```
attr/xattr.h -> libattr1-dev

sys/acl.h -> libacl1-dev
```
See
https://sourceforge.net/p/areca/discussion/587586/thread/79d14ff3/


## How to compile
Default compilation for linux x64
```bash
./gradlew clean build
```
switches for other environments
```bash
./gradlew clean build -PbuildProfile=linuxX86
./gradlew clean build -PbuildProfile=winX86
./gradlew clean build -PbuildProfile=winX64
```

# TODO
Maybe install4j


# Info about legacy Ant compilation

## Generate header file for jni

Patch build.xml according to those links  
https://github.com/NixOS/nixpkgs/blob/master/pkgs/applications/backup/areca/default.nix  
https://github.com/NixOS/nixpkgs/blob/master/pkgs/applications/backup/areca/fix-javah-bug.diff  
```
<exec executable="javah">
    <arg value="-d"/>
    <arg value="${root}/jni"/>
    <arg value="-force"/>
    <arg value="-classpath"/>
    <arg value="${root}/lib/areca.jar"/>
    <arg value="com.myJava.file.metadata.posix.jni.wrapper.FileAccessWrapper"/>
```
command line becomes
```
xxx@xxx-ubuntu:~/areca-7.5-src$ javah -d ./jni -force -classpath ./lib/areca.jar com.myJava.file.metadata.posix.jni.wrapper.FileAccessWrapper
```


## Compile Jni native code
```
xxx@xxx-ubuntu:~/areca-7.5-src/jni$ gcc -I/usr/lib/jvm/java-1.8.0-openjdk-amd64/include -I/usr/lib/jvm/java-1.8.0-openjdk-amd64/include/linux -c -fPIC -lacl com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.c -o com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.o
```



