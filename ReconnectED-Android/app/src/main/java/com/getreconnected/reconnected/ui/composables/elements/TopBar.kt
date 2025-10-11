package com.getreconnected.reconnected.ui.composables.elements

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

/**
 * The top bar of the app.
 *
 * @param title The title of the top bar.
 * @param onOpenDrawer The function to open the drawer.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("ktlint:standard:function-naming")
fun TopBar(
    title: String,
    onOpenDrawer: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Companion.ExtraBold,
                    ),
            )
        },
        navigationIcon = {
            IconButton(onClick = { onOpenDrawer() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Icon",
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Handle profile click */ }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Profile",
                    modifier =
                        Modifier.Companion
                            .padding(1.dp)
                            .width(36.dp)
                            .height(36.dp),
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun TopBarPreview() {
    ReconnectEDTheme {
        TopBar(
            title = "Dashboard",
            onOpenDrawer = {},
        )
    }
}
