package com.depthglass.studio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depthglass.studio.ui.components.GlassPanel
import com.depthglass.studio.ui.components.SectionHeader
import com.depthglass.studio.ui.theme.*

@Composable
fun SettingsScreen() {
    var darkMode by remember { mutableStateOf(true) }
    var parallaxEnabled by remember { mutableStateOf(true) }
    var highQualityExport by remember { mutableStateOf(true) }
    var batterySaver by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlassBlack)
            .verticalScroll(rememberScrollState())
    ) {
        SectionHeader(
            title = "Settings",
            subtitle = "App preferences & device optimization"
        )

        GlassPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Appearance", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            SettingRow("Dark Mode", darkMode) { darkMode = it }
            SettingRow("High Quality Export (1220×2712)", highQualityExport) { highQualityExport = it }
        }

        GlassPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Live Wallpaper", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            SettingRow("Gyroscope Parallax", parallaxEnabled) { parallaxEnabled = it }
            SettingRow("Battery Saver Mode", batterySaver) { batterySaver = it }
        }

        GlassPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Device", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Target: Motorola Edge 60 Pro", color = TextSecondary, fontSize = 13.sp)
            Text("Native resolution: 1220 × 2712", color = TextSecondary, fontSize = 13.sp)
            Text("Aspect ratio: 9:20 (portrait)", color = TextSecondary, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text("DepthGlass Studio 3D  ·  v1.0.0", color = TextMuted, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SettingRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = TextPrimary, fontSize = 14.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = AccentBlue,
                checkedTrackColor = AccentBlue.copy(alpha = 0.4f),
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = GlassSurface
            )
        )
    }
}
