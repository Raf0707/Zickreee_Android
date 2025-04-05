package raf.console.zickreee.presentation.screens.dalailhairat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.zickreee.util.VKBannerAd

@Composable
fun HowToReadScreen() {
    Scaffold(

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                VKBannerAd(1806157)
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Арабский текст
                        Text(
                            text = "День 1 (Понедельник)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Транскрипция
                        Text(
                            text = """
                                Дуа Истигфар (дуа о прощении грехов)
                                Дуа Ният (дуа намерения)
                                Дуа Фатих (или дуа ифтитах - дуа открытия)
                                Асмауль Хусна (99 имен Аллаха)
                                Дуа Тауфик
                                Имена Пророка Мухаммада Салля Ллаху алейхи уа Саллям
                                Понедельник
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                    }
                }
            }


            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Арабский текст
                        Text(
                            text = "День 2 (Вторник)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Транскрипция
                        Text(
                            text = """
                                Дуа Ният (дуа намерения)
                                Вторник
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Арабский текст
                        Text(
                            text = "День 3 (Среда)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Транскрипция
                        Text(
                            text = """
                                Дуа Ният (дуа намерения)
                                Среда
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Арабский текст
                        Text(
                            text = "День 4 (Четверг)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Транскрипция
                        Text(
                            text = """
                                Дуа Ният (дуа намерения)
                                Четверг
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Арабский текст
                        Text(
                            text = "День 5 (Пятница)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Транскрипция
                        Text(
                            text = """
                                Дуа Ният (дуа намерения)
                                Пятница
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Арабский текст
                        Text(
                            text = "День 6 (Суббота)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Транскрипция
                        Text(
                            text = """
                                Дуа Ният (дуа намерения)
                                Суббота
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Арабский текст
                        Text(
                            text = "День 7 (Воскресенье)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Транскрипция
                        Text(
                            text = """
                                Дуа Ният (дуа намерения)
                                Воскресенье
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Арабский текст
                        Text(
                            text = "День 8 / День 1 (Понедельник)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Транскрипция
                        Text(
                            text = """
                                Понедельник 2
                                Дуа Хатм (Дуа завершения)
                                Дуа Истигфар (дуа о прощении грехов)
                                Дуа Ният (дуа намерения)
                                Дуа Фатих (или дуа ифтитах - дуа открытия)
                                Асмауль Хусна (99 имен Аллаха)
                                Дуа Тауфик
                                Имена Пророка Мухаммада Салля Ллаху алейхи уа Саллям
                                Понедельник
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                    }
                }
            }

            item {
                VKBannerAd(1806160)
            }
        }
    }

}