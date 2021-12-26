package eu.u032.BotEvents;

import eu.u032.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class InviteEvents extends ListenerAdapter {

    @Override
    public void onGuildInviteCreate(GuildInviteCreateEvent event) {
        Invite invite = event.getInvite();
        User inviter = event.getInvite().getInviter();

        if (inviter == null) return;

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(inviter.getAsTag(), null, inviter.getEffectiveAvatarUrl())
                .setColor(Color.decode("#89d561"))
                .setDescription(String.format("%s created an [invite](%s)", inviter.getAsMention(), invite.getUrl()))
                .setFooter("User ID: " + inviter.getId())
                .setTimestamp(new Date().toInstant());
        Objects.requireNonNull(event.getJDA().getTextChannelById(Config.getString("LOGS_CHANNEL")))
                .sendMessageEmbeds(embed.build())
                .queue();
    }

    @Override
    public void onGuildInviteDelete(GuildInviteDeleteEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor("Invite deleted")
                .setColor(Color.decode("#89d561"))
                .setDescription(String.format("Invite `%s` deleted", event.getCode()))
                .setTimestamp(new Date().toInstant());
        Objects.requireNonNull(event.getJDA().getTextChannelById(Config.getString("LOGS_CHANNEL")))
                .sendMessageEmbeds(embed.build())
                .queue();
    }

}
