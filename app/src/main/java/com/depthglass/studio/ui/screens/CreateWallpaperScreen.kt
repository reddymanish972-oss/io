package com.depthglass.studio.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.depthglass.studio.ui.components.GlassButton
import com.depthglass.studio.ui.components.GlassPanel
import com.depthglass.studio.ui.components.SectionHeader
import com.depthglass.studio.ui.theme.*

@Composable
fun CreateWallpaperScreen(
    onContinueToEditor: () -> Unit
) {
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 12)
    ) { uris ->
        if (uris.isNotEmpty()) {
            selectedImages = selectedImages + uris
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlassBlack)
    ) {
        SectionHeader(
            title = "Create Wallpaper",
            subtitle = "Import photos to build depth layers"
        )

        GlassPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Import from Gallery",
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = "Select multiple images. Each can become a foreground, middle or background layer. AI cutout will be available in the 3D Editor.",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 6.dp, bottom = 16.dp)
            )

            GlassButton(
                text = if (selectedImages.isEmpty()) "Select Photos" else "Add More Photos",
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                accent = AccentBlue,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (selectedImages.isNotEmpty()) {
            SectionHeader(
                title = "Selected (${selectedImages.size})",
                subtitle = "These will become editable layers"
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(selectedImages) { uri ->
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(14.dp))
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        IconButton(
                            onClick = {
                                selectedImages = selectedImages.filter { it != uri }
                            },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remove",
                                tint = TextPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            GlassButton(
                text = "Continue to 3D Editor",
                onClick = onContinueToEditor,
                accent = AccentPurple,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No images selected yet",
                        color = TextMuted,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
