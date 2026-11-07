package com.depthglass.studio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depthglass.studio.ui.components.GlassCard
import com.depthglass.studio.ui.components.SectionHeader
import com.depthglass.studio.ui.theme.*

@Composable
fun GalleryScreen(
    onEditProject: () -> Unit
) {
    val categories = listOf(
        "My Creations",
        "3D Wallpapers",
        "Glass Wallpapers",
        "Clock Designs",
        "Widget Presets",
        "Favorites",
        "Recently Edited",
        "Exported"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlassBlack)
    ) {
        SectionHeader(
            title = "My Gallery",
            subtitle = "Projects, presets & exports"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.1f),
                    onClick = onEditProject
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(GlassSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            if (category == "Favorites") Icons.Default.Favorite else Icons.Default.Folder,
                            contentDescription = null,
                            tint = AccentPurple,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        category,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Text(
                        "0 items",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
