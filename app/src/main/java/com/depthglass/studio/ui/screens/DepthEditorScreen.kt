package com.depthglass.studio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.depthglass.studio.data.model.DepthLayer
import com.depthglass.studio.data.model.DeviceSpec
import com.depthglass.studio.data.model.LayerType
import com.depthglass.studio.ui.components.DepthSlider
import com.depthglass.studio.ui.components.GlassButton
import com.depthglass.studio.ui.components.GlassIconButton
import com.depthglass.studio.ui.components.GlassPanel
import com.depthglass.studio.ui.components.SectionHeader
import com.depthglass.studio.ui.theme.*

@Composable
fun DepthEditorScreen(
    onOpenClock: () -> Unit
) {
    // Sample layers for demo (in real app these come from ViewModel + imported images)
    var layers by remember {
        mutableStateOf(
            listOf(
                DepthLayer(name = "Background", type = LayerType.BACKGROUND, depth = 0.1f, blur = 8f),
                DepthLayer(name = "Subject 1", type = LayerType.FOREGROUND, depth = 0.75f),
                DepthLayer(name = "Subject 2", type = LayerType.MIDDLE, depth = 0.45f)
            )
        )
    }
    var selectedLayerId by remember { mutableStateOf(layers.firstOrNull()?.id) }
    val selectedLayer = layers.find { it.id == selectedLayerId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlassBlack)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "3D Depth Editor",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                GlassIconButton(onClick = { /* Undo */ }) {
                    Icon(Icons.Default.Undo, null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                }
                GlassIconButton(onClick = { /* Redo */ }) {
                    Icon(Icons.Default.Redo, null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                }
                GlassIconButton(onClick = onOpenClock) {
                    Icon(Icons.Default.Watch, null, tint = AccentCyan, modifier = Modifier.size(20.dp))
                }
            }
        }

        // Preview area (aspect ratio of Edge 60 Pro)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .aspectRatio(DeviceSpec.ASPECT_RATIO)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1A1A24))
                .border(1.dp, BorderGlass, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Layer stack visualization
            Text(
                text = "Live Preview\n${DeviceSpec.WIDTH} × ${DeviceSpec.HEIGHT}",
                color = TextMuted,
                fontSize = 13.sp
            )
            // In production: render all layers with Canvas + depth transforms + clock + widgets
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Layer list + controls
        GlassPanel(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                "Layers",
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f, fill = false)
            ) {
                items(layers, key = { it.id }) { layer ->
                    LayerItem(
                        layer = layer,
                        selected = layer.id == selectedLayerId,
                        onSelect = { selectedLayerId = layer.id }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Controls for selected layer
            selectedLayer?.let { layer ->
                Text(
                    "Depth & Appearance — ${layer.name}",
                    color = AccentCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                DepthSlider(
                    label = "Depth (0 = far, 1 = near)",
                    value = layer.depth,
                    onValueChange = { newDepth ->
                        layers = layers.map {
                            if (it.id == layer.id) it.copy(depth = newDepth) else it
                        }
                    }
                )
                DepthSlider(
                    label = "Opacity",
                    value = layer.alpha,
                    onValueChange = { v ->
                        layers = layers.map {
                            if (it.id == layer.id) it.copy(alpha = v) else it
                        }
                    }
                )
                DepthSlider(
                    label = "Blur",
                    value = layer.blur / 20f,
                    onValueChange = { v ->
                        layers = layers.map {
                            if (it.id == layer.id) it.copy(blur = v * 20f) else it
                        }
                    }
                )
                DepthSlider(
                    label = "Scale",
                    value = layer.scale,
                    onValueChange = { v ->
                        layers = layers.map {
                            if (it.id == layer.id) it.copy(scale = v) else it
                        }
                    },
                    valueRange = 0.3f..2.5f
                )
            }
        }

        // Bottom actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlassButton(
                text = "Export Wallpaper",
                onClick = { /* Trigger export at 1220×2712 */ },
                accent = AccentBlue,
                modifier = Modifier.weight(1f)
            )
            GlassButton(
                text = "Set Live",
                onClick = { /* Set as live wallpaper */ },
                accent = AccentPurple,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LayerItem(
    layer: DepthLayer,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) Color(0x334FC3F7) else Color.Transparent)
            .border(
                1.dp,
                if (selected) AccentBlue else Color.Transparent,
                RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = when (layer.type) {
                LayerType.BACKGROUND -> Icons.Default.Image
                LayerType.FOREGROUND -> Icons.Default.Person
                LayerType.MIDDLE -> Icons.Default.Layers
                else -> Icons.Default.Category
            },
            contentDescription = null,
            tint = if (selected) AccentCyan else TextSecondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(layer.name, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(
                "Depth ${"%.2f".format(layer.depth)} · ${layer.type.name.lowercase()}",
                color = TextMuted,
                fontSize = 11.sp
            )
        }
        IconButton(onClick = onSelect) {
            Icon(
                if (layer.visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
