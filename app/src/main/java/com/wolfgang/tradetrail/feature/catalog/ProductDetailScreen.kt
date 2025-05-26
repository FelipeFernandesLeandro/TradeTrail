package com.wolfgang.tradetrail.feature.catalog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wolfgang.tradetrail.core.data.model.Product
import com.wolfgang.tradetrail.core.designsystem.Yellow
import com.wolfgang.tradetrail.core.designsystem.component.TTAppBar
import com.wolfgang.tradetrail.feature.catalog.ui.ProductImageCarousel
import com.wolfgang.tradetrail.feature.checkout.formatted
import kotlinx.coroutines.flow.filterNotNull

/**
 * Product‑detail screen where the hero image starts occupying 65% of the screen when the
 * bottom‑sheet is collapsed (fully down) and shrinks linearly to 25% when the sheet is fully
 * expanded (offset == 0).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    vm: ProductDetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onCartClick: () -> Unit,
) {
    /* ---------- basic ui‑state ---------- */
    val state = vm.uiState
    when {
        state.isLoading       -> { Loading(); return }
        state.error != null   -> { Error(state.error); return }
        state.product == null -> return
    }
    val product          = state.product
    val cartCount  by vm.cartCount.collectAsState(0)
    val snackHost        = remember { SnackbarHostState() }

    /* ---------- window helpers ---------- */
    val density      = LocalDensity.current
    val windowHeight = LocalWindowInfo.current.containerSize.height.toFloat()
    if (windowHeight == 0f) { Loading(); return }

    val maxHeroPx = windowHeight * 0.65f      // hero when sheet collapsed
    val minHeroPx = windowHeight * 0.25f      // hero when sheet expanded (offset = 0)
    val peekHeightPx = windowHeight * 0.35f
    val peekHeightDp = with(density) { peekHeightPx.toDp() }

    /* ---------- bottom‑sheet scaffold ---------- */
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue    = SheetValue.PartiallyExpanded,
            skipHiddenState = true,
            confirmValueChange = { true }
        ),
    )

    /* ---------- track sheet offset & remember collapsed offset ---------- */
    val offsetPx       = remember { mutableFloatStateOf(0f) }
    val collapsedPxRef = remember { mutableFloatStateOf(Float.NaN) }

    LaunchedEffect(scaffoldState.bottomSheetState) {
        snapshotFlow { runCatching { scaffoldState.bottomSheetState.requireOffset() }.getOrNull() }
            .filterNotNull()
            .collect { off ->
                if (collapsedPxRef.floatValue.isNaN() || off > collapsedPxRef.floatValue) {
                    collapsedPxRef.floatValue = off
                }
                offsetPx.floatValue = off
            }
    }

    val heroHeightDp by remember {
        derivedStateOf {
            val currentOffset = offsetPx.floatValue
            val collapsedPx = collapsedPxRef.floatValue

            if (currentOffset.isNaN() || collapsedPx.isNaN() || collapsedPx == 0f) {
                return@derivedStateOf with(density) { maxHeroPx.toDp() }
            }

            val fraction = (currentOffset / collapsedPx).coerceIn(0f, 1f)
            val heroPx = minHeroPx + (maxHeroPx - minHeroPx) * fraction
            Log.d("HeroDebug‑compose", "offset=${currentOffset}  collapsed=$collapsedPx  fraction=$fraction  heroPx=$heroPx")
            with(density) { heroPx.toDp() }
        }
    }

    /* ---------- UI ---------- */
    Box(modifier = Modifier.fillMaxSize()) {
        BottomSheetScaffold(
            scaffoldState   = scaffoldState,
            containerColor  = Color.Transparent,
            sheetContainerColor = MaterialTheme.colorScheme.surface,
            sheetPeekHeight = peekHeightDp,
            sheetShape      = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            snackbarHost    = { SnackbarHost(snackHost) },
            sheetContent    = { BottomSheetBody(product) },
            topBar = {
                TTAppBar(
                    title       = product.title,
                    color    = MaterialTheme.colorScheme.surfaceVariant,
                    onBack      = onBack,
                    cartCount   = cartCount,
                    onCartClick = onCartClick,
                )
            },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heroHeightDp)
                    .padding(top = padding.calculateTopPadding())
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                ProductImageCarousel(product.images)
            }
        }
        AddToCartButton(modifier = Modifier.align(Alignment.BottomCenter)) {
            vm.addToCart(product)
        }
    }
}

/* --------------------------------‑ helpers -------------------------------- */
@Composable
private fun BottomSheetBody(p: Product) = Column(
    Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(24.dp)
        .navigationBarsPadding(),
) {
    Text(p.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(16.dp))
    Text(p.price.formatted(), style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(16.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { Icon(Icons.Default.Star, null, tint = Yellow, modifier = Modifier.size(16.dp)) }
        Spacer(Modifier.width(8.dp))
        Text("4.8k", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
    }
    Spacer(Modifier.height(16.dp))
    Text("Description", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(8.dp))
    repeat(10) { Text(p.description, style = MaterialTheme.typography.bodyMedium) }
}

@Composable private fun Loading() =
    Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }

@Composable private fun Error(msg: String) =
    Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Error: $msg") }

@Composable private fun AddToCartButton(modifier: Modifier = Modifier, onClick: () -> Unit) = Button(
    onClick = { onClick() },
    modifier = modifier
        .padding(horizontal = 16.dp, vertical = 24.dp)
        .navigationBarsPadding()
        .fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    shape = MaterialTheme.shapes.small,
) {
    Text("Add to cart")
}