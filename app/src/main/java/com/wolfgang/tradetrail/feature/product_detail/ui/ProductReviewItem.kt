package com.wolfgang.tradetrail.feature.product_detail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wolfgang.tradetrail.core.data.model.Review
import com.wolfgang.tradetrail.core.designsystem.TextGrey
import com.wolfgang.tradetrail.core.designsystem.Yellow

@Composable
fun ProductReviewItem(review: Review) {
    Column(Modifier.padding(vertical = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { index ->
                    Icon(
                        imageVector = if (index < review.rating) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = null,
                        tint = Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = review.reviewerName,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = review.date.substringBefore("T"),
                style = MaterialTheme.typography.bodySmall,
                color = TextGrey
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    }
}