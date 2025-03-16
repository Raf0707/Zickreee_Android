package raf.console.zickreee.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import raf.console.zickreee.components.NameItem
import raf.console.zickreee.components.Position
import raf.console.zickreee.data.Name

@Composable
fun NamesScreen(
    arabicNames: List<String>,
    transcriptNames: List<String>,
    translateNames: List<String>,
    infoNames: List<String>
) {
    // Объединяем 4 списка в один список объектов Name
    val names = arabicNames
        .zip(transcriptNames) { arabic, transcript -> Pair(arabic, transcript) }
        .zip(translateNames) { (arabic, transcript), translate -> Triple(arabic, transcript, translate) }
        .zip(infoNames) { (arabic, transcript, translate), info ->
            Name(
                arabic_name = arabic,
                transcript_name = transcript,
                translate_name = translate,
                info_name = info
            )
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 64.dp)
    ) {
        items(names.size) { index ->
            NameItem(
                arabicName = names[index].arabic_name,
                transcriptName = names[index].transcript_name,
                translateName = names[index].translate_name,
                infoName = names[index].info_name,
                position = when (index) {
                    0 -> Position.TOP
                    names.size - 1 -> Position.BOTTOM
                    else -> Position.CENTER
                }
            )
            Spacer(modifier = Modifier.height(8.dp)) // Отступ между карточками
        }
    }
}