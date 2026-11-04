package com.schluterman.golftracker.ui.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Locale

private val Evergreen = Color(0xFF173D32)
private val Background = Color(0xFFF5F5EF)
private val Gold = Color(0xFFC6A45A)
private val TextDark = Color(0xFF202B25)
private val TextMuted = Color(0xFF858C84)

@Composable
fun HomeScreenRoot(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onStartRound: () -> Unit = {},
    onResumeRound: () -> Unit = {},
    onViewStats: () -> Unit = {},
    onViewProfile: () -> Unit = {},
    onViewRounds: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onStartRound = onStartRound,
        onResumeRound = onResumeRound,
        onViewStats = onViewStats,
        onViewProfile = onViewProfile,
        onViewRounds = onViewRounds
    )
}

@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    onStartRound: () -> Unit = {},
    onResumeRound: () -> Unit = {},
    onViewStats: () -> Unit = {},
    onViewProfile: () -> Unit = {},
    onViewRounds: () -> Unit = {},
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 4.dp
            ) {
                val tabs = listOf(
                    Triple("Home", Icons.Default.Home, 0),
                    Triple("Play", Icons.Default.Flag, 1),
                    Triple("Stats", Icons.Default.BarChart, 2),
                    Triple("Profile", Icons.Default.Person, 3)
                )

                tabs.forEach { (label, icon, index) ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index

                            when (index) {
                                1 -> onStartRound()
                                2 -> onViewStats()
                                3 -> onViewProfile()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label
                            )
                        },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Evergreen,
                            selectedTextColor = Evergreen,
                            indicatorColor = Color(0xFFE5EBE5)
                        )
                    )
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(22.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "YOUR GOLF DASHBOARD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text = "Ready to play?",
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = Evergreen,
                    modifier = Modifier
                        .size(46.dp)
                        .clickable { onViewProfile() }
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.padding(11.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Start a round
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onStartRound() },
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Evergreen
                )
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.12f)
                        ) {
                            Icon(
                                Icons.Default.Flag,
                                contentDescription = null,
                                tint = Gold,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            "YOUR NEXT ROUND STARTS HERE",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "Let's hit the course.",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        "Find a course and start tracking your game.",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.75f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onStartRound,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Gold,
                            contentColor = Evergreen
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            "START NEW ROUND",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Active round (only displayed when one exists)
            uiState.activeRound?.let { round ->

                Spacer(modifier = Modifier.height(22.dp))

                SectionHeader("IN PROGRESS", "Resume", onResumeRound)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onResumeRound() },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    round.courseName,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )

                                Spacer(modifier = Modifier.height(5.dp))

                                Text(
                                    round.date,
                                    fontSize = 12.sp,
                                    color = TextMuted
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    formatScoreToPar(round.scoreToPar),
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Evergreen
                                )

                                Text(
                                    "THRU ${round.holesCompleted}",
                                    fontSize = 10.sp,
                                    color = TextMuted,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalDivider(color = Background)

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Continue your round",
                                fontSize = 13.sp,
                                color = TextDark
                            )

                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = "Resume round",
                                tint = Evergreen
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Statistics
            SectionHeader(
                "YOUR PERFORMANCE",
                "View all",
                onViewStats
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "AVG SCORE",
                    value = uiState.stats?.averageScore
                        ?.let { String.format(Locale.US, "%.1f", it) } ?: "—"
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "HANDICAP",
                    value = uiState.stats?.handicap
                        ?.let { String.format(Locale.US, "%.1f", it) } ?: "—"
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "FAIRWAYS",
                    value = uiState.stats?.fairwayPercentage
                        ?.let { "$it%" } ?: "—"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Recent rounds
            SectionHeader(
                "RECENT ROUNDS",
                "See all",
                onViewRounds
            )

            if (uiState.recentRounds.isEmpty()) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.GolfCourse,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(35.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            "No rounds played yet",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            "Your completed rounds will appear here.",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                }

            } else {

                for (round in uiState.recentRounds) {

                    RecentRound(round = round)

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    action: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            letterSpacing = 1.sp
        )

        Text(
            action,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Evergreen,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(11.dp)
        ) {
            Text(
                label,
                fontSize = 9.sp,
                color = TextMuted,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Evergreen
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                if (value == "—") "No data yet" else "Current",
                fontSize = 9.sp,
                color = TextMuted
            )
        }
    }
}

@Composable
fun RecentRound(round: RoundSummary) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFE8EEE8)
            ) {
                Icon(
                    Icons.Default.GolfCourse,
                    contentDescription = null,
                    tint = Evergreen,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    round.courseName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    round.date,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    round.score.toString(),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Evergreen
                )

                Text(
                    formatScoreToPar(round.scoreToPar),
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }
    }
}

private fun formatScoreToPar(score: Int): String {
    return when {
        score > 0 -> "+$score"
        score < 0 -> score.toString()
        else -> "E"
    }
}
