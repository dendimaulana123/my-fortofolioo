package com.example.myfortofolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfortofolio.ui.theme.MyFortofolioTheme
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFortofolioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MultiPagePortfolioApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MultiPagePortfolioApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var showContactDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "PORTFOLIO",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Dendi Maulana",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Biodata") },
                    label = { Text("Biodata") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.School, contentDescription = "Pendidikan") },
                    label = { Text("Pendidikan") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Work, contentDescription = "Aktivitas") },
                    label = { Text("Aktivitas") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showContactDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.ContactPhone, contentDescription = "Contact")
            }
        }
    ) { paddingValues ->
        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { if (targetState > initialState) it else -it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300)) with
                slideOutHorizontally(
                    targetOffsetX = { if (targetState > initialState) -it else it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) { targetTab ->
            when (targetTab) {
                0 -> BiodataPage(modifier = Modifier.padding(paddingValues))
                1 -> EducationPage(modifier = Modifier.padding(paddingValues))
                2 -> ActivitiesPage(modifier = Modifier.padding(paddingValues))
            }
        }
        
        if (showContactDialog) {
            ContactDialog(
                onDismiss = { showContactDialog = false }
            )
        }
    }
}

@Composable
fun BiodataPage(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Header with enhanced design
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .shadow(16.dp, RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        )
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .scale(scale)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        val context = LocalContext.current
                        val resId = context.resources.getIdentifier("profil_dendi", "drawable", context.packageName)
                        if (resId != 0) {
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Profile",
                                modifier = Modifier.size(50.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    
                    // Rotating background circle
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .offset(y = (-60).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "Decoration",
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer(rotationZ = rotation),
                            tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Dendi Maulana",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Mahasiswa & Karyawan",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                    
                    // Status indicator
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    Color.Green,
                                    RoundedCornerShape(4.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Available",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
        
        // Personal Info Card with enhanced styling
        EnhancedCard(
            title = "Informasi Pribadi",
            icon = Icons.Default.Info
        ) {
            BiodataItem("Nama Lengkap", "Dendi Maulana", Icons.Default.Person)
            BiodataItem("NIM", "23050711", Icons.Default.Badge)
            BiodataItem("Alamat", "Kp. Cikareo", Icons.Default.LocationOn)
            BiodataItem("Hobi", "Bermain Futsal", Icons.Default.SportsSoccer)
            BiodataItem("Status", "Mahasiswa & Karyawan", Icons.Default.Work)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // About Me Card with enhanced content
        EnhancedCard(
            title = "Tentang Saya",
            icon = Icons.Default.Psychology
        ) {
            Text(
                text = "Saya adalah mahasiswa yang aktif kuliah sambil bekerja. Memiliki hobi bermain futsal dan berkomitmen untuk mengembangkan diri dalam bidang teknologi. Berpengalaman dalam mengelola waktu antara pekerjaan dan pendidikan.",
                textAlign = TextAlign.Justify,
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Skills section
            Text(
                text = "Keahlian:",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SkillChip("Android", Icons.Default.Android)
                SkillChip("Kotlin", Icons.Default.Code)
                SkillChip("Java", Icons.Default.Computer)
            }
        }
    }
}

@Composable
fun SkillChip(skill: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = skill,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = skill,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun EducationPage(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "education")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Education Header with enhanced design
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .shadow(16.dp, RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.tertiary,
                                MaterialTheme.colorScheme.primary
                            )
                        )
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.School,
                        contentDescription = "Education",
                        modifier = Modifier
                            .size(48.dp)
                            .scale(pulse),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Riwayat Pendidikan",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = "Perjalanan Akademik",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                    )
                }
            }
        }
        
        // Education Timeline with enhanced design
        EnhancedCard(
            title = "Pendidikan Formal",
            icon = Icons.Default.Timeline
        ) {
            EducationTimelineItem(
                "Sekolah Dasar",
                "SDN Cireundeu 1",
                "2010-2016",
                Icons.Default.School,
                MaterialTheme.colorScheme.primary,
                "Lulus dengan nilai memuaskan"
            )
            EducationTimelineItem(
                "SMP",
                "SMPN 2 Solear",
                "2016-2019",
                Icons.Default.School,
                MaterialTheme.colorScheme.secondary,
                "Aktif dalam kegiatan OSIS"
            )
            EducationTimelineItem(
                "SMA",
                "SMAN 27 Kabupaten Tangerang",
                "2019-2022",
                Icons.Default.School,
                MaterialTheme.colorScheme.tertiary,
                "Jurusan IPS, aktif dalam ekstrakurikuler"
            )
            EducationTimelineItem(
                "Perguruan Tinggi",
                "Universitas Yatsi Madani (UYM)",
                "2022-Sekarang",
                Icons.Default.School,
                MaterialTheme.colorScheme.primary,
                "Program Studi Teknologi Informasi"
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Achievements with enhanced design
        EnhancedCard(
            title = "Pencapaian Akademik",
            icon = Icons.Default.EmojiEvents
        ) {
            AchievementItem("Aktif dalam kegiatan akademik", Icons.Default.Star, "Mengikuti berbagai mata kuliah dengan semangat tinggi")
            AchievementItem("Mengikuti berbagai seminar dan workshop", Icons.Default.Event, "Terus mengembangkan skill dan pengetahuan")
            AchievementItem("Berpartisipasi dalam kegiatan kampus", Icons.Default.Group, "Aktif dalam organisasi mahasiswa")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // GPA Section
        EnhancedCard(
            title = "Prestasi Akademik",
            icon = Icons.Default.TrendingUp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GpaCard("IPK", "3.75", "Cum Laude")
                GpaCard("SKS", "24", "Selesai")
                GpaCard("Semester", "4", "Aktif")
            }
        }
    }
}

@Composable
fun GpaCard(title: String, value: String, subtitle: String) {
    Card(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun ActivitiesPage(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "activities")
    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Activities Header with enhanced design
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .shadow(16.dp, RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.tertiary,
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Work,
                        contentDescription = "Activities",
                        modifier = Modifier
                            .size(48.dp)
                            .offset(y = (bounce * 4).dp),
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Aktivitas Harian",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Text(
                        text = "Work-Life Balance",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.8f)
                    )
                }
            }
        }
        
        // Work Section with enhanced design
        EnhancedCard(
            title = "Pekerjaan",
            icon = Icons.Default.Business
        ) {
            ActivityItem("Perusahaan", "PT Rinnai Indonesia", Icons.Default.Business)
            ActivityItem("Posisi", "Karyawan", Icons.Default.Person)
            ActivityItem("Jadwal", "Shift kerja sesuai jadwal", Icons.Default.Schedule)
            ActivityItem("Lokasi", "Tangerang", Icons.Default.LocationOn)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Work progress indicator
            Text(
                text = "Pengalaman Kerja",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LinearProgressIndicator(
                progress = 0.8f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
            
            Text(
                text = "2 tahun pengalaman",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Education Section with enhanced design
        EnhancedCard(
            title = "Pendidikan",
            icon = Icons.Default.School
        ) {
            ActivityItem("Universitas", "Universitas Yatsi Madani", Icons.Default.School)
            ActivityItem("Program Studi", "Teknologi Informasi", Icons.Default.Computer)
            ActivityItem("Status", "Aktif Kuliah", Icons.Default.CheckCircle)
            ActivityItem("Semester", "4", Icons.Default.Timeline)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Study progress
            Text(
                text = "Progress Studi",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LinearProgressIndicator(
                progress = 0.5f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
            )
            
            Text(
                text = "50% selesai (4/8 semester)",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Hobbies Section with enhanced design
        EnhancedCard(
            title = "Hobi & Aktivitas",
            icon = Icons.Default.SportsSoccer
        ) {
            ActivityItem("Hobi Utama", "Bermain Futsal", Icons.Default.SportsSoccer)
            ActivityItem("Frekuensi", "2-3 kali seminggu", Icons.Default.Schedule)
            ActivityItem("Lokasi", "Lapangan futsal terdekat", Icons.Default.LocationOn)
            ActivityItem("Posisi", "Striker", Icons.Default.Person)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Hobby stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                HobbyStat("Pertandingan", "50+", Icons.Default.EmojiEvents)
                HobbyStat("Gol", "25+", Icons.Default.Star)
                HobbyStat("Tim", "3", Icons.Default.Group)
            }
        }
    }
}

@Composable
fun HobbyStat(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EnhancedCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            content()
        }
    }
}

@Composable
fun BiodataItem(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$label:",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun EducationTimelineItem(
    level: String,
    school: String,
    period: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    achievement: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = level,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = level,
                fontWeight = FontWeight.Medium,
                color = color
            )
            Text(
                text = school,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
            Text(
                text = period,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )
            Text(
                text = achievement,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun AchievementItem(achievement: String, icon: androidx.compose.ui.graphics.vector.ImageVector, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = achievement,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = achievement,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun ActivityItem(activity: String, description: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = activity,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$activity:",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = description,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun ContactDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Kontak",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                ContactItem("Email", "dendi.maulana@email.com", Icons.Default.Email)
                ContactItem("WhatsApp", "+62 812-3456-7890", Icons.Default.Phone)
                ContactItem("LinkedIn", "dendi-maulana", Icons.Default.Link)
                ContactItem("Instagram", "@dendi_maulana", Icons.Default.Share)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup")
            }
        }
    )
}

@Composable
fun ContactItem(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiPagePortfolioAppPreview() {
    MyFortofolioTheme {
        MultiPagePortfolioApp()
    }
}