package com.apparchitecture.app.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apparchitecture.app.ui.main.viewmodel.test.MyViewModel

@Composable
fun MainScreen(
    viewModel: MyViewModel = hiltViewModel(),
    onClickDetail: (Int) -> Unit
){

    var inputId by remember { mutableStateOf("0") }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(text = "Main Screen")

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            value = inputId,
            onValueChange = {
                inputId = it
            },
            label = { Text(text = "Id") }
        )

        Button(
            onClick = {
                onClickDetail(inputId.toInt())
            }
        ) {
            Text(text = "Detail")
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                viewModel.test()
            }
        ) {
            Text(text = "Test")
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                viewModel.onAddClicked()
            }
        ) {
            Text(text = "Add")
        }

    }

}