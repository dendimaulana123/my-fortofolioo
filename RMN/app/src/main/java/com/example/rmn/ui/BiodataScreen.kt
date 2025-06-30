package com.example.rmn.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmn.R

// Data class untuk biodata
data class Biodata(
    var nama: String,
    var profesi: String,
    var keahlian: String
)

@Composable
fun BiodataScreen() {
    var biodataList by remember {
        mutableStateOf(
            listOf(
                Biodata(
                    nama = "Rizqi muhamad nur",
                    profesi = "Front-End Engineer",
                    keahlian = "- Menyesuaikan kebutuhan front-end\n- UI/UX Design\n- React, Kotlin, Jetpack Compose"
                )
            )
        )
    }
    var showDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }
    var tempNama by remember { mutableStateOf("") }
    var tempProfesi by remember { mutableStateOf("") }
    var tempKeahlian by remember { mutableStateOf("") }

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
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Daftar Biodata",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = Color.White,
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF512DA8), Color(0xFF2196F3))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            biodataList.forEachIndexed { idx, biodata ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    BiodataItem(
                        biodata = biodata,
                        onEdit = {
                            editIndex = idx
                            tempNama = biodata.nama
                            tempProfesi = biodata.profesi
                            tempKeahlian = biodata.keahlian
                            showDialog = true
                        },
                        onDelete = {
                            biodataList = biodataList.toMutableList().also { it.removeAt(idx) }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        // FloatingActionButton untuk tambah
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    editIndex = -1
                    tempNama = ""
                    tempProfesi = ""
                    tempKeahlian = ""
                    showDialog = true
                },
                containerColor = Color(0xFF512DA8),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Biodata")
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF512DA8))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (editIndex == -1) "Tambah Biodata" else "Edit Biodata")
                }
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = tempNama,
                        onValueChange = { tempNama = it },
                        label = { Text("Nama") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = tempProfesi,
                        onValueChange = { tempProfesi = it },
                        label = { Text("Profesi") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = tempKeahlian,
                        onValueChange = { tempKeahlian = it },
                        label = { Text("Keahlian") },
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (tempNama.isNotBlank() && tempProfesi.isNotBlank()) {
                        if (editIndex == -1) {
                            biodataList = biodataList + Biodata(tempNama, tempProfesi, tempKeahlian)
                        } else {
                            biodataList = biodataList.toMutableList().also {
                                it[editIndex] = Biodata(tempNama, tempProfesi, tempKeahlian)
                            }
                        }
                        showDialog = false
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF512DA8))) {
                    Text("Simpan", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun BiodataItem(biodata: Biodata, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(
                    listOf(Color(0xFF2196F3), Color(0xFF9C27B0))
                ),
                shape = RoundedCornerShape(24.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.98f)),
        elevation = CardDefaults.cardElevation(16.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Foto Profil",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(
                        width = 4.dp,
                        brush = Brush.sweepGradient(
                            listOf(Color(0xFF2196F3), Color(0xFF9C27B0), Color(0xFF2196F3))
                        ),
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = biodata.nama,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF512DA8)
            )
            Text(
                text = biodata.profesi,
                fontSize = 20.sp,
                color = Color(0xFF1976D2)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Keahlian:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF512DA8)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = biodata.keahlian,
                fontSize = 16.sp,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF512DA8))
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color(0xFFD32F2F))
                }
            }
        }
    }
} 