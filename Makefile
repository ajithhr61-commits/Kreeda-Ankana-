.PHONY: build install build-install clean lint run

# Default path to the Android SDK
ANDROID_HOME ?= /home/gk/Android/Sdk

# Build the debug APK
build:
	ANDROID_HOME=$(ANDROID_HOME) ./gradlew assembleDebug

# Install the built APK to a connected device/emulator
install:
	adb install -r app/build/outputs/apk/debug/app-debug.apk

# Build and then install in one step
build-install: build install

# Clean all build outputs
clean:
	ANDROID_HOME=$(ANDROID_HOME) ./gradlew clean

# Run Android Lint
lint:
	ANDROID_HOME=$(ANDROID_HOME) ./gradlew lint

# Launch the app on the connected device
run:
	adb shell am start -n com.kreeda.ankana/.MainActivity
