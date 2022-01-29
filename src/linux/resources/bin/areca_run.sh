#!/usr/bin/env bash
####################################################################
#
# Generic script to launch Areca-backup's executables
#
####################################################################
#Options
JAVADIR=/usr/lib/jvm/java-1.8.0-openjdk-amd64

#Getting Areca's directory
PROGRAM_DIR=`dirname "$0"`
PROGRAM_DIR=`cd "$PROGRAM_DIR/.."; pwd`

"${JAVA_PROGRAM_DIR}java" -version

#Configured directories
LICENSE_PATH=${PROGRAM_DIR}
LIB_PATH=${PROGRAM_DIR}/lib/
TRANSLATION_PATH=${PROGRAM_DIR}/translations
CONFIG_PATH=${PROGRAM_DIR}/config


#Building Areca's classpath
CLASSPATH="${CLASSPATH}:${LICENSE_PATH}"
CLASSPATH="${CLASSPATH}:${CONFIG_PATH}"
CLASSPATH="${CLASSPATH}:${TRANSLATION_PATH}"

CLASSPATH="${CLASSPATH}:${LIB_PATH}*"

LIBRARY_PATH="${LIB_PATH}:/lib64:/lib:/usr/lib64:/usr/lib:/usr/lib64/java:/usr/lib/java:/usr/lib64/jni:/usr/lib/jni:/usr/share/java"

#See https://bugs.launchpad.net/gtk/+bug/442078
export GDK_NATIVE_WINDOWS=true

#Method to locate matching JREs
look_for_java() {
  IFS=$'\n'
  potential_java_dirs=(`ls -1 "$JAVADIR" | sort | tac`)
  IFS=
  for D in "${potential_java_dirs[@]}"; do
    if [[ -d "$JAVADIR/$D" && -x "$JAVADIR/$D/bin/java" ]]; then
      JAVA_PROGRAM_DIR="$JAVADIR/$D/bin/"
      echo "JRE found in ${JAVA_PROGRAM_DIR} directory."
      if check_version ; then
        return 0
      else
        return 1
      fi
    fi
  done
  echo "No valid JRE found in ${JAVADIR}."
  return 1
}

#Method to check the current JRE (>=1.4)
check_version() {
  JAVA_HEADER=`${JAVA_PROGRAM_DIR}java -version 2>&1 | head -n 1`
  JAVA_IMPL=`echo ${JAVA_HEADER} | cut -f1 -d' '`
  if [ "$JAVA_IMPL" = "java" ] | [ "$JAVA_IMPL" = "openjdk" ] ; then
    VERSION=`echo ${JAVA_HEADER} | sed "s/java version \"\(.*\)\"/\1/"`
    if echo $VERSION | grep "^1.[0-3]" ; then
      return 1
    else
      return 0
    fi
  else
    return 1
  fi
}

#Locate and test the java executable
if [ "$JAVA_PROGRAM_DIR" == "" ]; then
  if ! command -v java &>/dev/null; then
    if ! look_for_java ; then
      exit 1
    fi
  else
    if ! check_version ; then
      if ! look_for_java ; then
        exit 1
      fi
    fi
  fi
fi

#Launching Areca
"${JAVA_PROGRAM_DIR}java" -version
"${JAVA_PROGRAM_DIR}java" -Xmx1024m -Xms64m -cp "${CLASSPATH}" -Duser.dir="${PROGRAM_DIR}" -Djava.library.path="${LIBRARY_PATH}" -Djava.system.class.loader=com.application.areca.impl.tools.ArecaClassLoader $1 "$2" "$3" "$4" "$5" "$6" "$7" "$8" "$9" "${10}" "${11}" "${12}"
