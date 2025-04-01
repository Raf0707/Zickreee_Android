package raf.console.zickreee.presentation.screens.counter

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import raf.console.zickreee.domain.models.CounterItem
import raf.console.zickreee.presentation.viewmodel.CounterViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterDetailScreen(
    counterId: Long,
    viewModel: CounterViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // Состояния для диалогов
    var showIncrementDialog by remember { mutableStateOf(false) }
    var showDecrementDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }
    var operationValue by remember { mutableStateOf("") }

    // Регулярное выражение для чисел
    val numberRegex = remember { Regex("^\\d*$") }

    // Состояния UI
    val counterState by viewModel.currentCounter.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }
    var progress by remember { mutableIntStateOf(0) }
    var wasGoalAchieved by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var currentTarget by remember { mutableIntStateOf(0) }

    // Виброотклик и уведомления
    val context = LocalContext.current
    val vibrator = remember { context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Загрузка данных
    LaunchedEffect(counterId) {
        viewModel.loadCounterById(counterId)
    }

    // Обновление полей при изменении состояния
    LaunchedEffect(counterState) {
        counterState?.let {
            title = it.title
            target = it.target.toString()
            progress = it.progress
            currentTarget = it.target
            wasGoalAchieved = it.target > 0 && progress >= it.target
        }
    }

    // Проверка достижения цели
    LaunchedEffect(progress, currentTarget) {
        val targetReached = currentTarget > 0 && progress >= currentTarget

        if (targetReached && !wasGoalAchieved) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            wasGoalAchieved = true
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Цель достигнута. Да вознаградит вас Аллах",
                    duration = SnackbarDuration.Long
                )
            }
        } else if (progress < currentTarget) {
            wasGoalAchieved = false
        }
    }

    // Функция немедленного сохранения изменений
    fun saveImmediately() {
        scope.launch {
            try {
                val newTarget = target.toIntOrNull() ?: currentTarget
                val updatedCounter = CounterItem(
                    id = counterId,
                    title = title,
                    target = newTarget,
                    progress = progress
                )
                viewModel.update(updatedCounter)
                currentTarget = newTarget
            } catch (e: Exception) {
                snackbarHostState.showSnackbar("Ошибка сохранения: ${e.message}")
            }
        }
    }

    // Функция сохранения изменений при редактировании
    fun saveChanges() {
        val newTarget = target.toIntOrNull() ?: 10
        if (newTarget <= 0) {
            scope.launch {
                snackbarHostState.showSnackbar("Цель должна быть больше 0")
            }
            return
        }

        isSaving = true
        scope.launch {
            try {
                val updatedCounter = CounterItem(
                    id = counterId,
                    title = title,
                    target = newTarget,
                    progress = progress
                )
                viewModel.update(updatedCounter)
                currentTarget = newTarget
                isEditing = false
                focusManager.clearFocus()
            } catch (e: Exception) {
                snackbarHostState.showSnackbar("Ошибка сохранения: ${e.message}")
            } finally {
                isSaving = false
            }
        }
    }

    // Диалог увеличения
    if (showIncrementDialog) {
        AlertDialog(
            onDismissRequest = { showIncrementDialog = false },
            title = { Text("Увеличить на") },
            text = {
                OutlinedTextField(
                    value = operationValue,
                    onValueChange = { if (it.matches(numberRegex)) operationValue = it },
                    label = { Text("Введите число") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    val value = operationValue.toIntOrNull() ?: 0
                    progress += value
                    saveImmediately()
                    operationValue = ""
                    showIncrementDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showIncrementDialog = false
                    operationValue = ""
                }) {
                    Text("Отмена")
                }
            }
        )
    }

    // Диалог уменьшения
    if (showDecrementDialog) {
        AlertDialog(
            onDismissRequest = { showDecrementDialog = false },
            title = { Text("Уменьшить на") },
            text = {
                OutlinedTextField(
                    value = operationValue,
                    onValueChange = { if (it.matches(numberRegex)) operationValue = it },
                    label = { Text("Введите число") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    val value = operationValue.toIntOrNull() ?: 0
                    progress = maxOf(0, progress - value)
                    saveImmediately()
                    operationValue = ""
                    showDecrementDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDecrementDialog = false
                    operationValue = ""
                }) {
                    Text("Отмена")
                }
            }
        )
    }

    // Диалог сброса до значения
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Сбросить до") },
            text = {
                OutlinedTextField(
                    value = operationValue,
                    onValueChange = { if (it.matches(numberRegex)) operationValue = it },
                    label = { Text("Введите число") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    val value = operationValue.toIntOrNull() ?: 0
                    progress = value
                    saveImmediately()
                    operationValue = ""
                    showResetDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showResetDialog = false
                    operationValue = ""
                }) {
                    Text("Отмена")
                }
            }
        )
    }

    // Диалог подтверждения удаления
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Подтвердить удаление") },
            text = { Text("Вы уверены, что хотите удалить счетчик? Отменить это действие будет невозможно") },
            confirmButton = {
                Button(
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                        viewModel.delete(counterState!!)
                        onBack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    // Диалог подтверждения сброса
    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            title = { Text("Подтвердить сброс") },
            text = { Text("Вы уверены, что хотите сбросить прогресс счетчика? Отменить это действие будет невозможно") },
            confirmButton = {
                Button(
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                        progress = 0
                        saveImmediately()
                        showResetConfirmDialog = false
                    }
                ) {
                    Text("Сбросить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    if (isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    } else {
                        Text(if (isEditing) "Редактирование" else "Счетчик")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isEditing) {
                            saveChanges()
                        }
                        onBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (isEditing) {
                                saveChanges()
                            } else {
                                isEditing = true
                            }
                        },
                        enabled = !isSaving
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Сохранить" else "Редактировать"
                        )
                    }

                    if (!isEditing && counterState != null) {
                        IconButton(
                            onClick = { showDeleteDialog = true },
                            enabled = !isSaving
                        ) {
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
            if (counterState == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // Поля редактирования
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        if (!isEditing) saveImmediately()
                    },
                    label = { Text("Название") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing && !isSaving,
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = target,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(numberRegex)) {
                            target = it
                            if (!isEditing) saveImmediately()
                        }
                    },
                    label = { Text("Цель") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing && !isSaving,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Большая кликабельная область счетчика
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            enabled = !isSaving,
                            onClick = {
                                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                                progress++
                                saveImmediately()
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = progress.toString(),
                        style = MaterialTheme.typography.displayLarge
                    )

                    // Иконки управления в правом нижнем углу
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Row {
                            IconButton(
                                onClick = { showIncrementDialog = true },
                                modifier = Modifier.size(32.dp),
                                enabled = !isSaving
                            ) {
                                Icon(Icons.Default.Add, "+")
                            }

                            IconButton(
                                onClick = { showDecrementDialog = true },
                                modifier = Modifier.size(32.dp),
                                enabled = !isSaving
                            ) {
                                Icon(Icons.Default.Remove, "-")
                            }

                            IconButton(
                                onClick = { showResetDialog = true },
                                modifier = Modifier.size(32.dp),
                                enabled = !isSaving
                            ) {
                                Icon(Icons.Default.Refresh, "Сброс")
                            }
                        }
                    }
                }

                Text(
                    text = "Нажмите для увеличения",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопки управления
                OutlinedButton(
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                        if (progress > 0) progress--
                        saveImmediately()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isSaving
                ) {
                    Text("Уменьшить")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                        showResetConfirmDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isSaving
                ) {
                    Text("Сбросить")
                }
            }
        }
    }
}