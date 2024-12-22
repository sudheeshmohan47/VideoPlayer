package com.sample.videoplayer.presentation.home.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sample.designsystem.videoplayer.components.VideoPlayerRoundedEdgedCard
import com.sample.designsystem.videoplayer.components.VideoPlayerTopAppBar
import com.sample.designsystem.videoplayer.foundation.VideoPlayerPercentage
import com.sample.designsystem.videoplayer.foundation.VideoPlayerSize
import com.sample.designsystem.videoplayer.foundation.VideoPlayerSpacing
import com.sample.designsystem.videoplayer.foundation.dp
import com.sample.videoplayer.R
import com.sample.videoplayer.commonmodule.foundation.base.UiState
import com.sample.videoplayer.domain.model.MediaFile
import com.sample.videoplayer.presentation.home.HomeAction
import com.sample.videoplayer.presentation.home.HomeUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBarSection(
    topAppBarState: TopAppBarState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    VideoPlayerTopAppBar(
        modifier = modifier.statusBarsPadding(),
        topAppBarState = topAppBarState,
        title = stringResource(R.string.title_media),
        titleStyle = MaterialTheme.typography.titleLarge,
        titleFontWeight = FontWeight.Normal,
        titleColor = MaterialTheme.colorScheme.onBackground,
        displayBackNavigation = false,
        titleAlignment = TextAlign.Center,
        backgroundColor = MaterialTheme.colorScheme.background,
        customActions = {
            IconButton(
                modifier = Modifier
                    .padding(start = VideoPlayerSize.MEDIUM.dp()),
                onClick = {
                    onAction(HomeAction.OnClickDownloads)
                }
            ) {
                Icon(
                    modifier = Modifier.size(VideoPlayerSize.EXTRA_SMALL.dp()),
                    painter = painterResource(id = com.sample.videoplayer.player.R.drawable.ic_download),
                    contentDescription = "Download button"
                )
            }
        }
    )
}

@Composable
fun HomeScreenContent(
    homeUiState: UiState<HomeUiModel>,
    screenWidth: Dp,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
    homeListState: LazyListState = rememberLazyListState(),
) {
    homeUiState.data?.let { homeUiModel ->
        val mediaItems = homeUiModel.mediaList
        if (mediaItems.isNotEmpty()) {
            LazyColumn(
                state = homeListState,
                modifier = modifier.fillMaxSize().padding(VideoPlayerSpacing.MEDIUM.dp()),
                contentPadding = PaddingValues(vertical = VideoPlayerSpacing.SMALL.dp()),
                verticalArrangement = Arrangement.spacedBy(VideoPlayerSpacing.MEDIUM.dp()),
            ) {
                items(mediaItems) { mediaItem ->
                    MediaListItem(
                        mediaFile = mediaItem,
                        screenWidth = screenWidth,
                        onAction = onAction
                    )
                }
            }
        }
    }
}

@Composable
fun MediaListItem(
    mediaFile: MediaFile,
    screenWidth: Dp,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // Image size set to 20% of the screen width, making it square
    val imageWidthSize = screenWidth * VideoPlayerPercentage.PERCENT_20.value
    VideoPlayerRoundedEdgedCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(VideoPlayerSpacing.SMALL.dp())
                .clickable {
                    onAction(HomeAction.OnClickMediaFile(mediaFile))
                }
        ) {
            // Main image
            AsyncImage(
                modifier = Modifier
                    .width(imageWidthSize)
                    .aspectRatio(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mediaFile.thumbnailUrl.ifEmpty { com.sample.videoplayer.designsystem.R.drawable.placeholder })
                    .crossfade(true)
                    .error(com.sample.videoplayer.designsystem.R.drawable.placeholder)
                    .build(),
                placeholder = painterResource(com.sample.videoplayer.designsystem.R.drawable.placeholder),
                contentDescription = "Cart image",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(VideoPlayerSpacing.SMALL.dp()))
            // Details Section
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = mediaFile.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(VideoPlayerSpacing.EXTRA_SMALL.dp()))
                Text(
                    text = mediaFile.description,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
