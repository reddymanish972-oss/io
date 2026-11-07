package com.depthglass.studio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depthglass.studio.data.model.ClockStyle
import com.depthglass.studio.data.model.WidgetType
import com.depthglass.studio.ui.components.DepthSlider
import com.depthglass.studio.ui.components.GlassButton
import com.depthglass.studio.ui.components.GlassPanel
import com.depthglass.studio.ui.components.SectionHeader
import com.depthglass.studio.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ClockWidgetsScreen() {
    var selectedClockStyle by remember { mutableStateOf(ClockStyle.DIGITAL_LARGE) }
    var clockScale by remember { mutableFloatStateOf(1f) }
    var clockAlpha by remember { mutableFloatStateOf(0.95f) }
    var showSeconds by remember { mutableStateOf(true) }
    var is24Hour by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlassBlack)
            .verticalScroll(rememberScrollState())
    ) {
        SectionHeader(
            title = "Clock Styles",
            subtitle = "Choose and customize your glass clock"
        )

        // Live clock preview
        GlassPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                val timeFormat = if (is24Hour) "HH:mm" else "h:mm"
                val time = SimpleDateFormat(timeFormat, Locale.getDefault()).format(Date())
                val seconds = if (showSeconds) SimpleDateFormat(":ss", Locale.getDefault()).format(Date()) else ""
                Text(
                    text = "$time$seconds",
                    color = Color.White,
                    fontSize = (48 * clockScale).sp,
                    fontWeight = FontWeight.Light
                )
            }
            Text(
                text = selectedClockStyle.name.replace('_', ' '),
                color = AccentCyan,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Clock style grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(220.dp)
        ) {
            items(ClockStyle.entries) { style ->
                val selected = style == selectedClockStyle
                Box(
                    modifier = Modifier
                        .aspectRatio(1.4f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selected) Color(0x334FC3F7) else GlassPanel)
                        .border(
                            1.dp,
                            if (selected) AccentBlue else BorderGlass,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedClockStyle = style }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = style.name.split('_').take(2).joinToString(" "),
                        color = if (selected) TextPrimary else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        GlassPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Clock Adjustments", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            DepthSlider("Scale", clockScale, { clockScale = it }, 0.5f..2f)
            DepthSlider("Opacity", clockAlpha, { clockAlpha = it })
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GlassButton(
                    text = if (showSeconds) "Hide Seconds" else "Show Seconds",
                    onClick = { showSeconds = !showSeconds },
                    accent = AccentPurple,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                GlassButton(
                    text = if (is24Hour) "12h" else "24h",
                    onClick = { is24Hour = !is24Hour },
                    accent = AccentBlue,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        SectionHeader(
            title = "Widgets",
            subtitle = "Add glass widgets to your wallpaper"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(280.dp)
        ) {
            items(WidgetType.entries) { type ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1.2f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(GlassPanel)
                        .border(1.dp, BorderGlass, RoundedCornerShape(14.dp))
                        .clickable { /* Add widget to project */ }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = type.name.replace('_', ' '),
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
