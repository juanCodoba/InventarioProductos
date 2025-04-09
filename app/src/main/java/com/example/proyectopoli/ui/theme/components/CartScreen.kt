import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectopoli.features.cart.CartViewModel
import com.example.proyectopoli.model.CartItem
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel,
    onCheckout: () -> Unit = {}
) {
    val cartItems = viewModel.cartItems.collectAsState().value
    val total = viewModel.total // Cambiado porque es una propiedad calculada

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                CartBottomBar(
                    total = total,
                    onClearCart = { viewModel.clearCart() },
                    onCheckout = onCheckout,
                    onContinueShopping = { navController.popBackStack() }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                EmptyCartView(onContinueShopping = { navController.popBackStack() })
            } else {
                CartContent(
                    items = cartItems,
                    total = total,
                    onQuantityChange = { id, quantity ->
                        viewModel.updateQuantity(id, quantity)
                    },
                    onRemoveItem = { id -> viewModel.removeItem(id) }
                )
            }
        }
    }
}

@Composable
private fun CartContent(
    items: List<CartItem>,
    total: StateFlow<Double>,
    onQuantityChange: (String, Int) -> Unit,
    onRemoveItem: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header
        item {
            CartHeader()
        }

        // Items
        items(items) { item ->
            CartItemRow(
                item = item,
                onQuantityChange = { newQuantity -> onQuantityChange(item.id, newQuantity) },
                onRemoveItem = { onRemoveItem(item.id) }
            )
        }

        // Total
        item {
            CartTotal(total = total)
        }
    }
}

@Composable
private fun CartHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Producto",
            modifier = Modifier.weight(2f),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Precio",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
        Text(
            text = "Cantidad",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Sub Total",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.weight(0.2f))
    }
}

@Composable
private fun CartTotal(total: StateFlow<Double>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Total",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "$${"%,.0f".format(total)}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = "$${"%,.0f".format(item.price)}",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
            QuantitySelector(
                currentQuantity = item.quantity,
                onQuantityChange = onQuantityChange,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$${"%,.0f".format(item.subTotal)}",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
            IconButton(
                onClick = onRemoveItem,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun QuantitySelector(
    currentQuantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { if (currentQuantity > 1) onQuantityChange(currentQuantity - 1) },
            modifier = Modifier.size(24.dp)
        ) {
            Text("-", fontSize = 18.sp)
        }
        Text(
            text = currentQuantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(
            onClick = { onQuantityChange(currentQuantity + 1) },
            modifier = Modifier.size(24.dp)
        ) {
            Text("+", fontSize = 18.sp)
        }
    }
}

@Composable
private fun EmptyCartView(
    onContinueShopping: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tu carrito está vacío",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onContinueShopping) {
            Text("Ver productos")
        }
    }
}

@Composable
private fun CartBottomBar(
    total: StateFlow<Double>,
    onClearCart: () -> Unit,
    onCheckout: () -> Unit,
    onContinueShopping: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedButton(
            onClick = onClearCart,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Eliminar todo")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onContinueShopping,
                modifier = Modifier.weight(1f)
            ) {
                Text("Seguir comprando")
            }
            Button(
                onClick = onCheckout,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text("Comprar ahora")
            }
        }
    }
}