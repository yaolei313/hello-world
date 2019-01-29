#!/usr/bin/env bash

# 运行脚本
JAVA_CMD="/usr/bin/java"

APP_PATH="/opt/apps"
LOG_PATH="${APP_PATH}/logs"

JVM_ARGS=" -server
-Xmx2G -Xms2G -Xss512K -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=256M -XX:MaxDirectMemorySize=128M
-XX:+UseG1GC -XX:MaxGCPauseMillis=100
-XX:+UnlockExperimentalVMOptions -XX:+ParallelRefProcEnabled
-XX:G1LogLevel=finest
-Xloggc:${LOG_PATH}/gc.log.`date +%Y%m%d%H%M`

-XX:+PrintCommandLineFlags
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+PrintTenuringDistribution
-XX:+PrintReferenceGC
-XX:+PrintAdaptiveSizePolicy
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=${LOG_PATH}/dumps
-XX:ErrorFile=${LOG_PATH}/errors/pid_%p.log

-XX:+UseCompressedOops
-XX:+AlwaysPreTouch
-XX:AutoBoxCacheMax=10000

-XX:+TraceClassUnloading
-XX:+TraceClassLoading "

JVM_DEBUG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8989 -XX:NativeMemoryTracking=summary "

function run() {
    START_LOG=${LOG_PATH}"/start.log"

    echo "${APP_PATH}" > ${START_LOG}
    mkdir -p ${LOG_PATH}

    cd ${APP_PATH}

    CMD="exec ${JAVA_CMD} ${JVM_ARGS} ${JVM_DEBUG}"
    echo "${CMD}" >> ${START_LOG}
    ${CMD} -jar nebula.jar >> ${START_LOG} 2>&1 &
}

run

