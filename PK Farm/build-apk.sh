#!/bin/bash

# Set project directory (modify as needed)
PROJECT_DIR=$(pwd)  # Assumes script is in the project root
APP_DIR="$PROJECT_DIR/app"
OUTPUT_DIR="$APP_DIR/build/outputs/apk"

# Keystore details (change these for a release build)
KEYSTORE_PATH="$PROJECT_DIR/my-release-key.keystore"
KEY_ALIAS="my-key-alias"
KEY_PASSWORD="your-key-password"
STORE_PASSWORD="your-store-password"

echo "Starting APK Build..."

# Step 1: Clean project
echo "Cleaning project..."
./gradlew clean

# Step 2: Build APK (Debug or Release)
echo "Building APK..."
./gradlew assembleRelease  # Use assembleDebug for debug builds

# Check if the APK is built
APK_PATH="$OUTPUT_DIR/release/app-release-unsigned.apk"
if [ ! -f "$APK_PATH" ]; then
    echo "APK build failed!"
    exit 1
fi

# Step 3: Sign the APK (for release only)
SIGNED_APK="$OUTPUT_DIR/release/app-release.apk"
echo "Signing APK..."
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
    -keystore "$KEYSTORE_PATH" -storepass "$STORE_PASSWORD" \
    -keypass "$KEY_PASSWORD" "$APK_PATH" "$KEY_ALIAS"

# Step 4: Align APK
echo "Aligning APK..."
zipalign -v 4 "$APK_PATH" "$SIGNED_APK"

# Step 5: Verify the signed APK
echo "Verifying APK..."
apksigner verify "$SIGNED_APK"

echo "APK build completed!"
echo "Find your APK at: $SIGNED_APK"
