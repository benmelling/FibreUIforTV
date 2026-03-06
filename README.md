# Fibre UI for TV (Android TV starter)

This project is a **native Android TV app starter** that matches your requested UI style and gives you a clean base to add real IPTV, VOD, Real-Debrid, and YouTube integrations.

## What is already included

- Android TV app shell (Leanback launcher activity)
- Home screen UI with:
  - horizontal navigation pill (top-left)
  - date/time pill (top-right)
  - large preview pane with title, description, pills, and “Up Next”
  - content rows for Continue Watching, Favourite Channels, Watchlist, Suggestions
- App architecture stubs for:
  - IPTV player integration (`PlayerFacade`)
  - Universal search (`UniversalSearchEngine`)
  - Recording scheduler (`RecordingManager`)
  - Reminder receiver (`ReminderReceiver`)
  - SmartTube and YouTube Music deep links (`ExternalIntegrations`)

## Important reality check

Building a full production app like TiviMate/Stremio takes significant backend work:

- IPTV parser (M3U + Xtream + EPG XMLTV)
- Stream player + codecs + DRM handling
- Real-Debrid API auth + resolver + caching strategy
- Android TV global/universal search provider integration
- Recording pipeline (timeshift, storage, muxing, retries)
- Notifications, permissions, account sync

This repo gives you a high-quality **starting foundation**, not a complete commercial-grade product yet.

---

## Quick answer: build + install on your TV

1. Turn on **Developer options** on your TV and enable **USB debugging** / **Network debugging**.
2. Find your TV IP address (example: `192.168.1.50`).
3. In Terminal, go to the real folder where this repo exists on your Mac (do **not** type `/path/to/...` literally):

```bash
cd ~/Desktop/FibreUIforTV
pwd
ls -la
```

You should see `gradlew` in the `ls -la` output.

4. Build the APK:

```bash
chmod +x gradlew
./gradlew :app:assembleDebug
# or fallback when gradlew is unavailable:
gradle :app:assembleDebug
```

5. Install to TV (replace `192.168.1.50` with your actual TV IP):

```bash
adb connect 192.168.1.50:5555
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

6. Launch **Fibre UI TV** from Apps on your TV.

### One-command helper (recommended)

From repo root:

```bash
./scripts/build_install_tv.sh 192.168.1.50
```

This script builds the APK, connects ADB, and installs automatically.

### Why your previous commands failed

- `cd /path/to/FibreUIforTV` failed because `/path/to/...` is a placeholder, not a real path.
- `./gradlew` failed because you were not in the folder that contains `gradlew`.
- `adb connect <TV_IP>:5555` failed because `<TV_IP>` is a placeholder; use a real IP like `192.168.1.50`.

If install fails with `INSTALL_FAILED_VERSION_DOWNGRADE`, run:

```bash
adb uninstall com.fibreuitv
adb install app/build/outputs/apk/debug/app-debug.apk
```

---


> Note: some PR systems reject binary files. If `gradle-wrapper.jar` is missing in your checkout, use `gradle ...` commands directly or open the project in Android Studio and run a Gradle sync.
>
> If you see `Error: Unable to access jarfile .../gradle/wrapper/gradle-wrapper.jar`, this means wrapper scripts exist but the wrapper JAR does not. Use:
>
> ```bash
> gradle :app:assembleDebug
> ./scripts/build_install_tv.sh 192.168.1.50
> ```

## 1) Prerequisites

Install:

1. Android Studio (latest stable)
2. Android SDK 35
3. Android TV emulator image (optional)
4. A real Google/Android TV device with developer mode enabled

---

## 2) Open and build

1. Open this folder in Android Studio.
2. Let Gradle sync.
3. Select `app` run configuration.
4. Run on your TV device (ADB over network) or emulator.

### Build APK from terminal

```bash
./gradlew :app:assembleDebug
# or fallback:
gradle :app:assembleDebug
```

APK output:

`app/build/outputs/apk/debug/app-debug.apk`

---

## 3) Install on TV

(Use your actual TV IP.)

```bash
adb connect 192.168.1.50:5555
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Then open **Fibre UI TV** from your TV apps list.

---

## 4) Next implementation steps (simple roadmap)

### Step A — IPTV basics

- Add M3U + Xtream input screen
- Parse channels and groups
- Add EPG XMLTV import
- Bind EPG data into preview pane and guide

### Step B — Real player

- Add ExoPlayer media3 module
- Implement `PlayerFacade.play(...)` with actual stream URLs
- Add buffering/error UI and subtitle/audio tracks

### Step C — Real-Debrid

- Device-code auth flow
- Add torrent/magnet resolver
- Merge playable results into VOD rows

### Step D — YouTube + YouTube Music

- Keep existing deep links for SmartTube and YouTube Music
- Add recommendation caching in local DB so your app can show shelves

### Step E — Universal search and recommendations

- Merge local history + IPTV + VOD indexes
- Add Android TV search integration provider
- Add TMDB metadata fetch (poster/backdrop/similar items)

### Step F — Recording and reminders

- Replace `RecordingManager` in-memory list with Room DB
- Use WorkManager/AlarmManager + storage writer service
- Keep schedule and conflict resolver

---

## 5) 1080p + 4K guidance

- UI is Compose and scales with dp-based layouts.
- Test on both 1080p and 2160p emulator/device.
- Use high-resolution images (at least 1920x1080 backdrops, better 3840x2160 for hero art).
- Keep text sizing TV-friendly and verify focus visibility on a couch distance.

---

## 6) Project structure

- `MainActivity` — app entry point
- `ui/FibreTvApp.kt` — complete home-screen UI
- `data/DemoRepository.kt` — demo content data
- `integration/ExternalIntegrations.kt` — SmartTube / YT Music / Real-Debrid deep links
- `player/PlayerFacade.kt` — playback abstraction
- `recording/RecordingManager.kt` — recording scheduling model
- `search/UniversalSearchEngine.kt` — search across shelves
- `reminder/ReminderReceiver.kt` — reminder notification receiver stub

---

If you want, next I can generate **Phase 2** directly in this project:
- ExoPlayer integration
- M3U + Xtream parser
- XMLTV parser
- Real guide grid screen
- Real watch history + favourites DB
