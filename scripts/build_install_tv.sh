#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Usage: $0 <TV_IP>"
  echo "Example: $0 192.168.1.50"
  exit 1
fi

TV_IP="$1"

if [[ -f "gradlew" ]]; then
  chmod +x gradlew
  BUILD_CMD=("./gradlew")
elif command -v gradle >/dev/null 2>&1; then
  echo "gradlew not found, falling back to system Gradle."
  BUILD_CMD=("gradle")
else
  echo "Error: neither gradlew nor system gradle was found."
  echo "Install Gradle or open the project in Android Studio and Sync first."
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
