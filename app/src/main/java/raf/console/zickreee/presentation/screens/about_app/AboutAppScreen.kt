package raf.console.zickreee.presentation.screens.about_app


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import raf.console.archnotes.utils.ChromeCustomTabUtil
import raf.console.archnotes.utils.FeedbackHelper
import raf.console.zickreee.R
import raf.console.zickreee.util.ChromeUtilNoTranslate


@Composable
fun AboutScreen(
    //modifier: Modifier = Modifier
    navController: NavController
) {
    val context = LocalContext.current
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 36.dp, 16.dp, 16.dp)

    ) {
        item {
            ElevatedCard(shape = ShapeDefaults.Large) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.letter_uppercase_circle_z_svgrepo_com),
                        contentDescription = "App Icon",
                        modifier = Modifier.size(128.dp)
                    )
                    Text(text = stringResource(R.string.app_name))
                    Text(text = "from R&R")

                    ListItem(
                        title = stringResource(R.string.back),
                        subtitle = stringResource(R.string.back_msg),
                        icon = painterResource(id = R.drawable.baseline_home_24),
                        onClick = {

                            navController.navigate("home") // Переход на главный экран

                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))

                    ListItem(
                        title = stringResource(R.string.license),
                        subtitle = stringResource(R.string.gnu_gpl_3_0),
                        icon = painterResource(id = R.drawable.license),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://github.com/Raf0707/Zickreee_Android/blob/master/LICENSE.md",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))
                    ListItem(
                        title = stringResource(R.string.privacy_policy),
                        subtitle = stringResource(R.string.privacy_policy_message_1),
                        icon = painterResource(id = R.drawable.privacy_tip),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://github.com/Raf0707/Zickreee_Android/blob/master/Privacy%20Policy.md",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))

                    ListItem(
                        title = stringResource(R.string.web),
                        subtitle = stringResource(R.string.web_mes),
                        icon = painterResource(id = R.drawable.web),
                        onClick = {

                            ChromeUtilNoTranslate.openUrl(
                                context = context,
                                url = "https://raf0707.github.io/zickreee_web",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))

                    ListItem(
                        title = stringResource(R.string.bug_report_title),
                        subtitle = stringResource(R.string.bug_report_subtitle),
                        icon = painterResource(id = R.drawable.bug_report_24px),
                        onClick = {
                            FeedbackHelper.SendEmail(context)
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))
                    ListItem(
                        title = stringResource(R.string.rate_the_app_title),
                        subtitle = stringResource(R.string.rate_the_app_subtitle),
                        icon = painterResource(id = R.drawable.rate_review_24px),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://www.rustore.ru/catalog/app/raf.console.zickreee",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))
                    ListItem(
                        title = stringResource(R.string.other_apps_title),
                        subtitle = stringResource(R.string.other_apps_subtitle),
                        icon = painterResource(id = R.drawable.apps_24px),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://www.rustore.ru/catalog/developer/90b1826e",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))

                    ListItem(
                        title = stringResource(R.string.shareapp),
                        subtitle = stringResource(R.string.shareapp_mes),
                        icon = painterResource(id = R.drawable.shareapp),
                        onClick = {
                            // Копируем ссылку в буфер обмена
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Скачайте приложение Zickreee в каталоге RuStore", "https://www.rustore.ru/catalog/app/raf.console.zickreee")
                            clipboard.setPrimaryClip(clip)

                            // Создаем Intent для диалога "Поделиться"
                            val shareIntent = Intent(Intent.ACTION_SEND)
                            shareIntent.type = "text/plain"
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.rustore.ru/catalog/app/raf.console.zickreee")

                            // Запускаем диалог "Поделиться"
                            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.shareapp)))
                        }
                    )

                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ElevatedCard(shape = ShapeDefaults.Large) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    /*ListItem(
                        title = stringResource(R.string.website_title),
                        subtitle = stringResource(R.string.website_subtitle),
                        icon = painterResource(id = R.drawable.web_24px),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://byteflipper.web.app",
                            )
                        }
                    )*/
                    //HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))
                    ListItem(
                        title = stringResource(R.string.vk_title),
                        subtitle = stringResource(R.string.vk_subtitle),
                        icon = painterResource(id = R.drawable.vk_24),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://vk.com/mahabbaa",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))
                    ListItem(
                        title = stringResource(R.string.telegram_title),
                        subtitle = stringResource(R.string.telegram_subtitle),
                        icon = painterResource(id = R.drawable.telegram_24),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://t.me/ibnRustum",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))
                    ListItem(
                        title = stringResource(R.string.github_title),
                        subtitle = stringResource(R.string.github_subtitle),
                        icon = painterResource(id = R.drawable.github_24),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://github.com/Raf0707",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))
                    ListItem(
                        title = "Исходный код",
                        subtitle = "Открытый исходный код приложения Zickreee на GitHub",
                        icon = painterResource(id = R.drawable.code_24px),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://github.com/Raf0707/Zickreee_Android",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))
                    ListItem(
                        title = "Версия",
                        subtitle = "1.0.0",
                        icon = painterResource(id = R.drawable.update_24px)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ElevatedCard(shape = ShapeDefaults.Large) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.book_open),
                        contentDescription = "Book Icon",
                        modifier = Modifier.size(128.dp)
                    )
                    Text(
                        text = "Любите читать?",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Читайте любимые книги и сортируйте их по категориям в RafBook Reader",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "RafBook Reader - одна из лучших читалок в каталоге RuStore, поддерживающая форматы .pdf, .txt, .epub, .fb2, .zip, .html, .htm, .md",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp),
                        textAlign = TextAlign.Center
                    )
                    ListItem(
                        title = "Скачать RafBook Reader",
                        subtitle = "Скачать приложение из каталога RuStore",
                        icon = painterResource(id = R.drawable.book_open),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://www.rustore.ru/catalog/app/raf.console.chitalka",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp))

                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ElevatedCard(shape = ShapeDefaults.Large) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.code_24px),
                        contentDescription = "Code Icon",
                        modifier = Modifier.size(128.dp)
                    )
                    Text(
                        text = "Raf</>Console Studio",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Вы программист и ищете комьюнити для общения, проектов и встреч? (встречи в Москве)",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Или вы просто хотите научиться программированию, но не знаете с чего начать?",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Или просто хотите пообщаться?",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp),
                        textAlign = TextAlign.Center
                    )
                    ListItem(
                        title = "Перейти в Raf</>Console",
                        subtitle = "Raf</>Console Studio - беседа в телеграм",
                        icon = painterResource(id = R.drawable.code_24px),
                        onClick = {
                            ChromeCustomTabUtil.openUrl(
                                context = context,
                                url = "https://t.me/rafConsoleStudio",
                            )
                        }
                    )
                    HorizontalDivider(Modifier.padding(16.dp, 0.dp, 16.dp, 32.dp))

                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ListItem(
    title: String = "Title",
    subtitle: String = "Subtitle",
    icon: Painter = painterResource(id = R.drawable.ic_launcher_foreground),
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
