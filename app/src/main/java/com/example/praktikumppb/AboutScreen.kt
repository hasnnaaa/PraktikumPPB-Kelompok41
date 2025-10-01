package com.example.praktikumppb

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Profile(
    val name: String,
    val nim: String,
    val group: String,
    val shift: String,
    @DrawableRes val imageRes: Int
)

val profiles = listOf(
    Profile("Hasnaa' Amalia Qurratu'aini ", "21120123140155", "41", "7", R.drawable.profile_hasnaa),
    Profile("Laurentcia Dormauli Harianja", "21120123140156", "41", "7", R.drawable.profile_lauren),
    Profile("Fawnia Belvandrya Naira Aqla", "21120123140173", "41", "7", R.drawable.profile_belva),
    Profile("Naila Azizah Berliani", "21120123120005", "41", "7", R.drawable.profile_naila)
)

@Composable
fun AboutScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "About Page",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Aplikasi Praktikum PPB dengan Jetpack\u00A0Compose", textAlign = TextAlign.Center)
                    Text("Dibuat oleh Kelompok 41", textAlign = TextAlign.Center)
                }
            }
        }


        items(profiles) { profile ->
            ProfileCard(profile = profile)
            Spacer(modifier = Modifier.height(16.dp)) // Beri jarak antar card profil
        }
    }
}

@Composable
fun ProfileCard(profile: Profile) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(

                painter = painterResource(id = profile.imageRes),
                contentDescription = "Foto profil ${profile.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "NIM: ${profile.nim}")
                Text(text = "Kelompok: ${profile.group}")
                Text(text = "Shift: ${profile.shift}")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview(){
    AboutScreen()
}
