LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_CERTIFICATE := platform
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_SDK_VERSION := current
LOCAL_PACKAGE_NAME := NetController
include $(BUILD_PACKAGE)
# Use the following include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))