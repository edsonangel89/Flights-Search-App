package com.example.flightssearchapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightssearchapp.ui.components.FlightCardsList
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    vm: SearchViewModel,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        SearchForm(
            label = label,
            value = value,
            onValueChange = onValueChange,
            vm = vm
        )
    }
}

@Composable
fun SearchForm(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    vm: SearchViewModel = viewModel()
) {
    var textF by remember { mutableStateOf(TextFieldValue(vm.airId)) }
    var optionsList = vm.airportSearches
    var coroutine = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var posibleFlights = vm.posibleFlightsList
    val iconStates by vm.flightStates.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(40.dp))

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

        Scaffold (
            content = {
                it
                Box {
                    if (optionsList.isNotEmpty()) {
                        LazyColumn(
                            state = rememberLazyListState(),
                            modifier = Modifier
                                .padding(16.dp)
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
                }
                if (optionsList.isEmpty()) {
                    FlightCardsList(
                        flightList = posibleFlights,
                        flightStates =  iconStates,
                        vm = vm
                    )
                }
            }
        )
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