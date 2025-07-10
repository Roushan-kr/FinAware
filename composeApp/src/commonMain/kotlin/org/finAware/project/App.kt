package org.finAware.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import finaware.composeapp.generated.resources.Res
import finaware.composeapp.generated.resources.homebanner
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FinAwareHomeScreen(
    onGoogleSignInClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "FinAware",
            modifier = Modifier.padding(vertical = 20.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green
        )

        Image(
            painter = painterResource(Res.drawable.homebanner),
            contentDescription = "Home Banner",
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(25.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Financial Literacy & Fraud Awareness",
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 10.dp),
            color = Color.Black
        )

        Text(
            text = "Learn personal finance skills and how to protect yourself from fraud.",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                HomeButton(
                    text = "Learning Zone",
                    onClick = onNavigateToLogin,
                    modifier = Modifier.weight(1f)
                )
                HomeButton(
                    text = "Fraud Zone",
                    onClick = onNavigateToLogin,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                HomeButton(
                    text = "Quiz Me",
                    onClick = onNavigateToLogin,
                    modifier = Modifier.weight(1f)
                )
                HomeButton(
                    text = "Track & Tips",
                    onClick = onNavigateToLogin,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun HomeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A84FF))
    ) {
        Text(text = text)
    }
}
@Preview
@Composable
fun FinAwareHomePreview(showSystemUi: Boolean = true, showBackground: Boolean = true) {
    FinAwareHomeScreen(
        onGoogleSignInClick = {},
        onNavigateToLogin = {}
    )
}
