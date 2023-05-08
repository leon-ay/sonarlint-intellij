/*
 * SonarLint for IntelliJ IDEA
 * Copyright (C) 2015-2023 SonarSource
 * sonarlint@sonarsource.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonarlint.intellij.ui.review

import com.intellij.openapi.ui.VerticalFlowLayout
import org.sonarsource.sonarlint.core.clientapi.backend.hotspot.HotspotStatus
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.ButtonGroup
import javax.swing.JPanel
import kotlin.properties.Delegates

class ReviewSecurityHotspotPanel(private val listAllowedStatuses: List<HotspotStatus>, val callbackForButton: (Boolean) -> Unit) : JPanel(),
    ActionListener {

    var selectedStatus: HotspotStatus by Delegates.observable(HotspotStatus.TO_REVIEW) { _, _, newValue -> callbackForButton(newValue != HotspotStatus.TO_REVIEW) }

    init {
        layout = VerticalFlowLayout(VerticalFlowLayout.TOP, 5, 15, true, false)
        handleStatusPanel(ButtonGroup())
    }

    private fun handleStatusPanel(buttonGroup: ButtonGroup) {
        listAllowedStatuses.forEach { status ->
            val statusPanel = StatusPanel(status, HotspotStatus.TO_REVIEW == status)
            buttonGroup.add(statusPanel.statusRadioButton)
            statusPanel.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    statusPanel.statusRadioButton.doClick()
                    statusPanel.statusRadioButton.requestFocus()
                }
            })
            statusPanel.statusRadioButton.addActionListener(this)
            add(statusPanel)
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        e ?: return
        selectedStatus = HotspotStatus.valueOf(e.actionCommand)
    }

}
