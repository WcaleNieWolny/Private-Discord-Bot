package pl.wolny.cmds;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Rep extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        if(msg[0].equalsIgnoreCase("?rep")){
            Member user = event.getGuild().getMemberById(event.getAuthor().getId());
            assert user != null;
            if(user.hasPermission(Permission.ADMINISTRATOR)) {
                for(int i=0;i<event.getGuild().getRoles().size();i++) {
                    if(!event.getGuild().getRoles().get(0).getId().equals("795011149887832066")){
                        event.getGuild().getRoles().get(0).getManager().revokePermissions(Permission.CREATE_INSTANT_INVITE).queue();
                    }
                }
            }
        }

    }
}
