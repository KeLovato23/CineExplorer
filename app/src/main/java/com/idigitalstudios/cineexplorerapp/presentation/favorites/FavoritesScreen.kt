package com.idigitalstudios.cineexplorerapp.presentation.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.idigitalstudios.cineexplorerapp.presentation.components.EmptyState
import com.idigitalstudios.cineexplorerapp.presentation.components.ShowCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onShowClick: (Int) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Favoritos",
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (favorites.isEmpty()) {
                EmptyState(message = "Aún no tienes favoritos.\nMarca una serie desde su detalle.")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(favorites, key = { _, show -> show.id }) { index, show ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(animationSpec = androidx.compose.animation.core.tween(
                                durationMillis = 300,
                                delayMillis = index * 50
                            )) + slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = androidx.compose.animation.core.tween(
                                    durationMillis = 300,
                                    delayMillis = index * 50
                                )
                            )
                        ) {
                            ShowCard(
                                show = show,
                                onClick = { onShowClick(show.id) },
                                onFavoriteClick = { viewModel.removeFromFavorites(show) }
                            )
                        }
                    }
                }
            }
        }
    }
}
