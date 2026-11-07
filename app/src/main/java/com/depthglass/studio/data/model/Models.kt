package com.depthglass.studio.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.util.UUID

/** Motorola Edge 60 Pro native wallpaper size */
object DeviceSpec {
    const val WIDTH = 1220
    const val HEIGHT = 2712
    const val ASPECT_RATIO = WIDTH.toFloat() / HEIGHT.toFloat()
}

enum class LayerType {
    BACKGROUND,
    MIDDLE,
    FOREGROUND,
    OBJECT,
    CLOCK,
    WIDGET
}

enum class ClockStyle {
    DIGITAL_LARGE,
    DIGITAL_MINIMAL,
    ANALOG,
    FLOATING_3D,
    GLASS_TRANSPARENT,
    LIQUID_GLASS,
    NEON,
    GRADIENT,
    HOLOGRAPHIC,
    VERTICAL,
    STACKED,
    TYPOGRAPHY
}

enum class WidgetType {
    WEATHER,
    BATTERY,
    CALENDAR,
    DATE,
    MUSIC,
    CLOCK,
    DIGITAL_CLOCK,
    ANALOG_CLOCK,
    STEPS,
    SYSTEM_INFO,
    PHOTO_FRAME,
    NOTES,
    COUNTDOWN,
    WORLD_CLOCK,
    CUSTOM_TEXT
}

@Immutable
data class DepthLayer(
    val id: String = UUID.randomUUID().toString(),
    val type: LayerType = LayerType.FOREGROUND,
    val imageUri: String? = null,
    val name: String = "Layer",
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    val scale: Float = 1f,
    val rotation: Float = 0f,
    val depth: Float = 0.5f,          // 0 = far, 1 = near
    val alpha: Float = 1f,
    val blur: Float = 0f,
    val brightness: Float = 1f,
    val contrast: Float = 1f,
    val saturation: Float = 1f,
    val shadowRadius: Float = 0f,
    val shadowOffsetX: Float = 0f,
    val shadowOffsetY: Float = 0f,
    val glowColor: Long = 0x00000000,
    val locked: Boolean = false,
    val visible: Boolean = true
)

@Immutable
data class ClockConfig(
    val style: ClockStyle = ClockStyle.DIGITAL_LARGE,
    val offsetX: Float = 0.5f,        // normalized 0-1
    val offsetY: Float = 0.25f,
    val scale: Float = 1f,
    val color: Long = 0xFFFFFFFF,
    val secondaryColor: Long = 0xFF4FC3F7,
    val alpha: Float = 0.95f,
    val blur: Float = 0f,
    val showSeconds: Boolean = true,
    val showDate: Boolean = true,
    val showDay: Boolean = true,
    val is24Hour: Boolean = false,
    val fontSize: Float = 72f,
    val glowEnabled: Boolean = true,
    val depth: Float = 0.4f           // can sit behind or in front of subjects
)

@Immutable
data class WidgetConfig(
    val id: String = UUID.randomUUID().toString(),
    val type: WidgetType = WidgetType.WEATHER,
    val offsetX: Float = 0.5f,
    val offsetY: Float = 0.7f,
    val scale: Float = 1f,
    val alpha: Float = 0.9f,
    val cornerRadius: Float = 24f,
    val backgroundColor: Long = 0x661A1A28,
    val textColor: Long = 0xFFFFFFFF,
    val accentColor: Long = 0xFF4FC3F7,
    val blur: Float = 20f,
    val depth: Float = 0.6f,
    val customText: String = ""
)

@Immutable
data class WallpaperProject(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "Untitled Project",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val thumbnailUri: String? = null,
    val layers: List<DepthLayer> = emptyList(),
    val clock: ClockConfig = ClockConfig(),
    val widgets: List<WidgetConfig> = emptyList(),
    val backgroundColor: Long = 0xFF0A0A0F,
    val parallaxStrength: Float = 0.6f,
    val isFavorite: Boolean = false
)

/** Pre-defined 3D object categories for the library */
enum class ObjectCategory {
    HUMAN,
    ANIMAL,
    NATURE,
    VEHICLE,
    SPACE,
    ABSTRACT,
    PARTICLE,
    CUSTOM
}
