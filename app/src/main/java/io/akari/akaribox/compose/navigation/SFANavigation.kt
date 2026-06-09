package io.akari.akaribox.compose.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.akari.akaribox.compose.screen.configuration.NewProfileScreen
import io.akari.akaribox.compose.screen.connections.ConnectionDetailsRoute
import io.akari.akaribox.compose.screen.connections.ConnectionsPage
import io.akari.akaribox.compose.screen.connections.ConnectionsViewModel
import io.akari.akaribox.compose.screen.dashboard.DashboardScreen
import io.akari.akaribox.compose.screen.dashboard.DashboardViewModel
import io.akari.akaribox.compose.screen.dashboard.GroupsCard
import io.akari.akaribox.compose.screen.dashboard.groups.GroupsViewModel
import io.akari.akaribox.compose.screen.log.HookLogScreen
import io.akari.akaribox.compose.screen.log.LogScreen
import io.akari.akaribox.compose.screen.log.LogViewModel
import io.akari.akaribox.compose.screen.privilegesettings.PrivilegeSettingsManageScreen
import io.akari.akaribox.compose.screen.profile.EditProfileRoute
import io.akari.akaribox.compose.screen.profileoverride.PerAppProxyScreen
import io.akari.akaribox.compose.screen.settings.AppSettingsScreen
import io.akari.akaribox.compose.screen.settings.CoreSettingsScreen
import io.akari.akaribox.compose.screen.settings.FDroidMirrorScreen
import io.akari.akaribox.compose.screen.settings.PrivilegeSettingsScreen
import io.akari.akaribox.compose.screen.settings.ProfileOverrideScreen
import io.akari.akaribox.compose.screen.settings.ServiceSettingsScreen
import io.akari.akaribox.compose.screen.settings.SettingsScreen
import io.akari.akaribox.compose.screen.settings.TailscaleFontPickerScreen
import io.akari.akaribox.compose.screen.settings.TailscaleTerminalConfigScreen
import io.akari.akaribox.compose.screen.settings.TailscaleThemePickerScreen
import io.akari.akaribox.compose.screen.tools.CrashReportDetailScreen
import io.akari.akaribox.compose.screen.tools.CrashReportFileContentScreen
import io.akari.akaribox.compose.screen.tools.CrashReportListScreen
import io.akari.akaribox.compose.screen.tools.CrashReportMetadataScreen
import io.akari.akaribox.compose.screen.tools.NetworkQualityScreen
import io.akari.akaribox.compose.screen.tools.OOMReportDetailScreen
import io.akari.akaribox.compose.screen.tools.OOMReportFileContentScreen
import io.akari.akaribox.compose.screen.tools.OOMReportListScreen
import io.akari.akaribox.compose.screen.tools.OOMReportMetadataScreen
import io.akari.akaribox.compose.screen.tools.OutboundPickerScreen
import io.akari.akaribox.compose.screen.tools.STUNTestScreen
import io.akari.akaribox.compose.screen.tools.TailscaleEndpointScreen
import io.akari.akaribox.compose.screen.tools.TailscaleExitNodePickerScreen
import io.akari.akaribox.compose.screen.tools.TailscalePeerScreen
import io.akari.akaribox.compose.screen.tools.TailscaleSSHPromptScreen
import io.akari.akaribox.compose.screen.tools.TailscaleSSHSharedViewModel
import io.akari.akaribox.compose.screen.tools.TailscaleSSHTerminalScreen
import io.akari.akaribox.compose.screen.tools.TailscaleStatusViewModel
import io.akari.akaribox.compose.screen.tools.ToolsScreen
import io.akari.akaribox.constant.Status

private val slideInFromRight: AnimatedContentTransitionScope<*>.() -> androidx.compose.animation.EnterTransition = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300))
}

private val slideOutToRight: AnimatedContentTransitionScope<*>.() -> androidx.compose.animation.ExitTransition = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300))
}

private val slideInFromLeft: AnimatedContentTransitionScope<*>.() -> androidx.compose.animation.EnterTransition = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300))
}

private val slideOutToLeft: AnimatedContentTransitionScope<*>.() -> androidx.compose.animation.ExitTransition = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300))
}

@Composable
fun SFANavHost(
    navController: NavHostController,
    serviceStatus: Status = Status.Stopped,
    showStartFab: Boolean = false,
    showStatusBar: Boolean = false,
    newProfileArgs: NewProfileArgs = NewProfileArgs(),
    onClearNewProfileArgs: () -> Unit = {},
    onOpenNewProfile: (NewProfileArgs) -> Unit = {},
    dashboardViewModel: DashboardViewModel? = null,
    logViewModel: LogViewModel? = null,
    groupsViewModel: GroupsViewModel? = null,
    connectionsViewModel: ConnectionsViewModel? = null,
    tailscaleStatusViewModel: TailscaleStatusViewModel? = null,
    tailscaleSSHSharedViewModel: TailscaleSSHSharedViewModel? = null,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier,
    ) {
        composable(Screen.Dashboard.route) {
            if (dashboardViewModel != null) {
                DashboardScreen(
                    serviceStatus = serviceStatus,
                    showStartFab = showStartFab,
                    showStatusBar = showStatusBar,
                    onOpenNewProfile = onOpenNewProfile,
                    viewModel = dashboardViewModel,
                )
            } else {
                DashboardScreen(
                    serviceStatus = serviceStatus,
                    showStartFab = showStartFab,
                    showStatusBar = showStatusBar,
                    onOpenNewProfile = onOpenNewProfile,
                )
            }
        }

        composable(Screen.Log.route) {
            if (logViewModel != null) {
                LogScreen(
                    serviceStatus = serviceStatus,
                    showStartFab = showStartFab,
                    showStatusBar = showStatusBar,
                    viewModel = logViewModel,
                )
            } else {
                LogScreen(
                    serviceStatus = serviceStatus,
                    showStartFab = showStartFab,
                    showStatusBar = showStatusBar,
                )
            }
        }

        composable(Screen.Groups.route) {
            if (groupsViewModel != null) {
                GroupsCard(
                    serviceStatus = serviceStatus,
                    viewModel = groupsViewModel,
                    showTopBar = true,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                GroupsCard(
                    serviceStatus = serviceStatus,
                    showTopBar = true,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        composable(Screen.Connections.route) {
            if (connectionsViewModel != null) {
                ConnectionsPage(
                    serviceStatus = serviceStatus,
                    viewModel = connectionsViewModel,
                    showTitle = false,
                    showTopBar = true,
                    onConnectionClick = { connectionId ->
                        navController.navigate("connections/detail/${Uri.encode(connectionId)}")
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                ConnectionsPage(
                    serviceStatus = serviceStatus,
                    showTitle = false,
                    showTopBar = true,
                    onConnectionClick = { connectionId ->
                        navController.navigate("connections/detail/${Uri.encode(connectionId)}")
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        composable(ProfileRoutes.NewProfile) {
            DisposableEffect(Unit) {
                onDispose { onClearNewProfileArgs() }
            }
            NewProfileScreen(
                importName = newProfileArgs.importName,
                importUrl = newProfileArgs.importUrl,
                qrsData = newProfileArgs.qrsData,
                onNavigateBack = {
                    onClearNewProfileArgs()
                    navController.navigateUp()
                },
                onProfileCreated = { profileId ->
                    onClearNewProfileArgs()
                    navController.navigate(ProfileRoutes.editProfile(profileId)) {
                        popUpTo(ProfileRoutes.NewProfile) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(
            route = ProfileRoutes.EditProfile,
            arguments = listOf(
                navArgument("profileId") {
                    type = NavType.LongType
                },
            ),
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getLong("profileId") ?: -1L
            EditProfileRoute(
                profileId = profileId,
                onNavigateBack = { navController.navigateUp() },
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable("connections/detail/{connectionId}") { backStackEntry ->
            val connectionId = backStackEntry.arguments?.getString("connectionId")
            if (connectionId != null) {
                if (connectionsViewModel != null) {
                    ConnectionDetailsRoute(
                        connectionId = connectionId,
                        serviceStatus = serviceStatus,
                        viewModel = connectionsViewModel,
                        onBack = { navController.navigateUp() },
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    ConnectionDetailsRoute(
                        connectionId = connectionId,
                        serviceStatus = serviceStatus,
                        onBack = { navController.navigateUp() },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }

        composable(Screen.Tools.route) {
            val tailscaleViewModel: TailscaleStatusViewModel = tailscaleStatusViewModel ?: viewModel()
            val sshSharedViewModel: TailscaleSSHSharedViewModel = tailscaleSSHSharedViewModel ?: viewModel()
            ToolsScreen(navController = navController, serviceStatus = serviceStatus, tailscaleViewModel = tailscaleViewModel, sshSharedViewModel = sshSharedViewModel)
        }

        // Tools subscreens with slide animations
        composable(
            route = "tools/network_quality",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            NetworkQualityScreen(navController = navController, serviceStatus = serviceStatus)
        }

        composable(
            route = "tools/stun_test",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            STUNTestScreen(navController = navController, serviceStatus = serviceStatus)
        }

        composable(
            route = "tools/outbound_picker/{selectedOutbound}",
            arguments = listOf(navArgument("selectedOutbound") { type = NavType.StringType }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val selectedOutbound = Uri.decode(backStackEntry.arguments?.getString("selectedOutbound") ?: "")
            OutboundPickerScreen(navController = navController, selectedOutbound = selectedOutbound)
        }

        composable(
            route = "tools/tailscale/{endpointTag}",
            arguments = listOf(navArgument("endpointTag") { type = NavType.StringType }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val endpointTag = Uri.decode(backStackEntry.arguments?.getString("endpointTag") ?: return@composable)
            val tailscaleViewModel: TailscaleStatusViewModel = tailscaleStatusViewModel ?: viewModel()
            val sshSharedViewModel: TailscaleSSHSharedViewModel = tailscaleSSHSharedViewModel ?: viewModel()
            TailscaleEndpointScreen(navController = navController, viewModel = tailscaleViewModel, sshSharedViewModel = sshSharedViewModel, endpointTag = endpointTag)
        }

        composable(
            route = "tools/tailscale/{endpointTag}/exit_node",
            arguments = listOf(navArgument("endpointTag") { type = NavType.StringType }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val endpointTag = Uri.decode(backStackEntry.arguments?.getString("endpointTag") ?: return@composable)
            val tailscaleViewModel: TailscaleStatusViewModel = tailscaleStatusViewModel ?: viewModel()
            TailscaleExitNodePickerScreen(navController = navController, viewModel = tailscaleViewModel, endpointTag = endpointTag)
        }

        composable(
            route = "tools/tailscale/{endpointTag}/peer/{peerId}",
            arguments = listOf(
                navArgument("endpointTag") { type = NavType.StringType },
                navArgument("peerId") { type = NavType.StringType },
            ),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val endpointTag = Uri.decode(backStackEntry.arguments?.getString("endpointTag") ?: return@composable)
            val peerId = Uri.decode(backStackEntry.arguments?.getString("peerId") ?: return@composable)
            val tailscaleViewModel: TailscaleStatusViewModel = tailscaleStatusViewModel ?: viewModel()
            TailscalePeerScreen(navController = navController, viewModel = tailscaleViewModel, endpointTag = endpointTag, peerId = peerId)
        }

        composable(
            route = "tools/tailscale/{endpointTag}/peer/{peerId}/ssh",
            arguments = listOf(
                navArgument("endpointTag") { type = NavType.StringType },
                navArgument("peerId") { type = NavType.StringType },
            ),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val endpointTag = Uri.decode(backStackEntry.arguments?.getString("endpointTag") ?: return@composable)
            val peerId = Uri.decode(backStackEntry.arguments?.getString("peerId") ?: return@composable)
            val tailscaleViewModel: TailscaleStatusViewModel = tailscaleStatusViewModel ?: viewModel()
            val sshSharedViewModel: TailscaleSSHSharedViewModel = tailscaleSSHSharedViewModel ?: viewModel()
            TailscaleSSHPromptScreen(
                navController = navController,
                sharedViewModel = sshSharedViewModel,
                viewModel = tailscaleViewModel,
                endpointTag = endpointTag,
                peerId = peerId,
            )
        }

        composable(
            route = "tools/tailscale/{endpointTag}/peer/{peerId}/terminal",
            arguments = listOf(
                navArgument("endpointTag") { type = NavType.StringType },
                navArgument("peerId") { type = NavType.StringType },
            ),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val sshSharedViewModel: TailscaleSSHSharedViewModel = tailscaleSSHSharedViewModel ?: viewModel()
            val tailscaleViewModel: TailscaleStatusViewModel = tailscaleStatusViewModel ?: viewModel()
            TailscaleSSHTerminalScreen(
                navController = navController,
                sharedViewModel = sshSharedViewModel,
                tailscaleViewModel = tailscaleViewModel,
            )
        }

        composable(
            route = "tools/crash_report",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            CrashReportListScreen(navController = navController)
        }

        composable(
            route = "tools/crash_report/{reportId}",
            arguments = listOf(navArgument("reportId") { type = NavType.StringType }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId") ?: return@composable
            CrashReportDetailScreen(navController = navController, reportId = reportId)
        }

        composable(
            route = "tools/crash_report/{reportId}/metadata",
            arguments = listOf(navArgument("reportId") { type = NavType.StringType }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId") ?: return@composable
            CrashReportMetadataScreen(navController = navController, reportId = reportId)
        }

        composable(
            route = "tools/crash_report/{reportId}/file/{fileKind}",
            arguments = listOf(
                navArgument("reportId") { type = NavType.StringType },
                navArgument("fileKind") { type = NavType.StringType },
            ),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId") ?: return@composable
            val fileKind = backStackEntry.arguments?.getString("fileKind") ?: return@composable
            CrashReportFileContentScreen(navController = navController, reportId = reportId, fileKind = fileKind)
        }

        composable(
            route = "tools/oom_report",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            OOMReportListScreen(navController = navController, serviceStatus = serviceStatus)
        }

        composable(
            route = "tools/oom_report/{reportId}",
            arguments = listOf(navArgument("reportId") { type = NavType.StringType }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId") ?: return@composable
            OOMReportDetailScreen(navController = navController, reportId = reportId)
        }

        composable(
            route = "tools/oom_report/{reportId}/metadata",
            arguments = listOf(navArgument("reportId") { type = NavType.StringType }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId") ?: return@composable
            OOMReportMetadataScreen(navController = navController, reportId = reportId)
        }

        composable(
            route = "tools/oom_report/{reportId}/file/{fileKind}",
            arguments = listOf(
                navArgument("reportId") { type = NavType.StringType },
                navArgument("fileKind") { type = NavType.StringType },
            ),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId") ?: return@composable
            val fileKind = backStackEntry.arguments?.getString("fileKind") ?: return@composable
            OOMReportFileContentScreen(navController = navController, reportId = reportId, fileKind = fileKind)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

        // Settings subscreens with slide animations
        composable(
            route = "settings/app",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            AppSettingsScreen(navController = navController, serviceStatus = serviceStatus)
        }

        composable(
            route = "settings/fdroid_mirror",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            FDroidMirrorScreen(navController = navController)
        }

        composable(
            route = "settings/core",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToRight,
            popEnterTransition = slideInFromRight,
            popExitTransition = slideOutToRight,
        ) {
            CoreSettingsScreen(navController = navController)
        }

        composable(
            route = "settings/service",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            ServiceSettingsScreen(navController = navController, serviceStatus = serviceStatus)
        }

        composable(
            route = "settings/profile_override",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            ProfileOverrideScreen(navController = navController, serviceStatus = serviceStatus)
        }

        composable(
            route = "settings/profile_override/manage",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            PerAppProxyScreen(onBack = { navController.navigateUp() }, serviceStatus = serviceStatus)
        }

        composable(
            route = "settings/privilege",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            PrivilegeSettingsScreen(navController = navController, serviceStatus = serviceStatus)
        }

        composable(
            route = "settings/privilege/manage",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            PrivilegeSettingsManageScreen(onBack = { navController.navigateUp() }, serviceStatus = serviceStatus)
        }

        composable(
            route = "settings/tailscale/terminal_config",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            TailscaleTerminalConfigScreen(navController = navController)
        }

        composable(
            route = "settings/tailscale/theme_picker/{isDark}",
            arguments = listOf(navArgument("isDark") { type = NavType.StringType }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) { backStackEntry ->
            val isDarkStr = backStackEntry.arguments?.getString("isDark") ?: "false"
            TailscaleThemePickerScreen(navController = navController, isDark = isDarkStr == "true")
        }

        composable(
            route = "settings/tailscale/font_picker",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            TailscaleFontPickerScreen(navController = navController)
        }

        composable(
            route = "settings/privilege/logs",
            enterTransition = slideInFromRight,
            exitTransition = slideOutToLeft,
            popEnterTransition = slideInFromLeft,
            popExitTransition = slideOutToRight,
        ) {
            HookLogScreen(onBack = { navController.navigateUp() })
        }
    }
}
