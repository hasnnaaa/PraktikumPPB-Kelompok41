package com.example.praktikumppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.input.ImeAction
import com.example.praktikumppb.model.SortOrder

@Composable
fun CharacterScreen(navController: NavController, viewModel: CharacterViewModel = viewModel()) {
    val characterList by viewModel.characterList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.fetchTopCharacters()
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            label = { Text("Search Character...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.searchCharacters(searchQuery)
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tombol Sorting
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { viewModel.sortCharacterList(SortOrder.ASC) },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text("A-Z")
            }
            Button(
                onClick = { viewModel.sortCharacterList(SortOrder.DESC) },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text("Z-A")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(characterList) { character ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            navController.navigate(Screen.CharacterDetail.createRoute(character.mal_id))
                        }
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(character.images.jpg.image_url),
                            contentDescription = character.name,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(character.name, fontWeight = FontWeight.Bold)
                            if (character.nicknames.isNotEmpty()) {
                                Text("Nickname: ${character.nicknames.first()}")
                            }
                        }
                    }
                }
            }
        }
    }
}