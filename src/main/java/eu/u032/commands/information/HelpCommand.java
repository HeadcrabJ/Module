/*
 * Module Discord Bot.
 * Copyright (C) 2022 untled032, Headcrab

 * UASM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * UASM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with UASM. If not, see https://www.gnu.org/licenses/.
 */

package eu.u032.commands.information;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import eu.u032.Constants;
import eu.u032.util.SettingsUtil;
import eu.u032.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.LinkedList;
import java.util.List;

public class HelpCommand extends Command {
    public HelpCommand() {
        this.name = MessageUtil.getMessage("command.help.name");
        this.help = MessageUtil.getMessage("command.help.help");
        this.arguments = MessageUtil.getMessage("command.help.arguments");
        this.category = Constants.INFORMATION;
    }

    @Override
    protected void execute(CommandEvent event) {
        String args = event.getArgs();
        String prefix = SettingsUtil.getPrefix(event.getGuild());
        List<Command> commands = event.getClient().getCommands();
        EmbedBuilder embed = new EmbedBuilder().setColor(Constants.COLOR);
        List<String> categories = new LinkedList<>();

        categoriesLoop:
        for (Command cmd : commands) {
            if (cmd.getCategory() == null) continue;
            for (String category : categories) {
                // if command already exists in "categories" - continue
                if (category.equals(cmd.getCategory().getName())) continue categoriesLoop;
            }
            categories.add(cmd.getCategory().getName());
        }

        // if "args" is empty - getTemplate all commands
        if (args.isEmpty()) {
            StringBuilder commandsBuilder = new StringBuilder();
            embed.setTitle("Available commands:");

            for (String category : categories) {
                for (Command cmd : commands) {
                    if (cmd.isHidden()) continue;
                    if (cmd.getCategory().getName().equals(category)) {
                        commandsBuilder.append("`")
							.append(prefix)
							.append(cmd.getName())
							.append("` ");
                    }
                }
                embed.addField(category + " (" + prefix + "help " + category + ")",
					commandsBuilder.toString(), false);
                commandsBuilder = new StringBuilder();
            }

            event.reply(embed.build());
        } else {
            for (String category : categories) {
                // if match found with name of category
                if (category.toLowerCase().startsWith(args.toLowerCase())) {
                    for (Command cmd : commands) {
                        if (cmd.isHidden()) continue;
                        if (cmd.getCategory().getName().equals(category))
                            embed.addField(prefix + cmd.getName(), cmd.getHelp(), false);
                    }
                    embed.setTitle("Commands of category " + category);
                    event.reply(embed.build());
                    return;
                }
            }
            for (Command cmd : commands) {
                // if match found with name of command
                if (cmd.getName().toLowerCase().startsWith(args.toLowerCase()) && !cmd.isHidden()) {
					MessageUtil.sendHelp(event, cmd);
					return;
                }
            }

			MessageUtil.sendErrorMessage(event, "Command or category **" + args + "** not found.");
        }
    }
}
