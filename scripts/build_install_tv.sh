#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Usage: $0 <TV_IP>"
  echo "Example: $0 192.168.1.50"
  exit 1
fi

TV_IP="$1"
WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"

if [[ -f "gradlew" && -f "$WRAPPER_JAR" ]]; then
  chmod +x gradlew
  BUILD_CMD=("./gradlew")
elif command -v gradle >/dev/null 2>&1; then
  if [[ -f "gradlew" && ! -f "$WRAPPER_JAR" ]]; then
    echo "gradlew detected but $WRAPPER_JAR is missing; falling back to system Gradle."
  else
    echo "gradlew not found; falling back to system Gradle."
  fi
  BUILD_CMD=("gradle")
else
  echo "Error: cannot build because neither a working Gradle wrapper nor system gradle is available."
  echo "Fix options:"
  echo "  1) install Gradle and retry, or"
  echo "  2) restore $WRAPPER_JAR, or"
  echo "  3) open the project in Android Studio and Sync."
  exit 1
fi

echo "Building debug APK..."
"${BUILD_CMD[@]}" :app:assembleDebug

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
if [[ ! -f "$APK_PATH" ]]; then
  echo "Error: APK not found at $APK_PATH"
  exit 1
fi

echo "Connecting to TV at ${TV_IP}:5555 ..."
adb connect "${TV_IP}:5555"

echo "Installing APK..."
adb install -r "$APK_PATH"

echo "Done. Open 'Fibre UI TV' on your TV apps screen."
