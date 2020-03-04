LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := auxin
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \
	-lz \
	-lm \

LOCAL_SRC_FILES := \
	main.c \

LOCAL_C_INCLUDES += $(call my-dir)

include $(BUILD_SHARED_LIBRARY)
