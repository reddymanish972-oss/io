# DepthGlass Studio 3D

**Premium multi-layer 3D depth wallpaper studio** optimized for the **Motorola Edge 60 Pro** (1220 × 2712).

## Features Implemented in this Skeleton

- ✅ Jetpack Compose + Material 3 glassmorphism UI
- ✅ Dark / light theme system with premium glass panels
- ✅ Bottom navigation (Home · Create · 3D Editor · Clock & Widgets · Gallery)
- ✅ Photo picker (Android Photo Picker) for multi-image import
- ✅ Layer list with depth, opacity, blur, scale controls
- ✅ 12 clock styles + live preview
- ✅ 15 widget types (ready for placement)
- ✅ Live Wallpaper service with gyroscope parallax
- ✅ Room database schema for saved projects
- ✅ Native Edge 60 Pro resolution constants
- ✅ Modular architecture ready for expansion

## What Still Needs Implementation (in Android Studio)

1. **AI Background Removal**  
   Wire ML Kit Subject Segmentation / Selfie Segmentation to the selected layer.

2. **Full Canvas Renderer**  
   Replace the placeholder preview with a Compose `Canvas` or OpenGL ES surface that draws all layers + clock + widgets with correct depth ordering and parallax.

3. **Export Pipeline**  
   Render the composition at exactly 1220 × 2712 and save as wallpaper / set via `WallpaperManager`.

4. **Project Persistence**  
   Serialize `WallpaperProject` with Gson and store via Room.

5. **3D Object Library**  
   Add a set of transparent PNGs (or glTF) under `assets/` and allow drag-drop placement.

6. **Advanced Gestures**  
   Full drag / pinch / rotate on individual layers using `detectTransformGestures`.

## How to Build

1. Open the `DepthGlassStudio3D` folder in **Android Studio** (Hedgehog or newer recommended).
2. Let Gradle sync (it will download Compose, Room, Coil, ML Kit, etc.).
3. Connect a device or start an emulator.
4. Run the `app` configuration.

### Recommended device / emulator

- Physical **Motorola Edge 60 Pro**, or
- Emulator with resolution close to 1220 × 2712 (or any tall 9:20 phone).

## Project Structure

```
app/src/main/java/com/depthglass/studio/
├── MainActivity.kt
├── DepthGlassApp.kt
├── data/
│   ├── model/Models.kt          ← all domain models + DeviceSpec
│   └── db/AppDatabase.kt        ← Room
├── ui/
│   ├── theme/                   ← Glass colors, typography, theme
│   ├── components/              ← GlassPanel, GlassButton, DepthSlider…
│   ├── navigation/              ← NavGraph + bottom bar
│   └── screens/                 ← Home, Create, Editor, Clock, Gallery, Settings
└── wallpaper/
    └── DepthLiveWallpaperService.kt
```

## Target Resolution

```kotlin
object DeviceSpec {
    const val WIDTH = 1220
    const val HEIGHT = 2712
}
```

All export and preview logic should respect these dimensions.

## License

MIT – feel free to extend into a full commercial product.

---

Built as a solid foundation for a professional depth-wallpaper studio.  
Open in Android Studio and continue from the TODOs marked in the editor and wallpaper service.
