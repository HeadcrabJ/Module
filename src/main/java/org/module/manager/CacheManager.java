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

package org.module.manager;

import net.dv8tion.jda.api.entities.Message;
import org.module.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/*
 * Author: https://github.com/Starrysparklez/
 * Code: https://gist.github.com/Starrysparklez/3da0d67241d8185315e4fdc012f8aca7
 */
public class CacheManager {
    public static final ArrayList<Message> MESSAGES = new ArrayList<>();

	public static int executedCommands = 0;

    public static void addMessage(@Nonnull Message message) {
		MESSAGES.stream()
			.filter(msg -> msg.getIdLong() == message.getIdLong())
			.forEach(msg -> MESSAGES.set(MESSAGES.indexOf(msg), message));

		if (MESSAGES.size() + 1 > Constants.MAX_MESSAGE_CACHE) MESSAGES.remove(0);

        MESSAGES.add(message);
    }

    @Nullable
    public static Message getMessage(long messageId) {
		return MESSAGES.stream()
			.filter(msg -> msg.getIdLong() == messageId)
			.findFirst()
			.orElse(null);
    }

	public static void incrementExecutedCommands() {
		executedCommands++;
	}

	public static boolean checkExecutedCommands() {
		return executedCommands >= Constants.MAX_EXECUTED_COMMANDS_CACHE;
	}

	public static int getExecutedCommands() {
		return executedCommands;
	}

	public static void resetExecutedCommands() {
		executedCommands = 0;
	}
}
