package raf.console.zickreee.presentation.screens.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import raf.console.zickreee.domain.models.CounterItemScreen
import raf.console.zickreee.domain.models.CounterItem
import raf.console.zickreee.presentation.viewmodel.CounterViewModel
import raf.console.zickreee.util.VKBannerAd

@Composable
fun CounterListScreen(
    viewModel: CounterViewModel,
    navController: NavController
) {
    val counters by viewModel.counters.collectAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var currentCounter by remember { mutableStateOf<CounterItem?>(null) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogTarget by remember { mutableStateOf("") }

    // Функция для показа диалога добавления/редактирования
    fun showAddOrEditDialog(counter: CounterItem? = null) {
        currentCounter = counter
        dialogTitle = counter?.title ?: ""
        dialogTarget = counter?.target?.toString() ?: ""
        showDialog = true
    }

    // Функция сохранения
    fun saveCounter() {
        val target = dialogTarget.toIntOrNull() ?: 0
        if (dialogTitle.isNotBlank() && target > 0) {
            if (currentCounter == null) {
                // Добавление нового счетчика
                viewModel.insert(dialogTitle, target)
            } else {
                // Обновление существующего
                viewModel.update(currentCounter!!.copy(
                    title = dialogTitle,
                    target = target
                ))
            }
            showDialog = false
        }
    }

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                // Новая маленькая кнопка с иконкой дома
                FloatingActionButton(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.size(40.dp),
                    elevation = FloatingActionButtonDefaults.elevation(4.dp)
                ) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Домой",
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Существующая кнопка добавления
                FloatingActionButton(
                    onClick = { showAddOrEditDialog() },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Add, "Добавить счетчик")
                }
            }
        }
    ) { padding ->
        if (counters.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Нет счетчиков", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item {
                    VKBannerAd(1806007)
                }
                items(counters) { counter ->
                    CounterItemScreen(
                        counter = counter,
                        onItemClick = {
                            navController.navigate("counterDetail/${counter.id}")
                        },
                        onEditClick = { showAddOrEditDialog(it) },
                        onDeleteClick = { viewModel.delete(it) }
                    )
                }
            }
        }

        // Диалоговое окно добавления/редактирования
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(if (currentCounter == null) "Добавить счетчик" else "Редактировать счетчик")
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = dialogTitle,
                            onValueChange = { dialogTitle = it },
                            label = { Text("Название счетчика") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = dialogTarget,
                            onValueChange = { if (it.all { c -> c.isDigit() }) dialogTarget = it },
                            label = { Text("Цель") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { saveCounter() },
                        enabled = dialogTitle.isNotBlank() && dialogTarget.isNotBlank()
                    ) {
                        Text(if (currentCounter == null) "Добавить" else "Сохранить")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}