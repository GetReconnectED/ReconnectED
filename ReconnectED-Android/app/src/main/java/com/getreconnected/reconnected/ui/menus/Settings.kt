package com.getreconnected.reconnected.ui.menus

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.getreconnected.reconnected.core.viewModels.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@Suppress("ktlint:standard:function-naming")
fun Settings(
        modifier: Modifier = Modifier,
        viewModel: SettingsViewModel = viewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var isSyncing by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }

    Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
        )

        // Data Sync Section
        Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                        text = "Data Synchronization",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                )

                Text(
                        text =
                                "Sync your app usage data to the cloud. Data is automatically synced every 5 minutes.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                        onClick = {
                            isSyncing = true
                            try {
                                viewModel.syncNow()
                                Toast.makeText(
                                                context,
                                                "Sync started successfully!",
                                                Toast.LENGTH_SHORT
                                        )
                                        .show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                                context,
                                                "Sync failed: ${e.message}",
                                                Toast.LENGTH_LONG
                                        )
                                        .show()
                            } finally {
                                // Delay to show loading state briefly
                                scope.launch {
                                    delay(1000)
                                    isSyncing = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSyncing,
                ) {
                    if (isSyncing) {
                        CircularProgressIndicator(
                                modifier = Modifier.height(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                        )
                    } else {
                        Text("Sync Now")
                    }
                }
            }
        }

        // Account Management Section
        Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                        text = "Account Management",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                )

                Text(
                        text =
                                "Delete your account and all associated data from our servers. This action cannot be undone.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                                ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                ),
                        enabled = !isDeleting,
                ) {
                    if (isDeleting) {
                        CircularProgressIndicator(
                                modifier = Modifier.height(20.dp),
                                color = MaterialTheme.colorScheme.onError,
                        )
                    } else {
                        Text("Delete Account")
                    }
                }
            }
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Account?") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                                "Are you sure you want to delete your account? All your data will be permanently deleted from our servers. This action cannot be undone."
                        )
                        Text(
                                "Note: If you haven't logged in recently, you may need to sign in again and repeat this action to fully delete your Firebase account.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                            onClick = {
                                showDeleteDialog = false
                                isDeleting = true
                                scope.launch {
                                    try {
                                        viewModel.deleteAccount(context)
                                        Toast.makeText(
                                                        context,
                                                        "Account deleted successfully",
                                                        Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        // The viewModel will navigate to login screen
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                                        context,
                                                        "Failed to delete account: ${e.message}",
                                                        Toast.LENGTH_LONG
                                                )
                                                .show()
                                    } finally {
                                        isDeleting = false
                                    }
                                }
                            },
                            colors =
                                    ButtonDefaults.textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.error,
                                    ),
                    ) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
                },
        )
    }
}
