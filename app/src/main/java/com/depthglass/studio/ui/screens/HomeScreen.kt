package com.depthglass.studio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depthglass.studio.ui.components.GlassButton
import com.depthglass.studio.ui.components.GlassCard
import com.depthglass.studio.ui.components.GlassPanel
import com.depthglass.studio.ui.components.SectionHeader
import com.depthglass.studio.ui.theme.*

@Composable
fun HomeScreen(
    onCreateNew: () -> Unit,
    onOpenEditor: () -> Unit,
    onOpenGallery: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlassBlack)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1035),
                            Color(0xFF0A0A0F)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "DepthGlass Studio 3D",
                    color = TextPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Premium multi-layer depth wallpapers\nOptimized for Motorola Edge 60 Pro",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                GlassButton(
                    text = "Create New Wallpaper",
                    onClick = onCreateNew,
                    accent = AccentBlue
                )
            }
        }

        SectionHeader(
            title = "Quick Actions",
            subtitle = "Start creating or continue editing"
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                QuickActionCard(
                    title = "Import Photos",
                    subtitle = "Gallery → Depth layers",
                    icon = Icons.Default.PhotoLibrary,
                    onClick = onCreateNew
                )
            }
            item {
                QuickActionCard(
                    title = "3D Editor",
                    subtitle = "Layer & depth tools",
                    icon = Icons.Default.Layers,
                    onClick = onOpenEditor
                )
            }
            item {
                QuickActionCard(
                    title = "My Gallery",
                    subtitle = "Saved projects",
                    icon = Icons.Default.Collections,
                    onClick = onOpenGallery
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(
            title = "Features",
            subtitle = "What you can create"
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureRow("Multi-layer 3D depth with parallax", Icons.Default.ViewInAr)
            FeatureRow("AI background removal & subject cutout", Icons.Default.AutoFixHigh)
            FeatureRow("12+ premium glass clock styles", Icons.Default.Watch)
            FeatureRow("15+ customizable glass widgets", Icons.Default.Widgets)
            FeatureRow("Native 1220×2712 Edge 60 Pro export", Icons.Default.PhoneAndroid)
            FeatureRow("Live wallpaper + gyroscope parallax", Icons.Default.MotionPhotosOn)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier.width(160.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AccentCyan,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(title, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(subtitle, color = TextSecondary, fontSize = 12.sp)
    }
}

@Composable
private fun FeatureRow(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(GlassPanel)
            .padding(14.dp)
    ) {
        Icon(icon, contentDescription = null, tint = AccentPurple, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Text(text, color = TextPrimary, fontSize = 14.sp)
    }
}
