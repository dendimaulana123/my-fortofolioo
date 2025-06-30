package com.example.rmn.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActivityScreen() {
    var aktivitas by remember {
        mutableStateOf(
            listOf(
                Pair("Front-End Engineer di PT. Contoh Teknologi", "2022-sekarang"),
                Pair("Kontributor Open Source Project", ""),
                Pair("Freelance UI/UX Designer", "")
            )
        )
    }
    var showDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }
    var tempJudul by remember { mutableStateOf("") }
    var tempTahun by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2196F3), // biru
                        Color(0xFF9C27B0)  // ungu
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Aktivitas",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF512DA8)
            )
            Spacer(modifier = Modifier.height(16.dp))
            aktivitas.forEachIndexed { idx, item ->
                ActivityItem(
                    title = item.first,
                    year = item.second,
                    onEdit = {
                        editIndex = idx
                        tempJudul = item.first
                        tempTahun = item.second
                        showDialog = true
                    },
                    onDelete = {
                        aktivitas = aktivitas.toMutableList().also { it.removeAt(idx) }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Button(onClick = {
                editIndex = -1
                tempJudul = ""
                tempTahun = ""
                showDialog = true
            }) {
                Text("Tambah Aktivitas")
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (editIndex == -1) "Tambah Aktivitas" else "Edit Aktivitas") },
            text = {
                Column {
                    OutlinedTextField(
                        value = tempJudul,
                        onValueChange = { tempJudul = it },
                        label = { Text("Judul Aktivitas") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = tempTahun,
                        onValueChange = { tempTahun = it },
                        label = { Text("Tahun/Periode (opsional)") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (tempJudul.isNotBlank()) {
                        if (editIndex == -1) {
                            aktivitas = aktivitas + Pair(tempJudul, tempTahun)
                        } else {
                            aktivitas = aktivitas.toMutableList().also {
                                it[editIndex] = Pair(tempJudul, tempTahun)
                            }
                        }
                        showDialog = false
                    }
                }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun ActivityItem(title: String, year: String, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Work,
                contentDescription = null,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(text = title, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = Color(0xFF512DA8))
                if (year.isNotEmpty()) {
                    Text(text = year, fontSize = 14.sp, color = Color(0xFF9C27B0))
                }
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF512DA8))
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color(0xFFD32F2F))
            }
        }
    }
} 