package eu.u032.Commands.Utilities;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import eu.u032.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

public class AvatarCommand extends Command {

    public AvatarCommand() {
        this.name = "avatar";
        this.help = "Show avatar of member";
        this.arguments = "[@Member | ID]";
        this.category = new Category("Utilities");
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member = Utils.getMemberFromArgs(event) != null ? Utils.getMemberFromArgs(event) : event.getMember();

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor("Avatar of " + member.getUser().getName())
                .setColor(member.getColorRaw())
                .setImage(member.getEffectiveAvatarUrl() + "?size=512");
        event.reply(embed.build());
    }

}