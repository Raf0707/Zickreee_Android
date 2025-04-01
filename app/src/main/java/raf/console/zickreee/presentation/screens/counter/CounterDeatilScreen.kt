package raf.console.zickreee.presentation.screens.counter

import android.content.Context
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.zickreee.domain.models.CounterItem
import raf.console.zickreee.presentation.viewmodel.CounterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterDetailScreen(
    counterId: Int?,
    viewModel: CounterViewModel,
    onBack: () -> Unit
) {
    val counterState by viewModel.currentCounter.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }
    var progress by remember { mutableIntStateOf(0) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val vibrator = remember { context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

    LaunchedEffect(counterState) {
        counterState?.let {
            title = it.title
            target = it.target.toString()
            progress = it.progress
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Редактирование" else "Счетчик") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isEditing) {
                            // Сохраняем изменения
                            val newTarget = target.toIntOrNull() ?: 10
                            val newCounter = CounterItem(
                                id = counterId ?: 0,
                                title = title,
                                target = newTarget,
                                progress = progress
                            )
                            if (counterId == null) {
                                viewModel.insert(newCounter)
                            } else {
                                viewModel.update(newCounter)
                            }
                            isEditing = false
                            focusManager.clearFocus()
                        }
                        onBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isEditing) {
                            // Сохраняем
                            val newTarget = target.toIntOrNull() ?: 10
                            if (newTarget <= 0) {
                                Toast.makeText(context, "Цель должна быть больше 0", Toast.LENGTH_SHORT).show()
                                return@IconButton
                            }
                            val newCounter = CounterItem(
                                id = counterId ?: 0,
                                title = title,
                                target = newTarget,
                                progress = progress
                            )
                            if (counterId == null) {
                                viewModel.insert(newCounter)
                            } else {
                                viewModel.update(newCounter)
                            }
                            isEditing = false
                            focusManager.clearFocus()
                        } else {
                            // Включаем редактирование
                            isEditing = true
                        }
                    }) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Сохранить" else "Редактировать"
                        )
                    }

                    if (!isEditing && counterId != null) {
                        IconButton(onClick = {
                            vibrator.vibrate(50)
                            viewModel.delete(counterState!!)
                            onBack()
                        }) {
                            Icon(Icons.Default.Delete, "Удалить")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth(),
                enabled = isEditing,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = target,
                onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) target = it },
                label = { Text("Цель") },
                modifier = Modifier.fillMaxWidth(),
                enabled = isEditing,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = progress.toString(),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        vibrator.vibrate(50)
                        progress++
                        if (counterState != null) {
                            viewModel.update(counterState!!.copy(progress = progress))
                        }
                    },
                textAlign = TextAlign.Center
            )

            Text(
                text = "Нажмите для увеличения",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    vibrator.vibrate(50)
                    progress = 0
                    if (counterState != null) {
                        viewModel.update(counterState!!.copy(progress = progress))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Сбросить")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    vibrator.vibrate(50)
                    if (progress > 0) progress--
                    if (counterState != null) {
                        viewModel.update(counterState!!.copy(progress = progress))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Уменьшить")
            }
        }
    }
}