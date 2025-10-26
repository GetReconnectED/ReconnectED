package com.getreconnected.reconnected.ui.composables.elements

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.getreconnected.reconnected.activities.MainActivity
import com.getreconnected.reconnected.core.Application
import com.getreconnected.reconnected.core.dataManager.UserManager
import com.getreconnected.reconnected.core.models.Screens
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

/**
 * The top bar of the app.
 *
 * @param title The title of the top bar.
 * @param navController The navigation controller.
 * @param onOpenDrawer The function to open the drawer.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("ktlint:standard:function-naming")
fun TopBar(
    title: String,
    context: Context,
    navController: NavHostController,
    onOpenDrawer: () -> Unit,
) {
    TopAppBar(
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary, // Green top bar
                titleContentColor = MaterialTheme.colorScheme.onPrimary, // White text
            ),
        title = {
            Text(
                text = title,
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
            )
        },
        navigationIcon = {
            IconButton(onClick = { onOpenDrawer() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Icon",
                    tint = MaterialTheme.colorScheme.onPrimary, // White icon
                )
            }
        },
        actions = {
            IconButton(onClick = {
                Toast
                    .makeText(
                        context,
                        "You are now logged out.",
                        Toast.LENGTH_LONG,
                    ).show()
                UserManager.logout()
                // TODO: navigate back to login screen
                (context as MainActivity).finish()
            }) {
                UserManager.user?.avatar?.let { avatarBitmap ->
                    Image(
                        painter = BitmapPainter(avatarBitmap.asImageBitmap()),
                        contentDescription = "Profile",
                        modifier = Modifier.width(36.dp).height(36.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                } ?: Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Profile",
                    modifier = Modifier.padding(1.dp).width(36.dp).height(36.dp),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun TopBarPreview() {
    val ctx = LocalContext.current
    val navController = NavHostController(LocalContext.current)
    ReconnectEDTheme {
        TopBar(
            title = "Dashboard",
            context = ctx,
            navController = navController,
            onOpenDrawer = {},
        )
    }
}
