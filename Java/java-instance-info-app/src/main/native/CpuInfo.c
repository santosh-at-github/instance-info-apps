#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_example_InstanceInfoController_getCpuInfo(JNIEnv *env, jobject obj) {
    FILE *fp;
    char buffer[1024];
    jstring result = NULL;

    fp = fopen("/proc/cpuinfo", "r");
    if (fp != NULL) {
        memset(buffer, 0, sizeof(buffer));
        while (fgets(buffer, sizeof(buffer), fp) != NULL) {
            // Append each line of cpuinfo to the result
            jstring line = (*env)->NewStringUTF(env, buffer);
            jclass stringClass = (*env)->FindClass(env, "java/lang/String");
            jmethodID concatMethodID = (*env)->GetMethodID(env, stringClass, "concat", "(Ljava/lang/String;)Ljava/lang/String;");
            result = (*env)->CallObjectMethod(env, result, concatMethodID, line);
            (*env)->DeleteLocalRef(env, line);
            (*env)->DeleteLocalRef(env, stringClass);
        }
        fclose(fp);
    }

    return result;
}

