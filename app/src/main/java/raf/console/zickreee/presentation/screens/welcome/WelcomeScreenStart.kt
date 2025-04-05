package raf.console.zickreee.presentation.screens.welcome

import android.content.Context
import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.archnotes.utils.ChromeCustomTabUtil
import raf.console.zickreee.R
import java.io.IOException


@Composable
fun WelcomeScreenStart (
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isAgreementChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp, 32.dp, 8.dp, 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))

        Image(
            painter = painterResource(id = R.drawable.letter_uppercase_circle_z_svgrepo_com),
            contentDescription = null,
            modifier = Modifier
                .size(164.dp)
                .clip(RoundedCornerShape(50))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.welcome_screen_title),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(id = R.string.welcome_screen_message),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        // Чекбокс с соглашением
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clickable { isAgreementChecked = !isAgreementChecked }
        ) {
            Checkbox(
                checked = isAgreementChecked,
                onCheckedChange = { isAgreementChecked = it },
                modifier = Modifier.padding(end = 8.dp)
            )
            val agreementText = buildAnnotatedString {
                append("Я принимаю условия ")

                // Пользовательское соглашение (жирное + ссылка)
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                pushStringAnnotation(
                    tag = "TERMS",
                    annotation = "https://github.com/Raf0707/Zickreee_Android/blob/master/UserAgreement.txt"
                )
                append("Пользовательского соглашения")
                pop()
                pop()

                append(", ")

                // Пользовательское соглашение (жирное + ссылка)
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                pushStringAnnotation(
                    tag = "TERMS",
                    annotation = "https://github.com/Raf0707/Zickreee_Android/blob/master/LICENSE.md"
                )
                append("Лицензионного соглашения")
                pop()
                pop()

                append(", ")

                // Политика конфиденциальности (жирное + ссылка)
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                pushStringAnnotation(
                    tag = "POLICY",
                    annotation = "https://github.com/Raf0707/Zickreee_Android/blob/master/Privacy%20Policy.md"
                )
                append("Политику конфиденциальности")
                pop()
                pop()

                append(" и соглашаюсь на обработку персональных данных.")
            }

            ClickableText(
                text = agreementText,
                modifier = Modifier.fillMaxWidth(),
                onClick = { offset ->
                    agreementText.getStringAnnotations("TERMS", offset, offset)
                        .firstOrNull()?.let { annotation ->
                            ChromeCustomTabUtil.openUrl(context, annotation.item)
                        }
                    agreementText.getStringAnnotations("POLICY", offset, offset)
                        .firstOrNull()?.let { annotation ->
                            ChromeCustomTabUtil.openUrl(context, annotation.item)
                        }
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onStartClick,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            enabled = isAgreementChecked // Кнопка активна только при принятии условий
        ) {
            Text(
                text = stringResource(id = R.string.continue_button),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}