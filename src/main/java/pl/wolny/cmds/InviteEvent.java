package pl.wolny.cmds;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InviteEvent extends ListenerAdapter {
    public void onGuildInviteCreate(GuildInviteCreateEvent event) {
        Member user = event.getGuild().getMemberById(event.getInvite().getInviter().getId());
        assert user != null;
        if(!(user.hasPermission(Permission.ADMINISTRATOR))){
            event.getInvite().delete().queue();
        }
    }
}
