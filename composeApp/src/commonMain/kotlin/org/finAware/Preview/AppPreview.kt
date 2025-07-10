package org.finAware.Preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.finAware.project.FinAwareAppRoot

@Preview(showBackground = true, name = "FinAware App Preview")
@Composable
fun AppPreview() {
    FinAwareAppRoot()
}
