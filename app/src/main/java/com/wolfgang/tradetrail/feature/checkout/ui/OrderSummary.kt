package com.wolfgang.tradetrail.feature.checkout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wolfgang.tradetrail.core.designsystem.TextGrey
import com.wolfgang.tradetrail.feature.checkout.formatted

@Composable
fun OrderSummary(
    total: Double,
    discountedTotal: Double,
    onConfirm: () -> Unit
) {
    val discount = total - discountedTotal
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal", color = TextGrey)
                Text(total.formatted())
            }
            if (discount > 0) {
                Spacer(Modifier.height(4.dp))
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Discount", color = TextGrey)
                    Text("-${discount.formatted()}",
                        color = MaterialTheme.colorScheme.error)
                }
            }
            HorizontalDivider(
                Modifier.padding(vertical = 8.dp),
                DividerDefaults.Thickness,
                DividerDefaults.color
            )
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", style = MaterialTheme.typography.titleMedium)
                Text(discountedTotal.formatted(),
                    style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { onConfirm() },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) { Text("Confirm order", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary) }
            Spacer(Modifier.height(24.dp))
        }
    }
}
