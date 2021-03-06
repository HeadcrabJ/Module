/*
 * This file is part of Module.
 *
 * Module is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Module is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Module. If not, see <https://www.gnu.org/licenses/>.
 */

package org.module.listeners;

import org.module.manager.CacheManager;
import org.module.service.StatsService;
import org.module.structure.CommandListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandListener implements CommandListenerAdapter {
	private final StatsService statsService;

	@Autowired
	public CommandListener(StatsService statsService) {
		this.statsService = statsService;
	}

	@Override
	public void onCommand() {
		CacheManager.incrementExecutedCommands();

		if (CacheManager.checkExecutedCommands()) {
			statsService.incrementExecutedCommands(CacheManager.getExecutedCommands());
			CacheManager.resetExecutedCommands();
		}
	}
}
