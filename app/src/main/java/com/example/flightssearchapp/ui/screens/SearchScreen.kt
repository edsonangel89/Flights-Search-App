package com.example.flightssearchapp.ui.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    vm: SearchViewModel,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        SearchForm(
            label = label,
            value = value,
            onValueChange = onValueChange,
            vm = vm,
            navigate = {
                Log.d("SEARCHFORM", "TEST")
                navigate.invoke()
                Log.d("SEARCHFORM", "TEST")
            }
        )
    }
}

@Composable
fun SearchForm(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    navigate: () -> Unit,
    vm: SearchViewModel = viewModel()
) {
    var textF by remember { mutableStateOf(TextFieldValue(vm.airId)) }
    var optionsList = vm.airportSearches
    var coroutine = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var posibleFlights = vm.posibleFlightsList
    var iconStates by remember { mutableStateOf(vm.flightStates) }
    var favList = vm._favList.value

    Column(
        verticalArrangement = Arrangement.Top,
        /*horizontalAlignment = Alignment.CenterHorizontally*/
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Row(modifier = Modifier) {
                OutlinedTextField(
                    label = { Text(text = stringResource(label)) },
                    value = textF,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        coroutine.launch {
                            if (it.text.isEmpty()) {
                                onValueChange("")
                                textF = it
                            } else {
                                onValueChange(it.text)
                                textF = it
                            }
                        }
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp
                        )
                        .fillMaxWidth(0.95f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                vm.posibleFlights(textF.text)
                                textF = TextFieldValue("")
                                onValueChange("")
                                Log.d("ICONBUTTON", "TEST")
                                navigate.invoke()
                                Log.d("ICONBUTTON", "TEST")
                            }
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    },
                    maxLines = 1,
                    singleLine = true
                )
            }
        }
        /*Row(modifier = Modifier) {*/
        if (optionsList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    items(optionsList) {
                        Surface(
                            onClick = {
                                textF = TextFieldValue(
                                    text = "${it.first} - ${it.second}",
                                    selection = TextRange("${it.first} - ${it.second}".length)
                                )
                            }
                        ) {
                            Text(
                                text = "${it.first} - ${it.second}"
                            )
                        }
                    }
                }
            }
        else {
            if(favList.isNotEmpty()) {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ){
                    items(favList) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(1.dp, Color.LightGray, RectangleShape)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column (
                                    modifier = Modifier.fillMaxWidth(0.7f)
                                ){
                                    Text(text = "Departure")
                                    Text(text = it.departure)
                                    Text(text = "Destination")
                                    Text(text = it.destination)
                                }
                                Column (
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ){
                                    IconButton(
                                        onClick = {
                                            vm.deleteFavorite(it)
                                        },
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "No hay favoritos")
                }
            }
        }
        /*}*/
    }
}

/*DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    keyboardController?.show()
                }
            ) {
                optionsList.forEach {
                    DropdownMenuItem(
                        text = { Text(text = "${it.first} - ${it.second}") },
                        onClick = {
                            /*coroutine.launch {*/
                                query = "${it.first} - ${it.second}"
                                onValueChange(it.first)
                                keyboardController?.show()
                                textF = TextFieldValue(text = it.first, selection = TextRange(it.first.length))
                            /*}*/
                        }
                    )
                }
            }
        }*/