package com.wolfgang.tradetrail.feature.checkout.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wolfgang.tradetrail.feature.checkout.formatted

@Composable
fun OrderSummary(
    total: Double,
    discountedTotal: Double
) {
    val discount = total - discountedTotal
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Subtotal")
            Text(total.formatted())
        }
        if (discount > 0) {
            Spacer(Modifier.height(4.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Discount")
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
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", style = MaterialTheme.typography.titleMedium)
            Text(discountedTotal.formatted(),
                style = MaterialTheme.typography.titleMedium)
        }
    }
}
