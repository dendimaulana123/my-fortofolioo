package com.example.rmn.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EducationScreen() {
    var pendidikan by remember {
        mutableStateOf(
            listOf(
                Triple("MI Darussalam", "", "2010-2016"),
                Triple("MTs Assalamiyah", "", "2017-2019"),
                Triple("SMK PGRI Maja", "Otomatisasi dan Tata Kelola Perkantoran", "2020-2023")
            )
        )
    }
    var showDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }
    var tempNama by remember { mutableStateOf("") }
    var tempJurusan by remember { mutableStateOf("") }
    var tempTahun by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2196F3),
                        Color(0xFF9C27B0)
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
                text = "Riwayat Pendidikan",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF512DA8)
            )
            Spacer(modifier = Modifier.height(16.dp))
            pendidikan.forEachIndexed { idx, item ->
                EducationItem(
                    title = item.first,
                    institution = item.second,
                    year = item.third,
                    onEdit = {
                        editIndex = idx
                        tempNama = item.first
                        tempJurusan = item.second
                        tempTahun = item.third
                        showDialog = true
                    },
                    onDelete = {
                        pendidikan = pendidikan.toMutableList().also { it.removeAt(idx) }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Button(onClick = {
                editIndex = -1
                tempNama = ""
                tempJurusan = ""
                tempTahun = ""
                showDialog = true
            }) {
                Text("Tambah Pendidikan")
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (editIndex == -1) "Tambah Pendidikan" else "Edit Pendidikan") },
            text = {
                Column {
                    OutlinedTextField(
                        value = tempNama,
                        onValueChange = { tempNama = it },
                        label = { Text("Nama Sekolah") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = tempJurusan,
                        onValueChange = { tempJurusan = it },
                        label = { Text("Jurusan (opsional)") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = tempTahun,
                        onValueChange = { tempTahun = it },
                        label = { Text("Tahun") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (tempNama.isNotBlank() && tempTahun.isNotBlank()) {
                        if (editIndex == -1) {
                            pendidikan = pendidikan + Triple(tempNama, tempJurusan, tempTahun)
                        } else {
                            pendidikan = pendidikan.toMutableList().also {
                                it[editIndex] = Triple(tempNama, tempJurusan, tempTahun)
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
fun EducationItem(title: String, institution: String, year: String, onEdit: () -> Unit, onDelete: () -> Unit) {
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
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(text = title, fontSize = 17.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = Color(0xFF512DA8))
                if (institution.isNotEmpty()) {
                    Text(text = institution, fontSize = 15.sp, color = Color(0xFF1976D2))
                }
                Text(text = year, fontSize = 14.sp, color = Color(0xFF9C27B0))
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