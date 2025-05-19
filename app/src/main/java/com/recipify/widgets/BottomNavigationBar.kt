import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.recipify.navigation_service.Screen
import com.recipify.ui.theme.Cyan

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier  // Provide a default for safety
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            label = "Home",
            isSelected = currentRoute == Screen.Home.route,
            onClick = {
                if (currentRoute != Screen.Home.route) {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                }
            },
            icon = Icons.Default.Home
        )

        BottomNavItem(
            label = "Account",
            isSelected = currentRoute == Screen.UserAccount.route,
            onClick = {
                if (currentRoute != Screen.UserAccount.route) {
                    navController.navigate(Screen.UserAccount.route){
                        launchSingleTop = true
                    }
                }
            },
            icon = Icons.Default.AccountCircle
        )
    }
}


@Composable
fun BottomNavItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Cyan else Color.Gray,
            modifier = Modifier.size(26.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) Cyan else Color.Gray
        )
    }
}
