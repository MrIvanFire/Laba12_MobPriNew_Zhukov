package com.example.laba12_mobprinew_zhukov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.laba12_mobprinew_zhukov.data.local.entity.NoteEntity
import com.example.laba12_mobprinew_zhukov.ui.theme.Laba12_MobPriNew_ZhukovTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laba12_MobPriNew_ZhukovTheme {
                NotesScreen()
            }
        }
    }
}

@Composable
fun NotesScreen() {
    val context = LocalContext.current
    val dao = remember {
        (context.applicationContext as NotesApp).database.noteDao()
    }

    var notes by remember { mutableStateOf<List<NoteEntity>>(emptyList()) }

    LaunchedEffect(dao) {
        dao.getAll().collect { notes = it }
    }

    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Заголовок") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Содержание") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    scope.launch {
                        dao.insert(NoteEntity(title = title, content = content))
                    }
                    title = ""
                    content = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notes) { note ->
                Column {
                    Text(text = "ID: ${note.id} | Заголовок: ${note.title}")
                    Text(text = "Содержание: ${note.content}")

                    Button(onClick = {
                        scope.launch {
                            dao.delete(note)
                        }
                    }) {
                        Text("Удалить")
                    }
                }
            }
        }
    }
}