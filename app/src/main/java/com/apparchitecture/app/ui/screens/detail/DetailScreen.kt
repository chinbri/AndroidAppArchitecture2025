package com.apparchitecture.app.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(id: Long, onBackPressed: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize().background(color = Color.LightGray)) {

        Text(text = "Detail Screen + $id")

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                onBackPressed()
            }
        ) {
            Text(text = "Back")
        }

    }

}